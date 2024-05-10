package br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.mercado_pago

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class Item(
    @JsonProperty("sku_number")
    val skuNumber: String,
    val category: String,
    val title: String,
    val description: String,
    @JsonProperty("unit_price")
    val unitPrice: BigDecimal,
    val quantity: Int,
    @JsonProperty("unit_measure")
    val unitMeasure: String,
    @JsonProperty("total_amount")
    val totalAmount: BigDecimal
)