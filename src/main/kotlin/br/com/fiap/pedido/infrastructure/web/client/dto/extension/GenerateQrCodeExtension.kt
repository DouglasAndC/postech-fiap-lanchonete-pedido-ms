package br.com.fiap.pedido.infrastructure.web.client.dto.extension

import br.com.fiap.pedido.domain.entities.Pedido
import br.com.fiap.pedido.domain.entities.Combo
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.GenerateQrCodeRequest
import br.com.fiap.pedido.infrastructure.web.client.dto.mercado_pago.Item

fun Pedido.toGenerateQrCodeRequest(): GenerateQrCodeRequest {
    return GenerateQrCodeRequest(
        description = "Pedido do Cliente -${this.clienteCpf ?: "N/I"}",
        externalReference = "reference_${this.id ?: 0}",
        items = this.combo.map { it.toMercadoPagoItemDTO() },
        notificationUrl = "https://www.yourserver.com/notifications",
        title = "Product order",
        totalAmount = this.precoTotal
    )
}

fun Combo.toMercadoPagoItemDTO(): Item {
    return Item(
        skuNumber = "sku_number_${this.id ?: 0}",
        category = this.produto.categoria,
        title = this.produto.nome,
        description = this.produto.descricao,
        unitPrice = this.produto.preco,
        quantity = this.quantidade,
        unitMeasure = "unit",
        totalAmount = this.calcularPrecoTotal()
    )
}