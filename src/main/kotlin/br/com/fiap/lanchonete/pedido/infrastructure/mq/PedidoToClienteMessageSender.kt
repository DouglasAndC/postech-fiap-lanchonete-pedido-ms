package br.com.fiap.lanchonete.pedido.infrastructure.mq

import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToClienteMessageSenderGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.file.Files
import kotlin.io.path.Path

@Service
class PedidoToClienteMessageSender(@Value("\${app.mq.cliente.username}") private val username: String,
                                  @Value("\${app.mq.cliente.password}") private val password: String,
                                  @Value("\${app.mq.cliente.brokerURL}") private val brokerURL: String,
                                  @Value("\${app.mq.cliente.queueName}") private val queueName: String,
                                  @Value("\${app.mq.port}") private val port: Int,
                                  @Autowired private val messageSenderService: MessageSenderService) :
    PedidoToClienteMessageSenderGateway {

    override fun sendMessageToCliente(pedido: Pedido) {

        val secretPath = Path(password)

        messageSenderService.sendMessage(username, Files.readString(secretPath), brokerURL, queueName, pedido.copy(clienteCpf = null), port)
    }
}