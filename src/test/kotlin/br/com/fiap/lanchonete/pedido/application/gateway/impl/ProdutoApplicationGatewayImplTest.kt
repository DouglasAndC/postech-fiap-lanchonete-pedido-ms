package br.com.fiap.lanchonete.pedido.application.gateway.impl

import br.com.fiap.lanchonete.pedido.exception.BusinessException
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.ProdutoClient
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ProdutoDto
import br.com.fiap.lanchonete.pedido.infrastructure.web.exception.ProdutoClientExceptionEnum
import feign.FeignException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever


class ProdutoApplicationGatewayImplTest {

    @Mock
    private lateinit var produtoClient: ProdutoClient

    @InjectMocks
    private lateinit var produtoApplicationGateway: ProdutoApplicationGatewayImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProductSuccess() {
        val expectedProduct = ProdutoDto(
            id = 1,
            nome = "Test Product",
            categoria = "LANCHE",
            descricao = "Test Description",
            preco = 10.00.toBigDecimal()
        )

        whenever(expectedProduct.id?.let { produtoClient.get(it) }).thenReturn(expectedProduct)

        val actualProduct = expectedProduct.id?.let { produtoApplicationGateway.get(it) }

        assertEquals(expectedProduct, actualProduct)
    }

    @Test
    fun testGetProductNotFound() {
        val productId = 1L
        doThrow(FeignException.NotFound::class)
            .`when`(produtoClient).get(productId)

        val exception = assertThrows(BusinessException::class.java) {
            produtoApplicationGateway.get(productId)
        }

        assertEquals(ProdutoClientExceptionEnum.PRODUTO_NOT_FOUND, exception.exceptionEnum)
    }
}