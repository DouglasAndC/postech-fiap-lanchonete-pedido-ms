package br.com.fiap.pedido.infrastructure.web.exception

import br.com.fiap.pedido.exception.dto.ResponseErrorDto
import br.com.fiap.pedido.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ProdutoClientExceptionEnum(private val error: String,
                                      private val httpStatusCode: HttpStatus
) : ExceptionEnum {

    PRODUTO_NOT_FOUND("Produto inválido para associação do Pedido", HttpStatus.BAD_REQUEST);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}