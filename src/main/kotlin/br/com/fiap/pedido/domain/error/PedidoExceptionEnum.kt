package br.com.fiap.pedido.domain.error

import br.com.fiap.pedido.exception.dto.ResponseErrorDto
import br.com.fiap.pedido.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class PedidoExceptionEnum(private val error: String,
                                private val httpStatusCode: HttpStatus) : ExceptionEnum {

    PEDIDO_NOT_FOUND("Pedido nao foi encontrado.", HttpStatus.NOT_FOUND),
    PEDIDO_STATUS_INVALID("Status do pedido é invalido para essa operacao.", HttpStatus.BAD_REQUEST),
    PEDIDO_STATUS_PAGAMENTO_INVALID("Status pagamento do pedido é invalido para essa operacao.", HttpStatus.BAD_REQUEST);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}