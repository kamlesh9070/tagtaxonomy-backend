# Tag Taxonomy Service - Backend

Java 21 Spring Boot 4 backend application with REST API.

## Quick Start

```bash
cd tagtaxonomy-backend
./gradlew clean build
./gradlew bootRun
```

Backend runs on `http://localhost:8080`

## Documentation

- See [../shared/ARCHITECTURE.md](../shared/ARCHITECTURE.md) for system design
- See [../shared/QUICKSTART.md](../shared/QUICKSTART.md) for full setup
- API Docs: http://localhost:8080/swagger-ui.html

## Tech Stack

- Java 21
- Spring Boot 4
- Gradle 8.10
- PostgreSQL / H2
- Liquibase
- JUnit 5 + Mockito

## Structure

```
app/              Spring Boot Application
api/              REST Controllers & DTOs
service/          Business Logic & Persistence
```

## Key Features

- ✅ Multi-module architecture
- ✅ JPA with optimistic locking
- ✅ Liquibase migrations
- ✅ Global exception handling
- ✅ Swagger/OpenAPI
- ✅ Comprehensive tests

## Testing

```bash
./gradlew test
```

## Building for Production

```bash
./gradlew clean build -x test --configure-on-demand
```

## Docker Build

```bash
docker build -f Dockerfile -t tagtaxonomy-backend:1.0.0 .
```

---

For complete setup and integration with frontend, see [../shared/README.md](../shared/README.md)
