# Simple Course Management System

## Description

This is a simple course management system to facilitate the students to
sign up for courses.

## Prerequisites

* [OpenJDK 21](https://openjdk.org/projects/jdk/21/)
* [Docker](https://www.docker.com/)

## Environment Variables

* JAVA_HOME: installation location of the JDK

## Steps:

1. Download official docker image for MySQL:\
   ```$ docker pull mysql```
2. Run docker container for MySQL:\
   ```$ docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=course_management -d mysql```
3. Go to the course-management directory and execute the following instructions:\
   ```$ ./mvnw clean install```\
   ```$ ./mvnw spring-boot:run```
4. View and try the APIs at <http://localhost:8080/swagger-ui/index.html>

## Notes:

Work skipped to speed development:

* Database tables are created and populated with sample data (documented in Swagger) upon server start, no database
  migration strategy is implemented.
* No logging statements are added.
* Unit tests are implemented but without acceptance test. 
