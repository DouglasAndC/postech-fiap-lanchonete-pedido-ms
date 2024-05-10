package br.com.fiap.lanchonete.pedido.application.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

data class WebhookPedidoRequest(
    val action: String,
    @JsonProperty("api_version")
    val apiVersion: String,
    val data: Data,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("date_created")
    val dateCreated: String,
    val id: String,
    @JsonProperty("live_mode")
    val liveMode: Boolean,
    val type: String,
    @JsonProperty("user_id")
    val userId: Long
){
    companion object{
        const val ACTION_UPDATE = "payment.updated"
    }
}

data class Data(
    val id: String
)