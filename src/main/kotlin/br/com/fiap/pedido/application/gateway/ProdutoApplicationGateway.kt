package br.com.fiap.pedido.application.gateway

import br.com.fiap.pedido.infrastructure.web.client.dto.produto.ProdutoDto

interface ProdutoApplicationGateway {
    fun get(id: Long): ProdutoDto
}
