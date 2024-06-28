# numbers


## Prequisites
What is needed to set up the dev environment:
openjdk version "21.0.3" 2024-04-16 LTS
Apache Maven 3.9.6


## Tech
These are principal framework/libraries used:

| Framework-library | Version     |
| ----------------- |-------------|
| Java              | 21          |
| Spring boot       | 3.3.1       |
| Maven             | 3.9.6       |
| Lombok            | 1.18.32     |
| Mapstruct         | 1.5.5.Final |
| JUnit             | 5           |


### Api first
This microservice has been built using the api first philosophy.
In file ../numbers/doc/openapi/openapi-rest.yml first of all microservice contract has been defined.
In swagger editor page: https://editor.swagger.io/ you can show this.
In file ../numbers/doc/postman/numbers.postman_collection.json you have examples to test api with postman.

### Architecture
Hexagonal architecture
- domain: domain objects and ports. (In this case there is not necessary to define any port yet).
- application: with business use cases.
- infrastructure: adapters which implements all ports definition.
NOTE: In this case, there is only rest and there is any port implementation due to is not necessary yet.
  One maven module to be really decoupled from each one and mappers to convert infrastructure to domain objects or vice-versa.

### Sonar
I have used sonar to analyse the code and there are currently no warnings.


### Application configuration
In you need take a look or set microservice configuration you can see the file:
../numbers/numbers-infrastructure/src/main/resources/application.yml

### Compile
Using root folder:
```sh
mvn clean install -U
```

### Launch application
In you want to launch the application from local use this steps
(if you prefer use dockerized one go to section Dockerize app)
Using root folder:
```sh
java -jar numbers-infrastructure/target/numbers-infrastructure-0.0.1-SNAPSHOT.jar
```
You can do a simple curl to test:
```sh
curl --location 'http://localhost:8080/numbers/by-binary' \
--header 'Content-Type: application/json' \
--data '{
"data": [
1
]
}'
```
This is expected response:
```sh
{
    "data": [
        1
    ]
}
```
App is running, then you can make http request with you favourite http client
(previously you can see a postman file with api examples).

### Tests
- JUnit testing everywhere :)
- Integration tests codified in order to validate application start and current feature from order numbers by binary representation.

You can run this with the command to run all of them:
```sh
mvn verify
```

Or this command to execute unit tests:
```sh
mvn test
```

If you need only to run integration tests, use this command as follows:
```sh
mvn failsafe:integration-test
```


### Api first
- This microservice has been built using the api first philosophy.
- In file ../numbers/doc/openapi/openapi-rest.yml first of all microservice contract has been defined.
- In swagger editor page: https://editor.swagger.io/ you can show this.

- Postman: in file ../numbers/doc/postman/numbers.postman_collection.json you have examples to test api with postman.

### Dockerize app
Having docker previously installed, using root folder do following commands and :

1.Compile application:
```sh
mvn clean install -U
```

2.Build docker image app:
```sh
docker build --tag=numbers-app:latest .
```

3.Run your container:
```sh
docker run -p 8081:8080 numbers-app:latest
```
NOTE: Is mandatory to be available port 8081 in your computer, please change this if not.

4.You can do a simple curl to test:
```sh
curl --location 'http://localhost:8081/numbers/by-binary' \
--header 'Content-Type: application/json' \
--data '{
"data": [
1
]
}'
```
This is expected response:
```sh
{
    "data": [
        1
    ]
}
```

If you see this, now your spring application is dockerized and running :)
