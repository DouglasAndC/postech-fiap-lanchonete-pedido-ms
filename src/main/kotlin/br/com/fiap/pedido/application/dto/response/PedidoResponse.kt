package br.com.fiap.pedido.application.dto.response

import br.com.fiap.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import java.time.LocalDateTime
import java.util.UUID

data class PedidoResponse(
    val id: String?,
    val status: StatusPedido?,
    val pagamento: StatusPagamento?,
    val cliente: String?,
    val combo: List<ComboResponse>,
    val qr: String?
)