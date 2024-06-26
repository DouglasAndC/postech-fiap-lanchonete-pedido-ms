package br.com.fiap.lanchonete.pedido.application.gateway.impl

import br.com.fiap.lanchonete.pedido.application.gateway.ClienteApplicationGateway
import br.com.fiap.lanchonete.pedido.exception.BusinessException
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.ClienteClient
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ClienteDto
import br.com.fiap.lanchonete.pedido.infrastructure.web.exception.ClienteClientExceptionEnum
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class ClienteApplicationGatewayImpl(
    private val client: ClienteClient) : ClienteApplicationGateway {

    override fun findByCpf(cpf: String) : ClienteDto{
        try {
            return client.findByCpf(cpf)
        } catch (e: FeignException.NotFound) {
            throw BusinessException(ClienteClientExceptionEnum.CLIENTE_NOT_FOUND)
        }
    }

}