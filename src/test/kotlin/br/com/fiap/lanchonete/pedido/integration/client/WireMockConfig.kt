package br.com.fiap.lanchonete.pedido.integration.client

import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ClienteDto
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ProdutoDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType

@Configuration
class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun wireMocServer() = WireMockServer(8090)


    companion object {
        fun setupCliente(wireMockServer: WireMockServer){
            val objectMapper = ObjectMapper()
            wireMockServer.stubFor(
                get(urlPathMatching("/lanchonete-cliente/api/v1/clientes/[^/]*"))
                    .willReturn(
                        aResponse().withHeader(
                            "Content-Type",
                            MediaType.APPLICATION_JSON_VALUE
                        ).withBody(
                            objectMapper.writeValueAsString(
                                ClienteDto("123.456.789-00", "John SMith", "email@email.com"))
                        )
                    )
            )
            wireMockServer.stubFor(
                get(urlPathMatching("/lanchonete-produto/api/v1/produto/[^/]*"))
                    .willReturn(
                        aResponse().withHeader(
                            "Content-Type",
                            MediaType.APPLICATION_JSON_VALUE
                        ).withBody(
                            objectMapper.writeValueAsString(
                                ProdutoDto(
                                    id = 1,
                                    nome = "produto",
                                    categoria = "LANCHE",
                                    descricao = "lanche",
                                    preco = 10.0.toBigDecimal())
                            )
                        )
                    )
            )
        }
    }
}