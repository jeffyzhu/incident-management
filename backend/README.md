# Incident Management Backend

## Overview

This is the backend service for the Incident Management system. It is built using Java with Spring Boot and provides RESTful APIs for managing incidents.

## Prerequisites

- Java 17 or later
- Maven
- Docker (for containerized deployment)

## Setup and Running

### Local Development

1. **Build the Project**:
    ```sh
    mvn clean install
    ```

2. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

3. **Access the Application**:
   The application will be available at `http://localhost:8080`.

### Docker

1. **Build Docker Image**:
    ```sh
    docker build -t incident-management-backend .
    ```

2. **Run Docker Container**:
    ```sh
    docker run -p 8080:8080 incident-management-backend
    ```

## Security

This project uses Spring Security for authentication and authorization.

### Authentication

The application uses basic authentication with in-memory user details.

#### Default Users

- **User**:
    - Username: `user`
    - Password: `password`
    - Roles: `USER`
- **Admin**:
    - Username: `admin`
    - Password: `adminpassword`
    - Roles: `ADMIN`

### Accessing Secured Endpoints

- Most endpoints are secured and require authentication.
- Use the default usernames and passwords provided above to access the endpoints.
- Access is controlled based on roles:
    - **USER**: Can list (`GET /incidents`) and create (`POST /incidents`) incidents.
    - **ADMIN**: Can list (`GET /incidents`), create (`POST /incidents`), update (`PUT /incidents/{id}`), and delete (`DELETE /incidents/{id}`) incidents.


## API Documentation

This project uses `springdoc` to generate OpenAPI 3 documentation for the RESTful APIs.

- **Access API Documentation**:
  Once the application is running, access the API documentation at `http://localhost:8080/swagger-ui/index.html`.

## Testing

### Unit and Integration Tests

Unit tests are used to test individual components of the application in isolation, while integration tests test the integration of various components and their interactions with dependencies like databases.

1. **Run Unit and Integration Tests**:
    ```sh
    mvn test
    ```

2. **Test Results**:
   Test results for unit and integration tests can be found in the `target/surefire-reports` directory.

### Stress Tests

Stress tests are used to evaluate the application's performance under extreme conditions using Gatling.

1. **Start the Spring Boot Application**:
    ```sh
    mvn spring-boot:run
    ```

2. **Run Gatling Stress Tests**:
    ```sh
    mvn gatling:test
    ```

3. **Test Results**:
   Gatling test results can be found in the `target/gatling` directory. Open the HTML files in a browser for a detailed report.

## API Endpoints

| Method | Endpoint               | Description             |
|--------|------------------------|-------------------------|
| GET    | /incidents             | List all incidents      |
| POST   | /incidents             | Create a new incident   |
| GET    | /incidents/{id}        | Get incident by ID      |
| PUT    | /incidents/{id}        | Update incident by ID   |
| DELETE | /incidents/{id}        | Delete incident by ID   |

## Environment Variables

| Variable        | Default Value       | Description                     |
|-----------------|---------------------|---------------------------------|
| `SERVER_PORT`   | `8080`              | Port on which the server runs   |
| `DATABASE_URL`  | `jdbc:h2:mem:testdb`| Database connection URL         |

## Dependencies

- Spring Boot
- Spring Data JPA
- H2 Database (for local development)
- Docker (for production deployment)
- Gatling (for stress testing)
- springdoc-openapi (for API documentation)
- Spring Security (for authentication and authorization)

