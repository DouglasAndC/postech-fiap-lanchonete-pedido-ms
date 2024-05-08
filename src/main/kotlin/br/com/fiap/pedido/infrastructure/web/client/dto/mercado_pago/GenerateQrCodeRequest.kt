package br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class GenerateQrCodeRequest(
    val description: String,
    @JsonProperty("external_reference")
    val externalReference: String,
    val items: List<Item>,
    @JsonProperty("notification_url")
    val notificationUrl: String,
    val title: String,
    @JsonProperty("total_amount")
    val totalAmount: BigDecimal
)

