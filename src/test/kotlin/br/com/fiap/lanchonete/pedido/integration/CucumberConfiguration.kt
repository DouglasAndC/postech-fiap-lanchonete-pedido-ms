package br.com.fiap.lanchonete.pedido.integration

import br.com.fiap.lanchonete.pedido.PedidoApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension


@AutoConfigureDataMongo
@SpringBootTest(
    properties = ["de.flapdoodle.mongodb.embedded.version=6.0.5"],
    classes = [PedidoApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
)
@CucumberContextConfiguration
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class CucumberConfiguration {

    @Test
    fun example(@Autowired mongoTemplate: MongoTemplate) {
        Assertions.assertThat(mongoTemplate.db).isNotNull()
    }
}