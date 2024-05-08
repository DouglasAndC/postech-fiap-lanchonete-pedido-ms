package br.com.fiap.pedido.infrastructure.web.client.dto.extension

import br.com.fiap.pedido.domain.entities.Produto
import br.com.fiap.pedido.infrastructure.web.client.dto.produto.ProdutoDto

fun Produto.toDto(): ProdutoDto {
    return ProdutoDto(
        id = this.id,
        nome = this.nome,
        categoria = this.categoria,
        descricao = this.descricao,
        preco = this.preco
    )
}

fun ProdutoDto.toModel(): Produto {
    return Produto(
        id = this.id,
        nome = this.nome,
        categoria = this.categoria,
        descricao = this.descricao,
        preco = this.preco
    )
}