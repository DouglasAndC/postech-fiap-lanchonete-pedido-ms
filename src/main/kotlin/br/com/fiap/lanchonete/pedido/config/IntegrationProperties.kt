package br.com.fiap.lanchonete.pedido.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "integration")
class IntegrationProperties (
    val mercadoPago: MercadoPago?,
    val cliente: Cliente?,
    val produto: Produto?
) {
    data class MercadoPago(
        val service: String = "",
        val enabled: Boolean = false,
        val bearerToken: String = "",
        val collectorId: String = "",
        val posId: String = ""
    )

    data class Cliente(
        val service: String,
    )

    data class Produto(
        val service: String
    )
}