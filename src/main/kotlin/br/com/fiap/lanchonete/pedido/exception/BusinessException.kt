package br.com.fiap.lanchonete.pedido.exception

import br.com.fiap.lanchonete.pedido.exception.enums.ExceptionEnum

class BusinessException(val exceptionEnum: ExceptionEnum,
                        val messages: List<String> = emptyList()) : Exception()