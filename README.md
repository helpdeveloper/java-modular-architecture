# Arquitetura modular
### Projeto em desenvolvimento...

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

## Build do projeto, já toda todos os teste unitários e os testes de aceitação

```bash
mvn clean install
```
## Gerando imagem docker com o Quarkus
```bash
docker build  -t helpdev/app-quarkus-jvm .
```

## Iniciando serviço

*O comando sobe toda infraestrutura necessária para a aplicação*
```bash
docker-compose -f .docker-compose/stack.yml up
```
*Agora podemos iniciar nossa aplicação*
```bash
docker-compose -f .docker-compose/quarkus-app.yml up
```

## Consumindo API
Para ter uma interface de fácil com o OpenAPI consumido usamos o [RapiDoc](https://mrin9.github.io/RapiDoc/).

Basta acessar [Endereço local](http://localhost:5000) e já pode testar as APIs 
- http://localhost:5000

## Rodando teste de carga
Para quem ainda não conhece o [k6](https://k6.io/) é uma ferramenta para testes de carga, basta [Instalar o K6](https://k6.io/docs/getting-started/installation) e executar o comando:

```bash
k6 run  k6/script.js
```

## Monitorando aplicação

### Observabilidade
Saber como a aplicação está se comportando e métricas

A combinação poderosa entre o [Grafana](https://grafana.com/) e o [Prometheus](https://prometheus.io/) permite gráficos e alertas configuraveis.
Acesssando o [Grafana Local](http://localhost:3000) já basta navegar até o dashboard précadastro e ter uma amostrado dos dados da apliação e das ferramentas.
- http://localhost:3000
    - login: admin
    - senha: admin
    
Já o [Promehteus](http://localhost:9090) só acessar o link (sem login):
- http://localhost:9090

### Acessando o banco de dados

Ao acessar o [Adminer](http://localhost:5000/adminer) é possivel executar scripts SQL além de adicionar e altarer dados dentro das tabelas.
- http://localhost:5000/adminer
    - host:
    - usuário:
    - senha:
    - base:

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

