Feature: PedidoHttpController
  Como usuário da API
  Quero criar, recuperar, atualizar e excluir pedidos

  Scenario: Criar um novo pedido
    Given eu faço um POST para a rota "/pedidos" com os dados de um pedido válido
    When a resposta do status HTTP deve ser 201
    Then a resposta deve conter um campo "id"

  Scenario: Tentar criar um pedido com dados inválidos
    Given eu faço um POST para a rota "/pedidos" com dados de pedido inválidos
    When a resposta do status HTTP deve ser 400
    Then a resposta deve conter uma mensagem de erro

  Scenario: Obter detalhes de um pedido existente
    Given eu faço um GET para a rota "/pedidos/{id}" com um id de pedido válido
    When a resposta do status HTTP deve ser 200
    Then a resposta deve conter os detalhes do pedido

  Scenario: Tentar obter detalhes de um pedido não existente
    Given eu faço um GET para a rota "/pedidos/{id}" com um id de pedido inválido
    When a resposta do status HTTP deve ser 404
    Then a resposta deve conter uma mensagem de erro

  Scenario: Atualizar um pedido existente
    Given eu faço um PUT para a rota "/pedidos/{id}" com um id de pedido válido e dados de pedido atualizados
    When a resposta do status HTTP deve ser 200
    Then a resposta deve conter os detalhes do pedido atualizado

  Scenario: Tentar atualizar um pedido não existente
    Given eu faço um PUT para a rota "/pedidos/{id}" com um id de pedido inválido
    When a resposta do status HTTP deve ser 404
    Then a resposta deve conter uma mensagem de erro

  Scenario: Excluir um pedido existente
    Given eu faço um DELETE para a rota "/pedidos/{id}" com um id de pedido válido
    Then a resposta do status HTTP deve ser 204

  Scenario: Tentar excluir um pedido não existente
    Given eu faço um DELETE para a rota "/pedidos/{id}" com um id de pedido inválido
    When a resposta do status HTTP deve ser 404
    Then a resposta deve conter uma mensagem de erro