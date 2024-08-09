package br.com.fiap.lanchonete.pedido.infrastructure.mq

import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToCozinhaMessageSenderGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.file.Files
import kotlin.io.path.Path

@Service
class PedidoToCozinhaMessageSender(@Value("\${app.mq.cozinha.username}") private val username: String,
                                   @Value("\${app.mq.cozinha.password}") private val password: String,
                                   @Value("\${app.mq.cozinha.brokerURL}") private val brokerURL: String,
                                   @Value("\${app.mq.cozinha.queueName}") private val queueName: String,
                                   @Value("\${app.mq.port}") private val port: Int,
                                   @Autowired private val messageSenderService: MessageSenderService) :
    PedidoToCozinhaMessageSenderGateway {

    override fun sendMessageToCozinha(pedido: Pedido) {

        val secretPath = Path(password)

        messageSenderService.sendMessage(username, Files.readString(secretPath), brokerURL, queueName, pedido.copy(clienteCpf = null), port)
    }
}