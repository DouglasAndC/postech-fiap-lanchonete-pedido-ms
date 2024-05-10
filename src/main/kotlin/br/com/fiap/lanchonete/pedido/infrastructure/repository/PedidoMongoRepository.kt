package br.com.fiap.lanchonete.pedido.infrastructure.repository

import br.com.fiap.lanchonete.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoMongoRepository: PedidoRepositoryGateway, MongoRepository<Pedido, Long>