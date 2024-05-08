package br.com.fiap.pedido.application.gateway.impl


import br.com.fiap.pedido.config.IntegrationProperties
import br.com.fiap.pedido.domain.entities.Combo
import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.domain.entities.Produto
import br.com.fiap.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.pedido.infrastructure.web.client.MercadoPagoClient
import br.com.fiap.pedido.infrastructure.web.client.dto.extension.toGenerateQrCodeRequest
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GenerateQrCodeResponse
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MercadoPagoQrCodeGatewayImplTest {

    @Mock
    private lateinit var mercadoPagoClient: MercadoPagoClient

    @Mock
    private lateinit var integrationProperties: IntegrationProperties

    @InjectMocks
    private lateinit var mercadoPagoQrCodeGatewayImpl: MercadoPagoQrCodeGatewayImpl

    private val pedido = Pedido("1",
        StatusPedido.RECEBIDO,
        StatusPagamento.AGUARDANDO_APROVACAO,
        "",
        listOf(Combo(null,
            Produto(1, "", "", "", 10.0.toBigDecimal()), 1)).toMutableList(),
        10.0.toBigDecimal())

    private val orderResponse = GetOrderResponse("1", "1", "1")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `testar generateQrCode com sucesso`() {
        val pedido = Pedido("1", StatusPedido.RECEBIDO, StatusPagamento.AGUARDANDO_APROVACAO, "", mutableListOf(), 10.0.toBigDecimal())
        val expectedQrData = GenerateQrCodeResponse("id", "qrcode")
        val mockedMercadoPago = mock(IntegrationProperties.MercadoPago::class.java)
        `when`(integrationProperties.mercadoPago).thenReturn(mockedMercadoPago)
        `when`(mockedMercadoPago.bearerToken).thenReturn("mockedBearerToken")
        `when`(mockedMercadoPago.collectorId).thenReturn("mockedCollectorId")
        `when`(mockedMercadoPago.posId).thenReturn("mockedPosId")
        `when`(mercadoPagoClient.generateQrCode("Bearer mockedBearerToken", "mockedCollectorId", "mockedPosId", pedido.toGenerateQrCodeRequest())).thenReturn(expectedQrData)

        val qrCode = mercadoPagoQrCodeGatewayImpl.generateQrCode(pedido)

        assertEquals(expectedQrData.qrData, qrCode)
    }

    @Test
    fun `testar generateQrCode com IntegrationProperties mercadoPago nulo`() {
        `when`(integrationProperties.mercadoPago).thenReturn(null)

        val qrCode = mercadoPagoQrCodeGatewayImpl.generateQrCode(pedido)

        assertNull(qrCode)
    }
    @Test
    fun `testar getOrder com sucesso`() {
        val orderId = "mockedOrderId"
        val mockedMercadoPago = mock(IntegrationProperties.MercadoPago::class.java)
        `when`(integrationProperties.mercadoPago).thenReturn(mockedMercadoPago)
        `when`(mockedMercadoPago.bearerToken).thenReturn("mockedBearerToken")
        `when`(mercadoPagoClient.getOrder("Bearer mockedBearerToken", orderId)).thenReturn(orderResponse)

        val response = mercadoPagoQrCodeGatewayImpl.getOrder(orderId)

        assertEquals(orderResponse, response)
    }

}
