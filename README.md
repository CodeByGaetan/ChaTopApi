# ChaTop Api

The API of the ChâTop Web App. Provide access and operations on the ChâTop application database.

This project uses :
- [JAVA] version 17
- [Maven] (https://maven.apache.org/guides/index.html) version 4.0.0
- [SpringBoot] (https://spring.io/projects/spring-boot) version 3.1.5 with Spring dependencies : Spring Web, Spring Security, Spring Data JPA, Lombok, OAuth2 Client and MySQL Driver
- [JJWT] (https://github.com/jwtk/jjwt.git) version 0.11.5
- [Springdoc] (https://github.com/springdoc/springdoc-openapi.git) version 2.2.0

# API Documentation

JSON documentation : http://localhost:3001/api/v3/api-docs
HTML documentation : http://localhost:3001/api/swagger-ui/index.html

# Install

Install all dependencies before starting : `npm install`.

MySQL Database install :
- Install mySQL on the localhost and enable the service
- From this app root directory, launch and connect to mySQL
- Run `CREATE DATABASE chatopdb;` to create the database
- Run `USE chatopdb;` to use the newly created database
- Run `SOURCE script_bdd.sql;` to create the database tables
- Run `CREATE USER 'TheUsername'@'%' IDENTIFIED BY 'ThePassword';` to create the MySQL user for the app. The username and password must be the same than in the application.properties file.
- Run `GRANT ALL ON chatopdb.* to 'TheUsername'@'%';` to give ChâTop database access to the new user

# Developpement run

Run the project from an IDE (Visual Studio Code or Eclipse) or use the command `mvn spring-boot:run`.

# Build and Run the jar file

Run `mvn package` to build the project. The build artifact will be stored in the `target/` directory.
Run `java -jar target/api-0.0.1-SNAPSHOT.jar` to launch the built package.