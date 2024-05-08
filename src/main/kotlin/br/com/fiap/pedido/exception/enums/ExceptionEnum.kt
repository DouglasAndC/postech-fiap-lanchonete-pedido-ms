package br.com.fiap.pedido.exception.enums

import br.com.fiap.pedido.exception.dto.ResponseErrorDto

interface ExceptionEnum {
    fun getResponseError(): ResponseErrorDto
}