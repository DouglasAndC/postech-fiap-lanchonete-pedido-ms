package br.com.fiap.pedido.domain.entities.enums

import br.com.fiap.pedido.domain.error.PedidoExceptionEnum
import br.com.fiap.pedido.exception.BusinessException

enum class StatusPedido {
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;

    companion object {
        fun valueOfStatus(customValue: String): StatusPedido {
            return entries.find { it.name.equals(customValue, ignoreCase = true) }
                ?: throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_INVALID)
        }
    }
}
