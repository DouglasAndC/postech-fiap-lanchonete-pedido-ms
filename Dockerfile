# Fase de construção
FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /workspace/app

# Copiando o POM e baixando as dependências
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copiando o código fonte e construindo o projeto
COPY src ./src/
RUN mvn package

# Fase de execução
FROM openjdk:17-alpine3.14

WORKDIR /app

# Copiando o JAR da fase de construção para a fase de execução
COPY --from=builder /workspace/app/target/lanchonete-pedido-ms-*.jar application.jar

EXPOSE 8080

CMD ["java","-jar","application.jar"]