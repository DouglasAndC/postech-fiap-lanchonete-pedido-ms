package br.com.fiap.pedido.infrastructure.web.client

import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GenerateQrCodeRequest
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GenerateQrCodeResponse
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseBody

@Service
@FeignClient(name = "mercadoPagoClient", url = "\${integration.mercado-pago.service}")
interface MercadoPagoClient {

    @PostMapping("/instore/orders/qr/seller/collectors/{collectorId}/pos/{posId}/qrs")
    @ResponseBody
    fun generateQrCode(
        @RequestHeader("Authorization") token: String?,
        @PathVariable collectorId: String?,
        @PathVariable posId: String?,
        @RequestBody request: GenerateQrCodeRequest?
    ): GenerateQrCodeResponse?

    @GetMapping("/merchant_orders/{orderId}")
    @ResponseBody
    fun getOrder(
        @RequestHeader("Authorization") token: String,
        @PathVariable orderId: String
    ): GetOrderResponse
}
