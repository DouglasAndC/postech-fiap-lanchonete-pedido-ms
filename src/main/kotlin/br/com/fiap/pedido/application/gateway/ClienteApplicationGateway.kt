package br.com.fiap.pedido.application.gateway

import br.com.fiap.pedido.infrastructure.web.client.dto.produto.ClienteDto

interface ClienteApplicationGateway {
    fun findByCpf(cpf: String) : ClienteDto
}
