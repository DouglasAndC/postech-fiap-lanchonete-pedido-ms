package br.com.fiap.lanchonete.pedido.exception.enums

import br.com.fiap.lanchonete.pedido.exception.dto.ResponseErrorDto

interface ExceptionEnum {
    fun getResponseError(): ResponseErrorDto
}