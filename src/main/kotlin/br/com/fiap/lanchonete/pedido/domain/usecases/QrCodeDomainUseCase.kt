package br.com.fiap.lanchonete.pedido.domain.usecases

import br.com.fiap.lanchonete.pedido.application.gateway.MercadoPagoQrCodeGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import org.springframework.stereotype.Service

@Service
class QrCodeDomainUseCase(private val qrCodeGateway: MercadoPagoQrCodeGateway) {

    fun generateQrCode(pedido: Pedido) = qrCodeGateway.generateQrCode(pedido)
    fun getOrder(orderId: String) = qrCodeGateway.getOrder(orderId)

}