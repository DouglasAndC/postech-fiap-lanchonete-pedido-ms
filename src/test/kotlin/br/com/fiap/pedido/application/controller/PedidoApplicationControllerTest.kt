package br.com.fiap.pedido.application.controller

import br.com.fiap.pedido.application.dto.request.ComboRequest
import br.com.fiap.pedido.application.dto.request.Data
import br.com.fiap.pedido.application.dto.request.PedidoRequest
import br.com.fiap.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.pedido.application.gateway.ClienteApplicationGateway
import br.com.fiap.pedido.application.gateway.ProdutoApplicationGateway
import br.com.fiap.pedido.domain.entities.Combo
import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.domain.entities.Produto
import br.com.fiap.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.pedido.domain.entities.extension.toStatusDTO
import br.com.fiap.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.pedido.domain.usecases.QrCodeDomainUseCase
import br.com.fiap.pedido.infrastructure.web.client.dto.extension.toDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class PedidoApplicationControllerTest {

    @Mock
    private lateinit var pedidoDomainUseCase: PedidoDomainUseCase

    @Mock
    private lateinit var produtoApplicationGateway: ProdutoApplicationGateway

    @Mock
    private lateinit var clienteApplicationGateway: ClienteApplicationGateway

    @Mock
    private lateinit var qrCodeDomainUseCase: QrCodeDomainUseCase

    @InjectMocks
    private lateinit var pedidoApplicationController: PedidoApplicationController

    private val pedidoRequestValido = criarPedidoRequestValido()
    private val pedido = pedido()
    private val produto = produto()
    private val pedidoProduto = Combo(null, produto, 1)

    @Test
    fun `deve criar pedido com sucesso`() {
        `when`(produtoApplicationGateway.get(1L)).thenReturn(produto.toDto())
        `when`(pedidoDomainUseCase.create(any())).thenReturn(
            pedido.copy(id = "1L", combo = listOf(pedidoProduto).toMutableList())
        )

        val resultado = pedidoApplicationController.create(pedidoRequestValido)

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado?.id)
        assertEquals(1, resultado?.combo?.size)
    }

    @Test
    fun `deve buscar todos os pedidos`() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val pedidoPage: Page<Pedido> = PageImpl(listOf(pedido))
        `when`(pedidoDomainUseCase.getAll(pageable)).thenReturn(pedidoPage)

        val resultado = pedidoApplicationController.getAll(pageable)

        assertNotNull(resultado)
        assertEquals(1, resultado.totalElements)
        assertEquals(pedido.id, resultado.content[0].id)
    }

    @Test
    fun `deve realizar checkout com sucesso`() {
        `when`(pedidoDomainUseCase.checkout("1L")).thenReturn(pedido)

        val resultado = pedidoApplicationController.checkout("1L")

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado.id)
    }

    @Test
    fun `deve tratar pedido nao encontrado no checkout`() {
        `when`(pedidoDomainUseCase.checkout("1L")).thenReturn(null)

        val resultado = runCatching { pedidoApplicationController.checkout("1L") }

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `deve criar pedido sem produtos`() {
        `when`(pedidoDomainUseCase.create(any())).thenReturn(pedido.copy(id = "1L", combo = mutableListOf()))

        val resultado = pedidoApplicationController.create(criarPedidoRequestSemProdutos())

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado?.id)
        assertTrue(resultado?.combo.isNullOrEmpty())
    }

    @Test
    fun `deve tratar pedido nao encontrado durante checkout`() {
        `when`(pedidoDomainUseCase.checkout("1L")).thenReturn(null)

        val resultado = runCatching { pedidoApplicationController.checkout("1L") }

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `deve buscar todos os pedidos corretamente`() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val pedidoPage: Page<Pedido> = PageImpl(listOf(pedido))
        `when`(pedidoDomainUseCase.getAll(pageable)).thenReturn(pedidoPage)

        val resultado = pedidoApplicationController.getAll(pageable)

        assertNotNull(resultado)
        assertEquals(1, resultado.totalElements)
        assertEquals(pedido.id, resultado.content[0].id)
        verify(pedidoDomainUseCase).getAll(pageable)
    }

    private fun criarPedidoRequestSemProdutos(): PedidoRequest {
        return PedidoRequest(
            "12345678909",
            emptyList()
        )
    }

    @Test
    fun `deve retornar statusDTO quando pedido encontrado`() {
        val pedidoId = "1L"
        val pedido = pedido().copy(id = pedidoId)
        `when`(pedidoDomainUseCase.findPedidoById(pedidoId)).thenReturn(pedido)

        val resultado = pedidoApplicationController.getStatusById(pedidoId)

        assertEquals(pedido.toStatusDTO(), resultado)
    }

    @Test
    fun `deve atualizar o status do pedido com sucesso`() {
        val pedidoId = "1L"
        val novoStatus = StatusPedido.EM_PREPARACAO
        val pedido = pedido().copy(id = pedidoId)

        `when`(pedidoDomainUseCase.findPedidoById(pedidoId)).thenReturn(pedido)

        `when`(pedidoDomainUseCase.updateStatus(eq(pedido), eq(novoStatus))).thenReturn(
            pedido.copy(status = novoStatus)
        )

        val resultado = pedidoApplicationController.updateStatus(pedidoId, novoStatus)

        assertEquals(novoStatus, resultado.status)
    }

    @Test
    fun `deve executar webhook com sucesso quando mercadoPagoEnabled desligado`() {
        val pedidoId = "1"
        val orderId = "orderId"
        val webhookPedidoRequest = criarWebhookPedidoRequest()
        val pedido = pedido().copy(id = pedidoId)

        `when`(pedidoDomainUseCase.findPedidoById(pedidoId)).thenReturn(pedido)

        pedidoApplicationController.webhook(orderId, webhookPedidoRequest)

        verify(pedidoDomainUseCase).closePedidoPagamento(eq(pedido), eq(null))
    }

    private fun criarWebhookPedidoRequest(): WebhookPedidoRequest {
        return WebhookPedidoRequest(
            "payment.update",
            "v1",
            Data("1"),
            "2021-11-01T02:02:02Z",
            "1",
            false,
            "payment",
            1
        )
    }


    private fun criarPedidoRequestValido(): PedidoRequest {
        return PedidoRequest(
            "12345678909",
            listOf(ComboRequest(1, 1))
        )
    }

    private fun pedido(): Pedido {
        return Pedido(
            "1L",
            StatusPedido.RECEBIDO,
            StatusPagamento.AGUARDANDO_APROVACAO,
            "123.456.789-09",
            emptyList<Combo>().toMutableList()
        )
    }

    private fun produto(): Produto {
        return Produto(
            1L,
            "Hamburguer",
            "LANCHE",
            "",
            BigDecimal.valueOf(10.0)
        )
    }
}
