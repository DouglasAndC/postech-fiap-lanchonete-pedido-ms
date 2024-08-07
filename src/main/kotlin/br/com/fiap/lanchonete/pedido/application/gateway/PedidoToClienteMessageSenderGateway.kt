package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.domain.entities.Pedido

interface PedidoToClienteMessageSenderGateway {
    fun sendMessageToCliente(pedido: Pedido)
}
