# Sicredi Challenge

## Prerequisites:
- Docker 20.10.22 build 3a2c30b or newer
- docker-compose 2.15.1 or newer
- Git 2.37.0 or newer
- Apache Maven 3.8.6 or newer
- Postman 10.13.5 or newer
---

## Clonning project:
```
$ git clone git@github.com:matheusbernard0/dt-dbc.git
  ```
## Installing dependencies:
```
$ cd dt-dbc && mvn clean install
```

## Running tests:
```
$ mvn test
```

## Running application Infra (MySQL, Kafka and Zookeper):
```
$ docker-compose up -d
```

## Running application:
```
$ mvn spring-boot:run
```

## Killing application Infra:
```
$ docker-compose down
```

## Docs:
The application docs was made using swagger. So you can access it [here](http://localhost:8080/swagger-ui.html) after you run the application.

---
## Application use:
After installed Postman, you can import [this collection](./Pauta%20API.postman_collection.json) and all the requests will be available to you interact with the app.

---

## Future improvements:
- Implement CPF validation.
- Implement ErrorObject on response errors to turn easier error mapping on client applications.
- Implement consumer outside this app to separate responsibilities.
- Setup of Prometheus/Grafana to visualize app and business metrics; 
- Setup of Grafana Loki to work with centralized Logs;
- Setup of Jaeger to do the app tracing.

---
P.S: https://user-info.herokuapp.com/users/{cpf} is not working. So i could not work on integrations.