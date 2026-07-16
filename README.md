# Staj2026 - Product Management API

A Spring Boot REST API application for managing products with security, exception handling, and Docker support.

## Overview

This is a backend service built with Spring Boot that provides RESTful endpoints for product management operations. The application includes integrated security configuration, comprehensive error handling, and Docker deployment support.

## Features

- **Product Management**: Create, read, update, and delete products
- **Security**: Spring Security configuration for API protection
- **Error Handling**: Global exception handler for consistent error responses
- **Database**: PostgreSQL with Spring Data JPA and repository pattern
- **API Documentation**: SwaggerUI/OpenAPI integration for interactive API documentation
- **Validation**: Input validation using Spring Validation framework
- **Docker Support**: Docker Compose configuration for containerized deployment
- **Testing**: Unit and integration test framework with Spring Security test support
- **Code Generation**: Lombok support for reducing boilerplate code

## Project Structure

```
src/
├── main/
│   ├── java/com/metesevim/staj2026/
│   │   ├── Staj2026Application.java       # Main Spring Boot application
│   │   ├── controller/
│   │   │   └── ProductController.java     # Product REST endpoints
│   │   ├── service/
│   │   │   └── ProductService.java        # Business logic layer
│   │   ├── repository/
│   │   │   └── ProductRepository.java     # Data access layer
│   │   ├── entity/
│   │   │   └── Product.java               # Product entity
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java # Global error handling
│   │   │   └── ProductNotFoundException.java
│   │   ├── security/
│   │   │   └── SecurityConfig.java        # Security configuration
│   │   └── common/
│   │       └── TestController.java        # Test endpoints
│   └── resources/
│       └── application.properties          # Application configuration
└── test/
    └── java/
        └── Staj2026ApplicationTests.java   # Integration tests
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+ (database)
- Docker & Docker Compose (for containerized deployment)

## Getting Started

### Build the Project

```bash
./mvnw clean package
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

### Docker Deployment

```bash
docker-compose up
```

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Testing
- `GET /test` - Test endpoint

### API Documentation
- `GET /swagger-ui.html` - Interactive API documentation (SwaggerUI)
- `GET /v3/api-docs` - OpenAPI specification in JSON format

## Configuration

The application can be configured via `application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/staj2026
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Technologies & Dependencies

- **Spring Boot 4.0.7** - Application framework
- **Spring Data JPA** - ORM and database abstraction
- **Spring Security** - Authentication and authorization
- **PostgreSQL** - Database management system
- **SpringDoc OpenAPI 2.8.9** - API documentation and Swagger UI
- **Lombok** - Code generation for boilerplate reduction
- **Spring Validation** - Input validation framework

## Exception Handling

The application uses a global exception handler to provide consistent error responses. The following exceptions are handled:

- `ProductNotFoundException` - When a requested product is not found

## Testing

Run the test suite with:

```bash
./mvnw test
```

## License

This project is part of the Staj2026 internship program.
