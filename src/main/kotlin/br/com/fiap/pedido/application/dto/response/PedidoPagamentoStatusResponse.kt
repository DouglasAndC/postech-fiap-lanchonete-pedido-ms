package br.com.fiap.pedido.application.dto.response

import br.com.fiap.pedido.domain.entities.enums.StatusPagamento

data class PedidoPagamentoStatusResponse(
    val id: String?,
    val pagamento: StatusPagamento?
)