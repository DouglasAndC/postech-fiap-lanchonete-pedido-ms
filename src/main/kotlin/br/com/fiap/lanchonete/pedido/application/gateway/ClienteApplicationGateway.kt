package br.com.fiap.lanchonete.pedido.application.gateway

import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ClienteDto

interface ClienteApplicationGateway {
    fun findByCpf(cpf: String) : ClienteDto
}
