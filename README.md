[![Codacy Badge](https://api.codacy.com/project/badge/Grade/190a6a5fbb1f4098ab76f724ed9795fd)](https://app.codacy.com/app/gagi3/isamrs-tim26?utm_source=github.com&utm_medium=referral&utm_content=gagi3/isamrs-tim26&utm_campaign=Badge_Grade_Settings)

# ISA/MRS - Fly Delta
Project for Internet Software Architectures & Software Development Methodologies courses.

## Team members:
1. Vladimir Gajčin SW49-2014

## Project description
- Flight ticket reservation and airline company page management.

## Technologies:
1. Java + Spring Boot 
2. REST services
3. MySQL database
4. Angular 7

## Features:
- account management (register, login, update and validate account)
- airline company management (flights, airplanes, tickets, discounted tickets, price lists, business reports)
- ticket reservation (reserve, reserve discounted, reserve for a friend, confirm and cancel reservation)
- friendship requests (send, accept, reject, remove friend)
- browsing (view and search flights, friends, non-friends)
- CRUD
- Token-based security configuration (JWT)

## Instructions
1. Clone/Download GitHub project from: https://github.com/gagi3/isamrs-tim26
2. App delta:
  Eclipse - import project:
  - right click
  - import...
  - Existing Project
   Build project:
  - right click -> Run as -> Maven clean
  - right click -> Run as -> Maven build (in goals type package)
   Pokretanje projekta:
  - right click -> Run as -> Spring Boot App\
In aplication.properties are application settings:
  - spring.datasource.url = jdbc:mysql://localhost:3306/delta?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
  - spring.datasource.username=root
  - spring.datasource.password=root\
In order for the application to run, you need to download MySQL (v8) from: https://www.mysql.com/downloads/
3. App delta-front:
   - run Command Prompt as Administrator 
   - change directory to project directory
   - ng serve
   - after compiling, open Chrome and type: http://localhost:4200/signin  
4. Both apps (delta & delta-front) need to be running at the same time, and the database should be connected as well

## Additional info
- There is only one auto-generated system administrator in the app (username: admin@admin.adm, password: Tier1SOF)
- For detailed REST API (and endpoints), take a look at: https://github.com/gagi3/isamrs-tim26/tree/master/delta/src/main/java/com/delta/fly/controller
