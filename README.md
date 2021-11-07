# Sample App

A sample application built with [Dropwizard.io](http://dropwizard.io)

Requirements to run this app:

* Java 11
* Maven 3.6.x

## How to run unit tests

```sh
mvn test
```

## How to run integration tests

MySQL database setup (with Docker):

```sh
docker-compose up -d
docker exec -it dropwizard-sample-app_db_1 mysql -u root -proot -e "create database db_notes_test;"
```

Run migrations:

```sh
mvn package
java -jar target/sample-app-1.0-SNAPSHOT.jar db migrate src/test/resources/configuration-integration-test.yml
```

Run integration-test:

```sh
mvn failsafe:integration-test
```

## Run the application

Create the package

```sh
mvn package
```

MySQL database setup (with Docker):

```sh
docker-compose up -d
docker exec -it dropwizard-sample-app_db_1 mysql -u root -proot -e "create database db_notes;"
```

Run migrations:

```sh
mvn package
java -jar target/sample-app-1.0-SNAPSHOT.jar db migrate configuration.yml
```

Run application:

```sh
java -jar target/sample-app-1.0-SNAPSHOT.jar server configuration.yml
```

## Do some calls

```sh
curl -X POST http://localhost:8080/notes --data "Hello world"
curl -X POST http://localhost:8080/notes --data "This is a sample application"
curl http://127.0.0.1:8080/notes/
curl http://127.0.0.1:8080/notes/1
curl http://127.0.0.1:8080/notes/2
```

## Vagrant

Requirements (Vagrant 1.8.1 or above):

Vagrant plugins:

* vagrant-berkshelf
* vagrant-cachier
* vagrant-omnibus

```sh
vagrant up
```

## Unit and Integration tests for cookbook

Requirements:

* Chef-dk 0.10.0

```sh
cd cookbooks/sample-app
```

Run unit tests

```sh
chef exec rspec
```

Run integration test

```sh
kitchen create
kitchen converge
kitchen verify
kitchen destroy
```

Abbrv.

```sh
kitchen test
```
