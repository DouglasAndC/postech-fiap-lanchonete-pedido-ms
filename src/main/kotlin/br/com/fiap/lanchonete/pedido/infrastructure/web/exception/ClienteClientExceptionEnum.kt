package br.com.fiap.lanchonete.pedido.infrastructure.web.exception

import br.com.fiap.lanchonete.pedido.exception.dto.ResponseErrorDto
import br.com.fiap.lanchonete.pedido.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ClienteClientExceptionEnum(private val error: String,
                                 private val httpStatusCode: HttpStatus
) : ExceptionEnum {

    CLIENTE_NOT_FOUND("Cliente inválido para associação", HttpStatus.BAD_REQUEST);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}