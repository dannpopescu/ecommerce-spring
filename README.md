## A simple e-commerce REST API

This is a learning project that represents an e-commerce backend built with:
- Spring Web MVC
- Hibernate
- Spring Security and JWT
- PostgreSQL

On the link below you can view all the endpoints provided by this API.

[**Postman DOCS**](https://documenter.getpostman.com/view/12473242/TVKEWGx8)

### Installation guide

To run this application, do the following:

- Clone the repository

`$ git clone https://github.com/dannpopescu/ecommerce-spring.git`

- If you have PostgreSQL installed, create a new database called `shop`

```sh
$ sudo -u postgres psql
postgres=# CREATE DATABASE shop;
```

- Write the PostgreSQL username and password in `application.properties`

```
spring.datasource.username=username
spring.datasource.password=password
```

- Write the JWT secret key in `application.properties`

```
jwt.secret=yoursupersecretkey
```

- Run the app

```shell script
$ ./mvnw spring-boot:run
```