package br.com.fiap.lanchonete.pedido.integration

import br.com.fiap.lanchonete.pedido.application.dto.request.ComboRequest
import br.com.fiap.lanchonete.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.lanchonete.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.pedido.infrastructure.repository.PedidoMongoRepository
import br.com.fiap.lanchonete.pedido.integration.client.PedidoCucumberClient
import br.com.fiap.lanchonete.pedido.integration.client.WireMockConfig.Companion.setupCliente
import com.github.tomakehurst.wiremock.WireMockServer
import feign.FeignException
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class PedidoHttpControllerSteps(@Autowired private val wireMockServer: WireMockServer,
                                @Autowired private val pedidoCucumberClient: PedidoCucumberClient,
                                @Autowired private val pedidoMongoRepository: PedidoMongoRepository) {

    private lateinit var idPedido: String
    private lateinit var pedidoRequest: PedidoRequest
    private lateinit var response: ResponseEntity<Any>

    companion object {
        const val CUSTOMER_CPF : String = "123.456.789-00"
    }

    private val cpf : String = CUSTOMER_CPF

    @Given("tenho um pedido válido")
    fun tenho_um_pedido_valido() {
        pedidoRequest = PedidoRequest(cpf, listOf(ComboRequest(1, 1)))
    }

    @When("solicito a criação do pedido")
    fun solicito_a_criacao_do_pedido() {
        setupCliente(wireMockServer)
        try {
            response = pedidoCucumberClient.create(pedidoRequest) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @Then("^a API deve retornar o status \"(\\d+)\"")
    fun a_api_deve_retornar_o_pedido_criado_com_status(status: Int) {
        assertEquals(response.statusCode.value(), status)
    }

    @Given("^um pedido existente com o id \"([^\"]*)\"$")
    fun um_pedido_existente_com_o_id(id: String) {
        idPedido = id
        pedidoMongoRepository.save(Pedido(id, StatusPedido.PRONTO, StatusPagamento.APROVADO))
    }

    @When("^eu envio uma requisição de consulta de status$")
    fun eu_envio_uma_requisicao_de_consulta_de_status() {
        response = try {
            pedidoCucumberClient.getStatusById(idPedido) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity(HttpStatus.valueOf(e.status()))
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
        try {
            response = pedidoCucumberClient.getAll(Pageable.unpaged()) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @And("^a API deve retornar a lista paginada de pedidos")
    fun a_api_deve_retornar_a_lista_paginada_de_pedidos_com_status_OK(status: Int) {
    }

    @When("^atualizo o status do pedido \"([^\"]*)\" \"([^\"]*)\"$")
    fun atualizo_o_status_do_pedido(id: String, status: String) {
        try {
            response = pedidoCucumberClient.updateStatus(id, status) as ResponseEntity<Any>
        } catch (e: FeignException){
            ResponseEntity<Any>(HttpStatus.valueOf(e.status()))
        }
    }

    @Then("^o status do pedido deve ser \"([^\"]*)\"$")
    fun o_status_do_pedido_deve_ser(status: String) {
        assertEquals((response as ResponseEntity<PedidoResponse>).body?.status.toString(), status)
    }

    @When("^eu envio uma requisição POST para \"([^\"]*)\" com o payload do webhook$")
    fun eu_envio_uma_requisicao_post_para_com_o_payload_do_webhook(endpoint: String) {
    }

    @Then("^a API deve atualizar o status do pagamento do pedido$")
    fun a_api_deve_atualizar_o_status_do_pagamento_do_pedido() {
    }

}