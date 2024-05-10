Feature: Gerenciar Pedidos

  Scenario: Criar um novo pedido
    Given tenho um pedido válido
    When solicito a criação do pedido
    Then a API deve retornar o status "201"

  Scenario: Criar um novo pedido com produtos invalidos
    Given tenho um pedido válido com produtos invalidos
    When solicito a criação do pedido
    Then a API deve retornar o status "400"

  Scenario Outline: Consultar status do pedido
    Given um pedido existente com o id "<id>" status "<status>"
    When eu envio uma requisição de consulta de status
    Then a API deve retornar o status "200"

    Examples:
      | id                                   | status   |
      | 4a7ce830-6092-4e86-9422-1b048cdea257 | RECEBIDO |

  Scenario: Listar todos os pedidos (paginado)
    Given existam alguns pedidos cadastrados
    When eu envio uma requisição para listar os pedidos
    Then a API deve retornar o status "200"

  Scenario Outline: Atualizar o status de um pedido
    Given um pedido existente com o id "<id>" status "<status_i>"
    When atualizo o status do pedido "<id>" "<status_f>"
    Then  a API deve retornar o status "<statusCode>"

    Examples:
      | id                                   | status_i | status_f | statusCode |
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6 | RECEBIDO | PRONTO   | 200        |
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6 | RECEBIDO | TEST     | 400        |

  Scenario Outline: Realizar checkout de um pedido
    Given um pedido existente com o id "<id>" status "<status>"
    When solicito o checkout do pedido
    Then a API deve retornar o status "<statusCode>"

    Examples:
      | id                                   | status   | statusCode |
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6 | RECEBIDO | 200        |
      | 3fa85f64-5717-4562-b3fc-2c963f66afa6 | PRONTO   | 400        |

  Scenario Outline: Processar webhook do Mercado Pago
    Given um pedido existente com o id "<id>" status "<statusPedido>"
    When eu envio uma requisição de webhook
    Then a API deve retornar o status "<status>"

    Examples:
      | id                                   | statusPedido | status |
      | 123e4567-e89b-12d3-a456-426614174000 | PRONTO       | 204    |