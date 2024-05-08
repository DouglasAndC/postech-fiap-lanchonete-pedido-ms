package br.com.fiap.pedido.domain.entities

import java.math.BigDecimal

data class Produto (
    val id: Long? = null,
    var nome: String,
    var categoria: String,
    var descricao: String,
    var preco: BigDecimal
)
