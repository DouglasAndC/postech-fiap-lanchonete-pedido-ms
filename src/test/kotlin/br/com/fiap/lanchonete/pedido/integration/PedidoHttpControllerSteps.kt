package br.com.fiap.lanchonete.pedido.integration

import br.com.fiap.lanchonete.pedido.application.dto.request.ComboRequest
import br.com.fiap.lanchonete.pedido.application.dto.request.Data
import br.com.fiap.lanchonete.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToClienteMessageSenderGateway
import br.com.fiap.lanchonete.pedido.application.gateway.PedidoToCozinhaMessageSenderGateway
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.pedido.infrastructure.repository.PedidoMongoRepository
import br.com.fiap.lanchonete.pedido.integration.client.PedidoCucumberClient
import br.com.fiap.lanchonete.pedido.integration.client.WireMockConfig.Companion.setupCliente
import com.github.tomakehurst.wiremock.WireMockServer
import feign.FeignException
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class PedidoHttpControllerSteps(@Autowired private val wireMockServer: WireMockServer,
                                @Autowired private val pedidoCucumberClient: PedidoCucumberClient,
                                @Autowired private val pedidoMongoRepository: PedidoMongoRepository,) {

    private lateinit var idPedido: String
    private lateinit var pedidoRequest: PedidoRequest
    private lateinit var response: ResponseEntity<Any>


    @Autowired
    private lateinit var pedidoToCozinhaMessageSenderGateway: PedidoToCozinhaMessageSenderGateway

    @Autowired
    private lateinit var pedidoToClienteMessageSenderGateway: PedidoToClienteMessageSenderGateway

    companion object {
        const val CUSTOMER_CPF : String = "123.456.789-00"
    }

    private val cpf : String = CUSTOMER_CPF

    @Given("tenho um pedido válido")
    fun tenho_um_pedido_valido() {
        pedidoRequest = PedidoRequest(cpf, listOf(ComboRequest(1, 2)))
    }

    @Given("tenho um pedido válido com produtos invalidos")
    fun tenho_um_pedido_valido_com_produtos_invalidos(){
        pedidoRequest = PedidoRequest(cpf, listOf(ComboRequest(2, 2)))
    }

    @When("solicito a criação do pedido")
    fun solicito_a_criacao_do_pedido() {
        setupCliente(wireMockServer)
        response = try {
            pedidoCucumberClient.create(pedidoRequest) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @Then("^a API deve retornar o status \"(\\d+)\"")
    fun a_api_deve_retornar_o_pedido_criado_com_status(status: Int) {
        assertEquals(response.statusCode.value(), status)
    }

    @Given("^um pedido existente com o id \"([^\"]*)\" status \"([^\"]*)\"$")
    fun um_pedido_existente_com_o_id(id: String, status: StatusPedido) {
        idPedido = id

        pedidoMongoRepository.save(Pedido(id, status, StatusPagamento.APROVADO))
    }

    @When("^eu envio uma requisição de consulta de status$")
    fun eu_envio_uma_requisicao_de_consulta_de_status() {
        response = try {
            pedidoCucumberClient.getStatusById(idPedido) as ResponseEntity<Any>
        } catch (e: FeignException) {
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @Then("^o status do pagamento deve ser \"([^\"]*)\"$")
    fun o_status_do_pagamento_deve_ser(statusPagamento: String) {
    }

    @Given("^existam alguns pedidos cadastrados$")
    fun existam_alguns_pedidos_cadastrados() {
        pedidoMongoRepository.save(Pedido(UUID.randomUUID().toString(),
            StatusPedido.PRONTO, StatusPagamento.APROVADO))
    }

    @When("^eu envio uma requisição para listar os pedidos")
    fun eu_envio_uma_requisicao_get_para() {
        response = try {
            pedidoCucumberClient.getAll(Pageable.unpaged()) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @And("^a API deve retornar a lista paginada de pedidos")
    fun a_api_deve_retornar_a_lista_paginada_de_pedidos_com_status_OK(status: Int) {
    }

    @When("^atualizo o status do pedido \"([^\"]*)\" \"([^\"]*)\"$")
    fun atualizo_o_status_do_pedido(id: String, status: String) {
        response = try {
            pedidoCucumberClient.updateStatus(id, status) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @When("solicito o checkout do pedido")
    fun solicito_o_checkout_do_pedido(){
        response = try {
            pedidoCucumberClient.checkout(idPedido) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }


    @When("^eu envio uma requisição de webhook$")
    fun eu_envio_uma_requisicao_de_webhook() {
        response = try {
            pedidoCucumberClient.webhook(
            WebhookPedidoRequest(
            action = WebhookPedidoRequest.ACTION_UPDATE,
            apiVersion = "api_version_placeholder",
            data = Data(idPedido),
            dateCreated = "2024-01-01T00:00:00Z",
            id = idPedido,
            liveMode = true,
            type = "type_placeholder",
            userId = 123456789L
            ), idPedido) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

}