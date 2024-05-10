package br.com.fiap.lanchonete.pedido.application.gateway.impl

import br.com.fiap.lanchonete.pedido.exception.BusinessException
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.ClienteClient
import br.com.fiap.lanchonete.pedido.infrastructure.web.client.dto.produto.ClienteDto
import br.com.fiap.lanchonete.pedido.infrastructure.web.exception.ClienteClientExceptionEnum
import feign.FeignException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

class ClienteApplicationGatewayImplTest {

    @Mock
    private lateinit var clienteClient: ClienteClient

    @InjectMocks
    private lateinit var clienteApplicationGateway: ClienteApplicationGatewayImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `testar findByCpf com sucesso`() {
        val cpf = "12345678900"
        val clienteDto = ClienteDto(cpf, "david smith", "email@tes.com")
        whenever(clienteClient.findByCpf(cpf)).thenReturn(clienteDto)

        val result = clienteApplicationGateway.findByCpf(cpf)

        assertEquals(clienteDto, result)
    }

    @Test
    fun `testar findByCpf quando cliente não é encontrado`() {
        val cpf = "12345678900"
        doThrow(FeignException.NotFound::class)
            .`when`(clienteClient).findByCpf(cpf)

        val exception = assertThrows<BusinessException> {
            clienteApplicationGateway.findByCpf(cpf)
        }

        assertEquals(ClienteClientExceptionEnum.CLIENTE_NOT_FOUND, exception.exceptionEnum)
    }
}
