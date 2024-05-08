package br.com.fiap.pedido

import br.com.fiap.pedido.config.IntegrationProperties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(IntegrationProperties::class)
class PedidoApplication

fun main(args: Array<String>) {
    runApplication<PedidoApplication>(*args)
}
