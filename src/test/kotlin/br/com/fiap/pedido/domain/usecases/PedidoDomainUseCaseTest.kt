package br.com.fiap.pedido.domain.usecases

import br.com.fiap.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.pedido.domain.entities.Combo
import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.pedido.exception.BusinessException
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

@ExtendWith(MockitoExtension::class)
class PedidoDomainUseCaseTest {

    @InjectMocks
    lateinit var pedidoDomainUseCase: PedidoDomainUseCase

    @Mock
    lateinit var pedidoRepositoryGateway: PedidoRepositoryGateway

    private val pedido =
        Pedido(UUID.randomUUID().toString(), StatusPedido.RECEBIDO , StatusPagamento.AGUARDANDO_APROVACAO, "123.456.789-09", emptyList<Combo>().toMutableList())
    private val orderResponse =
        GetOrderResponse(status = GetOrderResponse.STATUS_CLOSED, externalReference = "1", orderStatus = "")


    @Test
    fun `test create should save and return pedido`() {
        `when`(pedidoRepositoryGateway.save(pedido)).thenReturn(pedido)

        val result = pedidoDomainUseCase.create(pedido)

        assertEquals(pedido, result)
        verify(pedidoRepositoryGateway).save(pedido)
    }

    @Test
    fun `test getAll should return pageable pedidos`() {
        val pageable = PageRequest.of(0, 10)
        val pedidos = PageImpl(listOf(pedido))
        `when`(pedidoRepositoryGateway.findAll(pageable)).thenReturn(pedidos)

        val result = pedidoDomainUseCase.getAll(pageable)

        assertEquals(pedidos.toString(), result.toString())

        verify(pedidoRepositoryGateway).findAll(pageable)
    }

    @Test
    fun `deve alterar status para EM_PREPARACAO se o pedido estiver RECEBIDO e pagamento APROVADO`() {
        val pedidoRecebido = pedido.copy(status = StatusPedido.RECEBIDO, pagamento = StatusPagamento.APROVADO)
        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(pedidoRecebido)
        `when`(pedidoRepositoryGateway.save(pedidoRecebido)).thenReturn(pedidoRecebido.copy(status = StatusPedido.EM_PREPARACAO))

        val resultado = pedidoDomainUseCase.checkout(pedido.id!!)

        assertEquals(StatusPedido.EM_PREPARACAO, resultado.status)
    }

    @Test
    fun `deve alterar status para EM_PREPARACAO se o pedido estiver RECEBIDO e pagamento RECUSADO`() {
        val pedidoRecebido = pedido.copy(status = StatusPedido.RECEBIDO, pagamento = StatusPagamento.RECUSADO)
        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(pedidoRecebido)

        assertThrows <BusinessException> {
            pedidoDomainUseCase.checkout(pedido.id!!)
        }
    }

    @Test
    fun `deve lancar excecao se o status do pedido nao for RECEBIDO`() {

        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(pedido.copy(status = StatusPedido.EM_PREPARACAO))

        assertThrows <BusinessException> {
            pedidoDomainUseCase.checkout(pedido.id!!)
        }
    }

    @Test
    fun `deve lancar excecao se o pedido nao for encontrado`() {

        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(null)

        assertThrows <BusinessException> {
            pedidoDomainUseCase.checkout(pedido.id!!)
        }
    }


    @Test
    fun `deve atualizar o status do pedido com sucesso`() {
        val pedidoId = UUID.randomUUID().toString()
        val novoStatus = StatusPedido.EM_PREPARACAO
        val pedido = pedido.copy(id = pedidoId)

        `when`(pedidoRepositoryGateway.save(pedido)).thenReturn(pedido)

        assertEquals(StatusPedido.RECEBIDO, pedido.status)
        assertEquals(novoStatus, pedidoDomainUseCase.updateStatus(pedido, novoStatus).status)
    }

    @Test
    fun `deve validar pedido com pagamento aprovado`() {
        val resultado = pedidoDomainUseCase
            .validatePedidoPagamento(orderResponse.copy(orderStatus = GetOrderResponse.PAID_STATUS))
        assertTrue(resultado)
    }

    @Test
    fun `deve fechar pedido com pagamento aprovado`() {
        pedidoDomainUseCase
            .closePedidoPagamento(pedido, orderResponse.copy(orderStatus = GetOrderResponse.PAID_STATUS))
        assertEquals(StatusPagamento.APROVADO, pedido.pagamento)
    }

    @Test
    fun `nao deve validar pedido com pagamento nao aprovado`() {
        val resultado = pedidoDomainUseCase
            .validatePedidoPagamento(orderResponse.copy(orderStatus = "status_foo"))
        assertFalse(resultado)
    }

    @Test
    fun `deve recusar pedido com pagamento nao aprovado`() {
        pedidoDomainUseCase
            .closePedidoPagamento(pedido, orderResponse.copy(orderStatus = "status_foo"))
        assertEquals(StatusPagamento.RECUSADO, pedido.pagamento)
    }
}