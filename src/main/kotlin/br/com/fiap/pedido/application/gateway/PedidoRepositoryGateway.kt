package br.com.fiap.pedido.application.gateway

import br.com.fiap.pedido.domain.entities.Pedido
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query

interface PedidoRepositoryGateway {
    fun save(pedido: Pedido): Pedido
    fun findAll(pageable: Pageable): Page<Pedido>
    fun findPedidoById(id:String): Pedido?
}