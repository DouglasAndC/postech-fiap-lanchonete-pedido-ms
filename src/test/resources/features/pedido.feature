Feature: Gerenciar Pedidos

Feature: Gerenciar Pedidos

  Scenario: Criar um novo pedido
    Given tenho um pedido válido
    When solicito a criação do pedido
    Then a API deve retornar o status "201"

  Scenario Outline: Consultar status do pedido
    Given um pedido existente com o id "<id>"
    When eu envio uma requisição de consulta de status
    Then a API deve retornar o status "200"

    Examples:
      | id                                     |
      | 4a7ce830-6092-4e86-9422-1b048cdea257   |

  Scenario: Listar todos os pedidos (paginado)
    Given existam alguns pedidos cadastrados
    When eu envio uma requisição para listar os pedidos
    Then a API deve retornar o status "200"

  Scenario Outline: Atualizar o status de um pedido
    Given um pedido existente com o id "<id>"
    When atualizo o status do pedido "<id>" "<status>"
    Then  a API deve retornar o status "200"
    And o status do pedido deve ser "<status>"

    Examples:
      | id                                     | status  |
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6   | PRONTO  |
#
#  Scenario Outline: Realizar checkout de um pedido
#    Given um pedido existente com o id "{id}"
#    When eu envio uma requisição PATCH para "/pedidos/{id}"
#    Then  a API deve retornar o status "200"
#    And o status do pedido deve ser "PAGO"
#
#    Examples:
#      | id      |
#      | 12345   |
#
#  Scenario Outline: Processar webhook do Mercado Pago
#    Given um pedido existente com o id "{id}"
#    When eu envio uma requisição POST para "/pedidos/webhook" com o payload do webhook
#    Then a API deve atualizar o status do pagamento do pedido
#
#    Examples:
#      | id      |
#      | 54321   |
