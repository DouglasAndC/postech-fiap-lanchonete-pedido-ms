package br.com.fiap.pedido.steps

import io.cucumber.java.en.*

class PassosControladorHttpPedido {

    @Given("eu faço um POST para a rota {string} com os dados de um pedido válido")
    fun facoUmaSolicitacaoPostParaRotaComDadosPedidoValido(rota: String) {
        // Implementação do código para realizar a requisição POST com um pedido válido
    }

    @Given("eu faço um POST para a rota {string} com dados de pedido inválidos")
    fun facoUmaSolicitacaoPostParaRotaComDadosPedidoInvalidos(rota: String) {
        // Implementação do código para realizar a requisição POST com dados de pedido inválidos
    }

    @Given("eu faço um GET para a rota {string} com um id de pedido válido")
    fun facoUmaSolicitacaoGetParaRotaComIdPedidoValido(rota: String) {
        // Implementação do código para realizar a requisição GET com um id de pedido válido
    }

    @Given("eu faço um GET para a rota {string} com um id de pedido inválido")
    fun facoUmaSolicitacaoGetParaRotaComIdPedidoInvalido(rota: String) {
        // Implementação do código para realizar a requisição GET com um id de pedido inválido
    }

    @Given("eu faço um PUT para a rota {string} com um id de pedido válido e dados de pedido atualizados")
    fun facoUmaSolicitacaoPutParaRotaComIdPedidoValidoEDadosPedidoAtualizados(rota: String) {
        // Implementação de código para realizar a requisição PUT com um id de pedido válido e dados de pedido atualizados
    }

    @Given("eu faço um PUT para a rota {string} com um id de pedido inválido")
    fun facoUmaSolicitacaoPutParaRotaComIdPedidoInvalido(rota: String) {
        // Implementação de código para realizar a requisição PUT com um id de pedido inválido
    }

    @Given("eu faço um DELETE para a rota {string} com um id de pedido válido")
    fun facoUmaSolicitacaoDeleteParaRotaComIdPedidoValido(rota: String) {
        // Implementação do código para realizar a requisição DELETE com um id de pedido válido
    }

    @Given("eu faço um DELETE para a rota {string} com um id de pedido inválido")
    fun facoUmaSolicitacaoDeleteParaRotaComIdPedidoInvalido(rota: String) {
        // Implementação do código para realizar a requisição DELETE com um id de pedido inválido
    }

    @When("a resposta do status HTTP deve ser {int}")
    fun quandoRespostaStatusHttpDeveSer(codigoStatus: Int) {
        // Implementação do código para verificar o status do código HTTP
    }

    @Then("a resposta deve conter um campo {string}")
    fun entaoRespostaDeveConterCampo(campo: String) {
        // Implementação do código para verificar se a resposta contém um determinado campo
    }

    @Then("a resposta deve conter uma mensagem de erro")
    fun entaoRespostaDeveConterMensagemErro() {
        // Implementação do código para verificar se a resposta contém uma mensagem de erro
    }

    @Then("a resposta deve conter os detalhes do pedido")
    fun entaoRespostaDeveConterDetalhesPedido() {
        // Implementação do código para verificar se a resposta contém detalhes do pedido
    }

    @Then("a resposta deve conter os detalhes do pedido atualizado")
    fun entaoRespostaDeveConterDetalhesPedidoAtualizado() {
        // Implementação do código para verificar se a resposta contém detalhes do pedido atualizado
    }
}