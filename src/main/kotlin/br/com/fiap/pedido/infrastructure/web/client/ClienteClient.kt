package br.com.fiap.pedido.infrastructure.web.client

import br.com.fiap.pedido.infrastructure.web.client.dto.produto.ClienteDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Service
@FeignClient(name = "clienteClient", url = "\${integration.cliente.service}")
interface ClienteClient {
    @GetMapping("/clientes/{cpf}")
    @ResponseBody
    fun findByCpf(@PathVariable cpf: String): ClienteDto
}
