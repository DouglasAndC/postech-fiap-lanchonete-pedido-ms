package br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.mercado_pago

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetOrderResponse(
    val status: String?,
    @JsonProperty("external_reference")
    val externalReference: String?,
    @JsonProperty("order_status")
    val orderStatus: String?
){
    companion object{
        const val STATUS_CLOSED = "closed"
        const val PAID_STATUS = "paid"
    }
}
