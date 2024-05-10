package br.com.fiap.lanchonete.pedido.application.dto.request


data class ComboRequest(val produtoId: Long, val quantidade : Int = 1)
