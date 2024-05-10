package br.com.fiap.lanchonete.pedido.application.gateway.impl

import br.com.fiap.lanchonete.pedido.application.gateway.MercadoPagoQrCodeGateway
import br.com.fiap.lanchonete.pedido.config.IntegrationProperties
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.MercadoPagoClient
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.extension.toGenerateQrCodeRequest
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.springframework.stereotype.Service

@Service
class MercadoPagoQrCodeGatewayImpl(
    private val mercadoPagoClient: MercadoPagoClient,
    private val integrationProperties: IntegrationProperties) : MercadoPagoQrCodeGateway {

    override fun generateQrCode(pedido: Pedido): String? {
        return mercadoPagoClient.generateQrCode(
            "Bearer ${this.integrationProperties.mercadoPago?.bearerToken}",
            integrationProperties.mercadoPago?.collectorId,
            integrationProperties.mercadoPago?.posId,
            pedido.toGenerateQrCodeRequest()
        )?.qrData
    }

    override fun getOrder(orderId: String): GetOrderResponse {
        return mercadoPagoClient.getOrder(
            token = "Bearer ${this.integrationProperties.mercadoPago?.bearerToken}",
            orderId = orderId
        )
    }
}