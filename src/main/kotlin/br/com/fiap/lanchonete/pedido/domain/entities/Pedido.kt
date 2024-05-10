package br.com.fiap.lanchonete.pedido.domain.entities

import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.pedido.domain.entities.enums.StatusPedido
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "pedidos")
data class Pedido(

    @Id
    var id: String?,
    var status: StatusPedido? = null,
    var pagamento: StatusPagamento = StatusPagamento.AGUARDANDO_APROVACAO,
    var clienteCpf: String? = null,
    var combo: MutableList<Combo> = mutableListOf(),
    var precoTotal: BigDecimal = BigDecimal.ZERO
) {
    fun addProduto(produto: Produto) {
        var comboExistente = combo.find { it.produto.id == produto.id }

        if (comboExistente != null) {
            comboExistente.incrementarQuantidade()
        } else {
            var novoCombo = Combo(produto = produto)
            combo.add(novoCombo)
        }

        recalcularPrecoTotal()
    }

    private fun recalcularPrecoTotal() {
        precoTotal = combo.sumOf { it.calcularPrecoTotal() }
    }

    override fun toString() =
        "Pedido(id=$id, cliente-cpf=$clienteCpf, ,precoTotal=$precoTotal, produtos=$combo)"
}