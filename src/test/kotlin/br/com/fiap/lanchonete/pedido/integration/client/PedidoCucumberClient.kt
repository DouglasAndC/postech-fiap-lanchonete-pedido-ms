package br.com.fiap.lanchonete.pedido.integration.client

import br.com.fiap.lanchonete.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
@FeignClient(
    name = "PedidoCucumberClient",
    url = "\${server.host}:\${server.port}\${server.servlet.context-path}"
)
interface PedidoCucumberClient {

    @PostMapping("/pedidos")
    fun create(@RequestBody pedidoRequest: PedidoRequest): ResponseEntity<PedidoResponse>

    @GetMapping("/pedidos/{id}")
    fun getStatusById(@PathVariable("id") id: String): ResponseEntity<PedidoPagamentoStatusResponse>

    @GetMapping("/pedidos")
    fun getAll(pageable: Pageable): ResponseEntity<Page<PedidoResponse>>

    @PatchMapping("/pedidos/{id}/status/{status}")
    fun updateStatus(@PathVariable("id") id: String,
                     @PathVariable("status") status: String): ResponseEntity<PedidoResponse>

    @PatchMapping("/pedidos/{id}")
    fun checkout(@PathVariable("id") id: String): ResponseEntity<PedidoResponse>

    @PostMapping("/pedidos/webhook")
    fun webhook(@RequestBody webhookPedidoRequest: WebhookPedidoRequest,
                @RequestParam("id") orderId: String): ResponseEntity<JvmType.Object>
}