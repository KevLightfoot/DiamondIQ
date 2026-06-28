# DiamondIQ

DiamondIQ is a Java EE web application that predicts Major League Baseball matchups using historical team statistics.

## Features

- User registration and login
- Team search
- Favorite team selection
- Team comparison
- Matchup prediction engine
- Prediction history
- Detailed prediction analysis

## Technologies

- Java
- JSP / Servlets
- JDBC
- MySQL
- Apache Tomcat
- HTML
- CSS
- JavaScript

## Database

The database schema is located in:

database/schema.sql

## Running the Project

1. Import `database/schema.sql` into MySQL.
2. Update the database credentials in `DBConnection.java` if necessary.
3. Deploy the application to Apache Tomcat.
4. Visit:

http://localhost:8080/DiamondIQ/login.jsp