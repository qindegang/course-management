# Simple Course Management System

## Description

This is a simple course management system to facilitate the students to
sign up for courses.

## Prerequisites

* JDK: [OpenJDK 21](https://openjdk.org/projects/jdk/21/)

## Environment Variables

* JAVA_HOME: installation location of the JDK

## Steps:

1. Go to the course-management directory and execute the following instructions
2. ```./mvnw clean install```
3. ```./mvnw spring-boot:run```
4. View and try the APIs at <http://localhost:8080/swagger-ui/index.html>

## Notes:

Work skipped to speed development:

* H2 is used as database without persistence across restart.
* No logging statements are added.
* Unit test are implemented but without acceptance test. 
