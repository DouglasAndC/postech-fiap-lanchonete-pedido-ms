package br.com.fiap.lanchonete.pedido.application.controller

import br.com.fiap.lanchonete.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.pedido.application.gateway.ClienteApplicationGateway
import br.com.fiap.lanchonete.pedido.application.gateway.ProdutoApplicationGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.pedido.domain.entities.extension.toDTO
import br.com.fiap.lanchonete.pedido.domain.entities.extension.toStatusDTO
import br.com.fiap.lanchonete.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.lanchonete.pedido.domain.usecases.QrCodeDomainUseCase
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.extension.toModel
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.mercado_pago.GetOrderResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PedidoApplicationController(private val pedidoDomainUseCase: PedidoDomainUseCase,
                                  private val produtoApplicationGateway: ProdutoApplicationGateway,
                                  private val clienteApplicationGateway: ClienteApplicationGateway,
                                  private val qrCodeDomainUseCase: QrCodeDomainUseCase,
                                    ) {

    @Value("\${integration.mercado-pago.enabled}") private val mercadoPagoEnabled: Boolean = false

    fun create(pedidoRequest: PedidoRequest): PedidoResponse? {

        val cliente = pedidoRequest.cpf?.let {clienteApplicationGateway.findByCpf(it)}

        val pedido = Pedido(
            id = UUID.randomUUID().toString(),
            clienteCpf = cliente?.cpf,
            status = StatusPedido.RECEBIDO,
            combo = emptyList<Combo>().toMutableList()
        )

        pedidoRequest.combo.forEach{
            pd -> pedido.addProduto(produtoApplicationGateway.get(pd.produtoId).toModel())
        }
        var qr = ""
        if(mercadoPagoEnabled){
            qr = qrCodeDomainUseCase.generateQrCode(pedido).toString()
        }
        return pedidoDomainUseCase.create(pedido)
            .toDTO(qr)
    }

    fun getAll(pageable: Pageable): Page<PedidoResponse> {
        val page = pedidoDomainUseCase.getAll(pageable)
        val pedidoDtos = page.content.map { it.toDTO() }
        return PageImpl(pedidoDtos, pageable, page.totalElements)
    }

    fun checkout(id: String): PedidoResponse =
            pedidoDomainUseCase.checkout(id).toDTO()

    fun getStatusById(id: String): PedidoPagamentoStatusResponse? =
        pedidoDomainUseCase.findPedidoById(id).toStatusDTO()

    fun updateStatus(id: String, statusPedido: StatusPedido) =
        pedidoDomainUseCase.updateStatus(pedidoDomainUseCase.findPedidoById(id), statusPedido).toDTO()

    fun webhook(orderId: String, webhookPedidoRequest: WebhookPedidoRequest) {
        val pedido = pedidoDomainUseCase.findPedidoById(webhookPedidoRequest.data.id)
        if (mercadoPagoEnabled) {
            val orderResponse = qrCodeDomainUseCase.getOrder(orderId)
            orderResponse.let {
                pedidoDomainUseCase.closePedidoPagamento(pedido, it)
            }
        } else {
            pedidoDomainUseCase.closePedidoPagamento(pedido, GetOrderResponse(status = "closed", externalReference = null,orderStatus = "paid"))
        }
    }

}