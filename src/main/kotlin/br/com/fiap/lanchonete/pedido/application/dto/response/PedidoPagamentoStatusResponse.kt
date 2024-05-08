package br.com.fiap.lanchonete.pedido.application.dto.response

import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPagamento

data class PedidoPagamentoStatusResponse(
    val id: String?,
    val pagamento: StatusPagamento?
)