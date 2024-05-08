package br.com.fiap.pedido.exception

import br.com.fiap.pedido.exception.enums.ExceptionEnum

class BusinessException(val exceptionEnum: ExceptionEnum,
                        val messages: List<String> = emptyList()) : Exception()