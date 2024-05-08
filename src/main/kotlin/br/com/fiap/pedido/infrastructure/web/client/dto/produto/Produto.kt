package br.com.fiap.pedido.infrastructure.web.client.dto.produto

import java.math.BigDecimal

data class ProdutoDto (
    val id: Long? = null,
    var nome: String,
    var categoria: String,
    var descricao: String,
    var preco: BigDecimal
)
