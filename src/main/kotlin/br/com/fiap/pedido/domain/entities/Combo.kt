package br.com.fiap.pedido.domain.entities

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.UUID

@Document
data class Combo(

    @Id
    var id: String? = UUID.randomUUID().toString(),
    var produto: Produto,
    var quantidade: Int = 1,
){
    fun incrementarQuantidade() {
        quantidade += 1
    }

    fun calcularPrecoTotal(): BigDecimal {
        return produto.preco.multiply(quantidade.toBigDecimal())
    }

    override fun toString(): String {
        return "Combo(id=$id, produto=${produto.id}, quantidade=$quantidade"
    }
}
