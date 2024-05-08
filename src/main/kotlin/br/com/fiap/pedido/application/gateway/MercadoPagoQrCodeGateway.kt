package br.com.fiap.pedido.application.gateway

import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse

interface MercadoPagoQrCodeGateway {
    fun generateQrCode(pedido: Pedido): String?
    fun getOrder(orderId: String): GetOrderResponse
}