package br.com.fiap.lanchonete.pedido.application.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PedidoRequest(
    val cpf: String?,
    @field:NotNull @field:Size(min=1) val combo: List<ComboRequest>)