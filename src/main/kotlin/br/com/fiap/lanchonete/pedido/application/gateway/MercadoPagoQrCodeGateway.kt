package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse

interface MercadoPagoQrCodeGateway {
    fun generateQrCode(pedido: Pedido): String?
    fun getOrder(orderId: String): GetOrderResponse
}