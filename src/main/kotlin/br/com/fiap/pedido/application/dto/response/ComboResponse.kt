package br.com.fiap.pedido.application.dto.response

import br.com.fiap.pedido.domain.entities.Produto


data class ComboResponse(val id: String?, val quantidade: Int?, val produto: Produto?)
