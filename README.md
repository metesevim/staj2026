# Staj2026 - Product Management API

A Spring Boot REST API application for managing products with security, exception handling, and Docker support.

## Overview

This is a backend service built with Spring Boot that provides RESTful endpoints for product management operations. The application includes integrated security configuration, comprehensive error handling, and Docker deployment support.

## Features

- **Product Management**: Create, read, update, and delete products
- **Security**: Spring Security configuration with JWT authentication for API protection
- **Error Handling**: Global exception handler for consistent error responses
- **Database**: PostgreSQL with Spring Data JPA and repository pattern
- **API Documentation**: SwaggerUI/OpenAPI integration for interactive API documentation
- **Validation**: Input validation using Spring Validation framework
- **Docker Support**: Docker Compose and Dockerfile for containerized deployment
- **Kubernetes Ready**: Kubernetes manifests (deployment, service, pod) for orchestration
- **Testing**: Unit and integration test framework with Spring Security test support
- **Code Generation**: Lombok support for reducing boilerplate code
- **DTOs**: Request/Response Data Transfer Objects for API contracts

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/metesevim/staj2026/
│   │   │   ├── Staj2026Application.java       # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java         # OpenAPI/Swagger configuration
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java     # Product REST endpoints
│   │   │   ├── service/
│   │   │   │   └── ProductService.java        # Business logic layer
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java     # Data access layer
│   │   │   ├── entity/
│   │   │   │   └── Product.java               # Product entity
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java # Global error handling
│   │   │   │   └── ProductNotFoundException.java
│   │   │   ├── security/
│   │   │   │   └── SecurityConfig.java        # Security configuration
│   │   │   ├── dto/
│   │   │   │   ├── ProductRequest.java        # API request DTO
│   │   │   │   └── ProductResponse.java       # API response DTO
│   │   │   └── common/
│   │   │       └── TestController.java        # Test endpoints
│   │   └── resources/
│   │       └── application.properties          # Application configuration
│   └── test/
│       └── java/
│           └── Staj2026ApplicationTests.java   # Integration tests
├── k8s/
│   ├── deployment.yaml                        # Kubernetes deployment
│   ├── pod.yaml                               # Kubernetes pod definition
│   └── service.yaml                           # Kubernetes service definition
├── .env                                        # Environment variables
├── Dockerfile                                  # Docker build configuration
├── docker-compose.yml                         # Docker Compose configuration
└── pom.xml                                     # Maven project configuration
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+ (database)
- Docker & Docker Compose (for containerized deployment)

## Getting Started

### Prerequisites Setup

1. **Database Setup**
   ```bash
   # Create PostgreSQL database
   createdb staj2026
   ```

2. **Environment Configuration**
   - Copy or create `.env` file with required environment variables:
   ```env
   JWT_SECRET=your-secret-key-here
   ```

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

**Using Docker Compose:**
```bash
docker-compose up
```

**Building Docker Image Manually:**
```bash
docker build -t staj2026:latest .
docker run -p 8080:8080 staj2026:latest
```

The Dockerfile uses a multi-stage build process:
- Stage 1: Build stage with full JDK
- Stage 2: Runtime stage with slim JRE for reduced image size

### Kubernetes Deployment

Deploy to Kubernetes cluster:
```bash
# Create namespace (optional)
kubectl create namespace staj2026

# Apply configurations
kubectl apply -f k8s/pod.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Check deployment status
kubectl get deployments
kubectl get services
```

Port forwarding for local access:
```bash
kubectl port-forward svc/staj2026-service 8080:8080
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

### Environment Variables

The application reads configuration from a `.env` file:

```env
JWT_SECRET=your-jwt-secret-key
```

**Required Variables:**
- `JWT_SECRET` - Secret key for JWT token signing (must be a valid Base64-encoded string)

### Application Properties

Configure via `application.properties`:

```properties
# Application name
spring.application.name=staj2026

# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/staj2026
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# OpenAPI/Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Technologies & Dependencies

- **Spring Boot 4.0.7** - Application framework
- **Spring Data JPA** - ORM and database abstraction
- **Spring Security** - Authentication and authorization with JWT support
- **PostgreSQL** - Database management system
- **SpringDoc OpenAPI 2.8.9** - API documentation and Swagger UI
- **Lombok** - Code generation for boilerplate reduction
- **Spring Validation** - Input validation framework
- **JWT (JSON Web Tokens)** - Stateless authentication mechanism
- **Docker** - Container platform for application deployment
- **Kubernetes** - Orchestration platform for containerized applications

## Exception Handling

The application uses a global exception handler to provide consistent error responses. The following exceptions are handled:

- `ProductNotFoundException` - When a requested product is not found

## Testing

Run the test suite with:

```bash
./mvnw test
```

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running: `psql -U postgres` 
- Check if database exists: `psql -U postgres -l | grep staj2026`
- Verify database URL, username, and password in `application.properties`

### Docker Compose Issues
- Ensure Docker daemon is running
- Check logs: `docker-compose logs`
- Remove stopped containers: `docker-compose down -v`
- Rebuild: `docker-compose up --build`

### JWT Token Issues
- Verify `.env` file exists in project root
- Check `JWT_SECRET` is a valid Base64-encoded string
- Ensure token is sent in Authorization header: `Authorization: Bearer <token>`

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or kill process using port 8080: `lsof -ti:8080 | xargs kill -9`

## Contributing

This project is part of the Staj2026 internship program.

## License

This project is part of the Staj2026 internship program.
