package br.com.fiap.pedido.infrastructure.web.controller

import br.com.fiap.pedido.application.controller.PedidoApplicationController
import br.com.fiap.pedido.application.dto.request.PedidoRequest
import br.com.fiap.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.pedido.application.dto.response.PedidoResponse
import br.com.fiap.pedido.domain.entities.enums.StatusPedido
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/pedidos")
class PedidoHttpController(private val pedidoApplicationController: PedidoApplicationController) {

    @PostMapping
    fun create(@Valid @RequestBody pedidoRequest: PedidoRequest,
               uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PedidoResponse> {
        val pedidoCreated = pedidoApplicationController.create(pedidoRequest)
        val uri = uriBuilder.path("/api/v1/pedido/{id}").buildAndExpand(pedidoCreated?.id).toUri()
        return ResponseEntity.created(uri).body(pedidoCreated)
    }

    @GetMapping("/{id}")
    fun getStatusById(@PathVariable(name = "id") id: String): ResponseEntity<PedidoPagamentoStatusResponse> =
        ResponseEntity(pedidoApplicationController.getStatusById(id), HttpStatus.OK)


    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<PedidoResponse>> =
        ResponseEntity(pedidoApplicationController.getAll(pageable), HttpStatus.OK)

    @PatchMapping("/{id}/status/{status}")
    fun updateStatus(@PathVariable(name = "id") id: String,
                     @PathVariable(name = "status") status: String): ResponseEntity<PedidoResponse> =
        ResponseEntity(pedidoApplicationController.updateStatus(id, StatusPedido.valueOfStatus(status)), HttpStatus.OK)

    @PatchMapping("/{id}")
    fun checkout(@PathVariable(name = "id") id: String): ResponseEntity<PedidoResponse> =
            ResponseEntity(pedidoApplicationController.checkout(id), HttpStatus.OK)


    @PostMapping("/webhook")
    fun webhook(@RequestBody webhookPedidoRequest: WebhookPedidoRequest,
                @RequestParam("id") orderId: String
    ): ResponseEntity<JvmType.Object> {

        pedidoApplicationController.webhook(orderId, webhookPedidoRequest)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


}

