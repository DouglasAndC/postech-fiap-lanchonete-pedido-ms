package br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago

import com.fasterxml.jackson.annotation.JsonProperty

data class GenerateQrCodeResponse(
    @JsonProperty(value = "in_store_order_id")
    val inStoreOrderId: String?,
    @JsonProperty(value = "qr_data")
    val qrData: String?
)
