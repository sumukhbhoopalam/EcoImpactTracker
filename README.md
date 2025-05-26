# Eco Impact Tracker Backend

A Spring Boot application for tracking environmental impact and sustainability metrics.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
src/main/java/com/ecoimpact/
├── EcoImpactTrackerApplication.java
├── config/
│   └── OpenApiConfig.java
├── model/
│   └── User.java
├── repository/
│   └── UserRepository.java
└── service/
    └── (service classes will go here)

src/main/resources/
├── application.properties
└── db/
    └── migration/
        └── V1__create_users_table.sql
```

## Getting Started

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Features

- RESTful API endpoints
- JPA/Hibernate for database operations
- H2 in-memory database for development
- Lombok for reducing boilerplate code
- Swagger/OpenAPI documentation
- Flyway database migrations

## Database Migrations

The application uses Flyway for database migrations. Migration scripts are located in `src/main/resources/db/migration/`.

### Migration Naming Convention
- V1__Description.sql
- V2__Description.sql
- etc.

Where:
- V1, V2, etc. are version numbers
- Description is a brief description of the migration
- Files must end with .sql

### Creating a New Migration
1. Create a new SQL file in `src/main/resources/db/migration/`
2. Name it following the convention (e.g., `V2__add_new_column.sql`)
3. Write your SQL statements
4. Run the application - Flyway will automatically apply the new migration

## API Documentation

The API documentation is available through Swagger UI:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## Database

The application uses H2 in-memory database for development. You can access the H2 console at:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:ecoimpactdb
- Username: sa
- Password: password

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request 