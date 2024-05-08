package br.com.fiap.pedido.domain.entities.extension

import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.application.dto.response.ComboResponse
import br.com.fiap.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.pedido.application.dto.response.PedidoResponse
import br.com.fiap.pedido.domain.entities.Combo


fun Pedido.toStatusDTO() = PedidoPagamentoStatusResponse(
    id = this.id,
    pagamento = this.pagamento
)

fun Pedido.toDTO(qr: String? = null) = PedidoResponse(
    id = this.id,
    status = this.status,
    pagamento = this.pagamento,
    cliente = this.clienteCpf,
    combo = this.combo.map { it.toDTO() },
    qr = qr
)

fun Combo.toDTO() = ComboResponse(
    id = this.id,
    quantidade = this.quantidade,
    produto = this.produto
)