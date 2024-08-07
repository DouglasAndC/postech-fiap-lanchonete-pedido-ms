package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.domain.entities.Pedido

fun interface PedidoToCozinhaMessageSenderGateway {
    fun sendMessageToCozinha(pedido: Pedido)
}