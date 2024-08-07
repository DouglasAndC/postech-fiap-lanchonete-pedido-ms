package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.domain.entities.Pedido

fun interface PedidoToClienteMessageSenderGateway {
    fun sendMessageToCliente(pedido: Pedido)
}
