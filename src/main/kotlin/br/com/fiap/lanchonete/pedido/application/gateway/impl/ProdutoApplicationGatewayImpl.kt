package br.com.fiap.lanchonete.pedido.application.gateway.impl

import br.com.fiap.lanchonete.pedido.application.gateway.ProdutoApplicationGateway
import br.com.fiap.lanchonete.pedido.exception.BusinessException
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.ProdutoClient
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ProdutoDto
import br.com.fiap.lanchonete.pedido.infrastructure.web.exception.ProdutoClientExceptionEnum
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class ProdutoApplicationGatewayImpl(private val client: ProdutoClient)
    : ProdutoApplicationGateway {
    override fun get(id: Long): ProdutoDto {
        try {
            return client.get(id)
        } catch (e: FeignException.NotFound) {
            throw BusinessException(ProdutoClientExceptionEnum.PRODUTO_NOT_FOUND)
        }
    }
}