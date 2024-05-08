package br.com.fiap.pedido.steps

import br.com.fiap.pedido.PedidoApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@CucumberContextConfiguration
@AutoConfigureDataMongo
@SpringBootTest(classes = [PedidoApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class CucumberConfiguration {
}