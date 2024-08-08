# postech-fiap-lanchonete-pedido-ms

# Lanchonete Pedido - Versão 1.0.0

Bem-vindo ao projeto **Lanchonete Pedido**! Esta é a versão 1.0.0, e abaixo você encontrará todas as instruções necessárias para começar a utilizar Docker e Docker Compose para rodar a aplicação.

## Pré-Requisitos

Antes de prosseguir, assegure-se de que você tem o Docker e o Docker Compose instalados em seu sistema. Caso ainda não os tenha instalado, visite a [documentação oficial do Docker](https://docs.docker.com/get-docker/) para obter instruções de instalação.

## Estrutura do Projeto

O projeto tem a seguinte estrutura de diretórios:

```plaintext
lanchonete-pedido/
│   docker-compose.yml
│   Dockerfile
│   ...
```

- `Dockerfile`: Arquivo com instruções para a criação da imagem Docker.
- `docker-compose.yml`: Arquivo de configuração para rodar a aplicação com o Docker Compose, definindo serviços, volumes, portas, entre outros.

## Construindo a Imagem Docker

Para construir a imagem Docker do projeto, abra um terminal, vá até a raiz do projeto e execute o seguinte comando:

`docker build -t lanchonete-pedido:latest`

## Executando a Imagem Docker:

`docker-compose up`

Este comando irá subir duas imagens Docker: um banco de dados MongoDB e um broker MQ, que são necessários para executar o projeto.

# Guia de Execução do Projeto e Utilização das APIs

Antes de iniciar, certifique-se de ter o Docker instalado em sua máquina. A seguir, são apresentados os passos necessários para executar o projeto localmente.

## Passo 1: Inicializando o Ambiente

Abra o terminal na pasta do projeto e execute o seguinte comando:

```bash
docker-compose up
```

Este comando iniciará todas as imagens Docker necessárias para a execução do projeto.

## Padrão SAGA: Coreografia

O projeto utiliza o padrão SAGA de coreografia em combinação com uma fila de mensagens MQ, utilizamos esse padrão principalmente pois ele é mais adequado para projetos menores e mais autônomos o que se encaixa para esse projeto. Aqui estão os principais motivos para essa escolha:

### Descentralização e Autonomia
A coreografia permite que cada microsserviço funcione de forma independente, sem a necessidade de um coordenador central. Isso é ideal para projetos menores, pois reduz a complexidade e mantém os serviços mais simples e gerenciáveis.

### Simplicidade
Para projetos menores, com fluxos de trabalho e interdependências reduzidos, a coreografia simplifica a arquitetura. A distribuição do controle entre os serviços facilita a compreensão e o gerenciamento do fluxo geral do sistema.

### Isolamento de Falhas
A independência dos serviços na coreografia significa que uma falha em um componente não compromete todo o sistema. Isso garante que o projeto continue funcionando adequadamente, mesmo se um serviço falhar.

### Eficiência no Desenvolvimento
A coreografia permite que equipes menores desenvolvam, testem e implantem microsserviços de forma isolada, resultando em um processo de entrega mais rápido e eficiente.

Assim, a coreografia SAGA é a escolha ideal para este projeto, proporcionando uma abordagem flexível e resiliente que se alinha com a necessidade de manter a autonomia e simplicidade dos serviços.