# Movies Battle Project

O projeto consiste basicamente em um jogo de cartas, o sistema apresentará duas opções de Filmes e o usuário deverá escolher o de maior pontuação, segundo o IMDB.

## Dependências
* JDK 11: Necessário para executar o projeto
* Maven 3: Necessário para realizar o build do projeto Java

## Documentação
A documentação da aplicação foi criada utilizando a lib [SpringDoc](https://springdoc.org/), seguinte a especificação OpenAPI 3.0.

É possível acessar a documentação do sistema, após sua inicialização, através do link

```
http://127.0.0.1:8080/swagger-ui.html
```

#### Autenticação
* A aplicação usa token JWT para autenticação do usuário, com isso, após a criação e login do usuário, será gerado um Token JWT utilizado para o acesso nos demais endpoints.

## Instalação

Para realizar a instalação e configuração das dependências, basta acessar a pasta do projeto e executar o seguinte comando:

```bash
mvn clean install
```

## Inicialização
É possível iniciar a aplicação de maneira simples, acessando a pasta do projeto e executando o seguinte comando:

```bash
mvn spring-boot:run
```

## Testes
Execução dos testes unitários

```bash
mvn test
```

## Banco de dados
A aplicação foi criada utilizando o banco de dados em memória H2. É possível acessar o console do H2, através do endpoint:

```
http://127.0.0.1:8080/h2
```

As credência de acesso ao banco estão disponíveis no arquivo de propriedades do projeto (A nível de avaliação).


#### Obrigado!
