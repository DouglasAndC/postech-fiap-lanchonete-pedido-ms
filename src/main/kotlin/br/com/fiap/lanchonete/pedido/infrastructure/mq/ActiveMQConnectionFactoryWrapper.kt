package br.com.fiap.lanchonete.pedido.infrastructure.mq

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class ActiveMQConnectionFactoryWrapper(@Value("\${app.mq.ssl}") private val ssl: Boolean) {

    fun createConnection(username: String, password: String, host: String,port: Int): Connection {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = username
        connectionFactory.password = password
        connectionFactory.host = host
        connectionFactory.port = port
        if(ssl){
            connectionFactory.useSslProtocol()
        }
        return connectionFactory.newConnection()
    }

}