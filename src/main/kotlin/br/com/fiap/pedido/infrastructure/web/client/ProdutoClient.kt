package br.com.fiap.pedido.infrastructure.web.client

import br.com.fiap.pedido.infrastructure.web.client.dto.produto.ProdutoDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Service
@FeignClient(name = "produtoClient", url = "\${integration.produto.service}")
interface ProdutoClient {
    @GetMapping("/produto/{id}")
    @ResponseBody
    fun get(@PathVariable id: Long): ProdutoDto
}
