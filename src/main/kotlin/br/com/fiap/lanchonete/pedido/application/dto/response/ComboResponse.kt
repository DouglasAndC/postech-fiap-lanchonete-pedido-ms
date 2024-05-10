package br.com.fiap.lanchonete.pedido.application.dto.response

import br.com.fiap.lanchonete.pedido.domain.entities.Produto


data class ComboResponse(val id: String?, val quantidade: Int?, val produto: Produto?)
