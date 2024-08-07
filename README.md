# management-api-java
This repository is to purpose to have some studies between microservices and rellated techinical tests

## Project 1 Company Service
This project is to purpose serve management for a company, that have a CRUD of companies and your partners

## Commands for the project
# Run project for mode dev
- mvn spring-boot:run -Dspring.profiles.active=dev

# Run project for mode prod
- mvn spring-boot:run -Dspring.profiles.active=prod

# Run project for mode testing
- mvn spring-boot:run -Dspring.profiles.active=test
- Run unit tests
- mvn  test -Dspring.profiles.active=test

# Documentation of API Company Management
http://localhost:5000/swagger-ui/index.html
http://localhost:5000/api-docs/public
