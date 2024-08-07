package br.com.fiap.lanchonete.pedido.integration.client

import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToClienteMessageSenderGateway
import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToCozinhaMessageSenderGateway
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class TestMockBeansConfiguration {

    @Bean
    @Primary
    fun pedidoToCozinhaMessageSenderGateway(): PedidoToCozinhaMessageSenderGateway {
        return mock(PedidoToCozinhaMessageSenderGateway::class.java)
    }

    @Bean
    @Primary
    fun pedidoToClienteMessageSenderGateway(): PedidoToClienteMessageSenderGateway {
        return mock(PedidoToClienteMessageSenderGateway::class.java)
    }
}