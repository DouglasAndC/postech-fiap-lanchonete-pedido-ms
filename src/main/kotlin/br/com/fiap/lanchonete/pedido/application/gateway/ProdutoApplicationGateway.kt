package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ProdutoDto

fun interface ProdutoApplicationGateway {
    fun get(id: Long): ProdutoDto
}
