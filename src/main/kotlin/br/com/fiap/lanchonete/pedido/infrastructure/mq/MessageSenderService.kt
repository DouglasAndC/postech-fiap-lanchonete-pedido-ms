package br.com.fiap.lanchonete.pedido.infrastructure.mq

import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageSenderService(@Autowired private val connectionFactoryWrapper: ActiveMQConnectionFactoryWrapper) {

    private val mapper = jacksonObjectMapper()

    fun sendMessage(username: String, password: String, host: String, queueName: String, pedido: Pedido, port: Int) {
        val connection: Connection = connectionFactoryWrapper.createConnection(username, password, host, port)
        val channel: Channel = connection.createChannel()

        val jsonMessage = mapper.writeValueAsString(pedido)
        channel.queueDeclare(queueName, true, false, false, null)

        channel.basicPublish("", queueName, null, jsonMessage.toByteArray(charset("UTF-8")))

        channel.close()
        connection.close()
    }
}