# Mercado ERP API

## API REST que simula um sistema ERP disponibilizando CRUD de várias classes. Feita utilizando Spring Framework, JPA Hibernate, versionamento de banco de dados com Flyway, banco MySQL, e testes com JUnit 5.

<p align="center">
 <a href="#pre-requisitos">Pré-requisitos</a> •
 <a href="#rodando-o-back-end-servidor">Rodando o Back End</a> • 
 <a href="#tecnologias">Tecnologias</a> •
 <a href="#autor">Autor</a>
</p>

<h3 align="center">:hammer: Em construção :books:</h3>

## :speech_balloon: Pré-requisitos 

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Java 15 SE](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html),
[Maven](https://maven.apache.org/download.cgi) e [MYSQL Server](https://dev.mysql.com/downloads/mysql/). 
Além disto é bom ter uma IDE para trabalhar com o código como [Eclipse](https://www.eclipse.org/downloads/)

## :computer: Rodando o Back End (servidor) 🚀

### Clone este repositório
$ git clone <https://github.com/heitorgo/mercado-api>

### Acesse a pasta do projeto no terminal/cmd
$ cd mercado-api

### Verifique se seu usuário e senha no MySQL Server, no caso deste repositório o padrão é:

### usuário: root
### senha:

Caso este não seja seu caso será necessário alterar os arquivos src\main\resources\application.properties e src\test\resources\application-test.properties em uma IDE ou editor de texto. Dentro destes arquivos altere os parametro spring.datasource.username e spring.datasource.password conforme seu usuário e senha do MySQL

### Novamente no diretório do projeto pelo terminal vamos empacotar o projeto com  o Maven
$ mvn package

### Execute o arquivo jar na pasta target
$ java -jar target\mercado-api-0.0.1-SNAPSHOT.jar

<h3 align="center"> O servidor inciará na porta:8080 certifique-se que ela esteja livre - acesse <http://localhost:8080></h3>

## Tecnologias

Ferramentas usadas na construção do projeto:

- [Java](https://www.oracle.com/br/java/)
- [Spring Framework](https://spring.io/)

## Autor

Heitor Gonçalves

