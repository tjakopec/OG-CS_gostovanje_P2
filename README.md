# springboot-crud-postgres

Spring Boot Postgres App is a REST based service that can be use by webapps or other devices. It show's example of using 
Postgres as container in docker and Testcontainers for Integration testing.

## Prerequisite 

Prerequisites
You need the following software to build the project:

* OpenJDK
* Gradle

## Project Setup

Build:

`./gradlew clean build`

Run:

`./gradlew bootRun`

... and fetch all cars from endpoint:

`curl localhost:8080/cars`

... or call API docs: http://localhost:8080/swagger-ui

## Running the program with Docker

Install Docker on your machine. Then in the terminal build an image

`docker build -t spring-boot-postgres:latest .`

Then start `spring-boot-postgres:latest` and `postgres:latest` in one container using docker compose

`docker compose up`

## Environment Variables
You have set the following environment variables if you want to run against a different Postgres DB:  

    POSTGRES_PORT
    POSTGRES_DB
    POSTGRES_USERNAME
    POSTGRES_PASSWORD