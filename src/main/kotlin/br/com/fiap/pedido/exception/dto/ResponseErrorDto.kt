package br.com.fiap.pedido.exception.dto

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class ResponseErrorDto(
    val timestamp: String = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
    val status: Int,
    val error: String,
    var messages: List<String> = emptyList()
)
