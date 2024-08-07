package br.com.fiap.lanchonete.pedido.infrastructure.mq

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.springframework.stereotype.Component


@Component
class ActiveMQConnectionFactoryWrapper {

    fun createConnection(username: String, password: String, host: String,port: Int): Connection {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = username
        connectionFactory.password = password
        connectionFactory.host = host
        connectionFactory.port = port
        return connectionFactory.newConnection()
    }

}