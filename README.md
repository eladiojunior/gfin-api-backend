# gfin-api-backend-quarkus

Projeto de backend do gerenciador financeiro pessoal, que será utilizado em um aplicativo mobile para registro das despesas e receitas de uma pessoa para seu controle financeiro.

Esse projeto foi desenvolvimento em Java, framework Quarkus vrs 3.7.1.

## Extensões utilizadas neste projeto:

**RESTEasy JAX-RS** - REST endpoint framework implementing JAX-RS and more [Referencia](https://quarkus.io/extensions/io.quarkus/quarkus-resteasy)

**RESTEasy Reactive** - Easily start your Reactive RESTful Web Services [Referencia](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

**RESTEasy JSON-B** - JSON-B serialization support for RESTEasy [Referencia](https://quarkus.io/extensions/io.quarkus/quarkus-resteasy-jsonb/)

**JDBC Driver - Microsoft SQL Server** - Connect to the Microsoft SQL Server database via JDBC [Referencia](https://quarkus.io/extensions/io.quarkus/quarkus-jdbc-mssql/)

**Hibernate ORM with Panache** - Simplify your persistence code for Hibernate ORM via the active record or the repository pattern [Referencia](https://quarkus.io/extensions/io.quarkus/quarkus-hibernate-orm-panache/)

**SmallRye OpenAPI** - Document your REST APIs with OpenAPI - comes with Swagger UI [Referencia](https://quarkus.io/extensions/io.quarkus/quarkus-smallrye-openapi/)

**Camel MapStruct** - Type Conversion using Mapstruct [Referencia](https://quarkus.io/extensions/org.apache.camel.quarkus/camel-quarkus-mapstruct/)

## Como rodar o projeto

Modo DEV:
```shell script
./mvnw compile quarkus:dev
```
> **_NOTE:_** Aplicação estará rodando em modo dev: http://localhost:8080/q/dev/.


Criar uma versão nativa para executar
```shell script
./mvnw package -Dnative
```

ou, se você não tiver o GraalVM instalado, você pode executar o build de forma nativa em um container: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```
Resultado:
 `./target/gfin-api-backend-1.0.0-runner`
