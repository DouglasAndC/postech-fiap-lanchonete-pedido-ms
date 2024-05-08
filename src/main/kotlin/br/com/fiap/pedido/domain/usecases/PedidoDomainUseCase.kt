package br.com.fiap.pedido.domain.usecases

import br.com.fiap.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.pedido.domain.error.PedidoExceptionEnum
import br.com.fiap.pedido.exception.BusinessException
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PedidoDomainUseCase(private val pedidoRepositoryGateway: PedidoRepositoryGateway) {


    fun findPedidoById(id: String) =
        pedidoRepositoryGateway.findPedidoById(id)
        ?: throw BusinessException(PedidoExceptionEnum.PEDIDO_NOT_FOUND)

    @Transactional
    fun create(pedido: Pedido) = pedidoRepositoryGateway.save(pedido)

    fun getAll(pageable: Pageable): Page<Pedido> {
        val pedidos = pedidoRepositoryGateway.findAll(pageable).content

        val statusPriority = mapOf(
            StatusPedido.PRONTO to 1,
            StatusPedido.EM_PREPARACAO to 2,
            StatusPedido.RECEBIDO to 3)

        val mutablePedidos = pedidos.toMutableList()
        mutablePedidos.sortWith(compareBy({ statusPriority[it.status] }, { it.status }))

        return PageImpl(
            mutablePedidos,
            PageRequest.of(pageable.pageNumber, pageable.pageSize),
            mutablePedidos.size.toLong())
    }

    @Transactional
    fun checkout(id: String) =
        findPedidoById(id).also {
            if(it.status == StatusPedido.RECEBIDO){
                if(it.pagamento != StatusPagamento.APROVADO){
                    throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_PAGAMENTO_INVALID)
                }
                it.status = StatusPedido.EM_PREPARACAO
                pedidoRepositoryGateway.save(it)
            }else{
                throw BusinessException(PedidoExceptionEnum.PEDIDO_STATUS_INVALID)
            }
        }
    @Transactional
    fun updatePedido(pedido: Pedido):Pedido{
        return pedidoRepositoryGateway.save(pedido)
    }
    fun updateStatus(pedido: Pedido, statusPedido: StatusPedido): Pedido{
        pedido.status = statusPedido

        return updatePedido(pedido)
    }

    fun validatePedidoPagamento(orderResponse: GetOrderResponse?): Boolean{
        return orderResponse?.status == GetOrderResponse.STATUS_CLOSED &&
                orderResponse.orderStatus == GetOrderResponse.PAID_STATUS
    }

    @Transactional
    fun closePedidoPagamento(pedido: Pedido, orderResponse: GetOrderResponse?){
        if(validatePedidoPagamento(orderResponse)){
            pedido.pagamento = StatusPagamento.APROVADO
        } else {
            pedido.pagamento = StatusPagamento.RECUSADO
        }
        updatePedido(pedido)
    }

}