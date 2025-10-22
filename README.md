# Reading Tracker API

This project implements a reading tracker API, allowing a reader to track their progress on the books they are reading.

## Technology
* Java 17
* Spring Boot
* Spring Data JPA
* SQLite

## Goals
This project serves as exploration into using SQLite and JPA to further my Java skills with Spring Boot and the Java persistence layer.

## How to Run
If you don't have Maven installed, you will need to install that first. For those having trouble using `mvnw` with Maven installed, try running the following command from the project directory to setup the Maven jar for the Spring Boot wrapper to use it:
```
mvn -N io.takari:maven:wrapper
```

To start, please run a `./mvnw install` to install all necessary dependencies before attempting to run the project.

After installation, run `./mvnw spring-boot:run`. You can then make requests in a tool like Postman to `http://localhost:8080/books*`.

Please note, the repository is currently configured to recreate the DB on each run, so changes are not persisted across runs.