## A simple e-commerce REST API

This is a learning project that represents an e-commerce backend built with:
- Spring Web MVC
- Hibernate
- Spring Security and JWT
- PostgreSQL

### tl;dr — Quickstart

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


# API Overview

## User Management

We'll use [HTTPie](https://httpie.org/) for making requests.

#### Authenticate

By default, we have 3 user accounts with 3 different roles: admin, staff, customer. For each one, the password is `123`.

```shell script
http POST :2222/auth/login username=admin password=123
```

If successful, the response will contain the JWT token that must be used for almost all requests.

#### Register customer

```shell script
http POST :2222/customers < data.json
```

Example data:

```json
{
    "firstName": "Jon",
    "lastName": "Doe",
    "email": "doe@gmail.com",
    "username": "johny",
    "password": "password",
    "active": true,
    "address": {
        "address": "First 11",
        "city": "Washington",
        "postalCode": "800111",
        "country": "United States",
        "phone": "+145752148552"
    }
}
```

#### Get all customers

*Only available to STAFF and ADMIN roles.*

```shell script
http :2222/customers Authorization:"Bearer your_jwt_token"
```

#### Register staff

*Only available to ADMIN role.*

```shell script
http POST :2222/staff Authorization:"Bearer your_jwt_token" < data.json
```

Example data:

```json
{
    "firstName": "Jon",
    "lastName": "Doe",
    "email": "doe@gmail.com",
    "username": "johny",
    "password": "password",
    "active": true,
    "address": {
        "address": "First 11",
        "city": "Washington",
        "postalCode": "800111",
        "country": "United States",
        "phone": "+145752148552"
    }
}
```

#### Get all staff

*Only available to STAFF and ADMIN roles.*

```shell script
http :2222/staff Authorization:"Bearer your_jwt_token"
```

## Product Management

#### Create product

*Only available to STAFF and ADMIN roles.*

```shell script
http POST :2222/products Authorization:"Bearer your_jwt_token" < data.json
```

Example data:

```json
{
    "title": "iPhone 13 PRO",
    "description": "last last last model",
    "price": 3669.90
}
```

#### Get all products

```shell script
http :2222/products Authorization:"Bearer your_jwt_token"
```

#### Get product by id

```shell script
http :2222/products/{productID} Authorization:"Bearer your_jwt_token"
```

#### Update product by id

*Only available to STAFF and ADMIN roles.*

```shell script
http PATCH :2222/products/{productID} Authorization:"Bearer your_jwt_token" < data.json
```

Example data:

```json
{
    "title": "iPhone 13 SUPER PRO",
    "description": "the best model"
}
```

#### Delete product by id

*Only available to STAFF and ADMIN roles.*

```shell script
http DELETE :2222/products/{productID} Authorization:"Bearer your_jwt_token"
```

## Order Management

#### Create order

The order will be created for the user whose JWT token is.

```shell script
http POST :2222/orders Authorization:"Bearer your_jwt_token" < data.json
```

Example data:

```json
{
    "pid": "725a4e54-b9ad-4c43-a7f7-987562b0074a",
    "count": 2,
    "comment": "a gift"
}
```

#### Get all orders

*Only available to STAFF and ADMIN roles.*

```shell script
http :2222/orders Authorization:"Bearer your_jwt_token"
```

#### Get order by id

*Only available to STAFF and ADMIN roles.*

```shell script
http :2222/orders/{orderID} Authorization:"Bearer your_jwt_token"
```

#### Update order by id

*Only available to STAFF and ADMIN roles.*

```shell script
http PATCH :2222/orders/{orderID} Authorization:"Bearer your_jwt_token" < data.json
```

Example data:

```json
{
    "count": 10
}
```

#### Delete order by id

*Only available to STAFF and ADMIN roles.*

```shell script
http DELETE :2222/orders/{orderID} Authorization:"Bearer your_jwt_token"
```