# Arquitetura modular

<p align="center">
    <img src="./images/arch.png" height="350">
</p>

## Foco no negócio

### domain
A camada mais restrita de todas possui o mapeamento de regras e restrições do domínio do negócio.

### use-case
Determina o comportamento da funcionalidade exigida, no caso, o use case será o orquestrador do domínio.

## Adaptadores

### input
Camada de apresentação do sistema, nela, por exemplo, iremos fornecer nossos end points.

### output
Todo acesso a dados seja banco e/ou api's expostos pelas interfaces do Use Case devem ser implementadas nessa camada.

## Testabilidade

Facilidade na execução dos testes com uma melhor granularidade.

### Unitários
- JUnit5
- Mockito
- AssertJ
- Mutação
- Cobertura de código (linha e condições)

### Aceitação (acceptance-test)
Teste do ponto de vista de quem irá consumir, sempre buscando o mais perto de produção.

#### O que tem:
- Docker
- TestContainers
- RestAssured
- WireMock
- Flyway

#### O que não tem:
- Framework (Spring ou quarkus)

# Fluxo de dependências das camadas

<p align="center">
    <img src="./images/flow.png" height="500">
</p>

# Executando o projeto

### Requerido

- Java 11+
- Maven 3+
- docker
- docker-compose

## Gerando imagem docker

mvn clean install

docker build  -t helpdev/app-quarkus-jvm .

## Iniciando serviço

docker-compose -f .docker-compose/stack.yml up

docker-compose -f .docker-compose/quarkus-app.yml up

## Consumindo API

http://localhost:5000

## Rodando teste de carga

k6 run  k6/script.js

## Monitorando aplicação

### Observabilidade
Saber como a aplicação está se comportando e métricas

http://localhost:3000
- login: admin
- senha: admin

# Desenvolvedores

**Alisson Medeiros**
- LinkedIn - https://www.linkedin.com/in/alisson-medeiros-8bb67830/
- GitHub - https://github.com/AlissonMedeiros
- Email - alisson.medeiros@gmail.com

**Guilherme Biff Zarelli**
- Blog/Site - https://helpdev.com.br
- LinkedIn - https://linkedin.com/in/gbzarelli/
- GitHub - https://github.com/gbzarelli
- Medium - https://medium.com/@guilherme.zarelli
- Email - gbzarelli@helpdev.com.br
