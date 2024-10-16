# Incident Management System

## Overview

The Incident Management System aims to provide a comprehensive solution for reporting, tracking, and managing incidents. This project consists of two main parts:
- **Backend**: RESTful API built with Spring Boot.
- **Frontend**: User interface built with React for managing incidents.

## Prerequisites

- Java Development Kit (JDK) version 17
- Maven
- Docker and Docker Compose
- Node.js (version 14 or later)
- npm (version 6 or later)

## Repository Structure

```plaintext
.
├── backend
│   ├── src
│   ├── pom.xml
│   ├── Dockerfile
│   └── README.md
├── frontend
│   ├── src
│   ├── package.json
│   ├── Dockerfile
│   └── README.md
├── docker-compose.yml
└── README.md (This file)
```

## Setup and Running

### Clone the Repository

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/your-username/incident-management.git
    cd incident-management
    ```

### Using Docker Compose

If you prefer to use Docker Compose, follow these steps:

1. **Build and Run the Containers**:
    ```sh
    docker-compose up --build
    ```

2. **Access the Application**:
    - Backend API: `http://localhost:8080`
    - Frontend Application: `http://localhost:3000`

### Manual Setup

If you prefer to run the applications manually, follow these steps:

#### Backend

1. **Navigate to the backend directory**:
    ```sh
    cd backend
    ```

2. **Install Dependencies**:
    ```sh
    mvn install
    ```

3. **Package the Application**:
    ```sh
    mvn package
    ```

4. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

5. **Access API**:
   The API will be available at `http://localhost:8080`.

For detailed information, refer to the [Backend README](backend/README.md).

#### Frontend

1. **Navigate to the frontend directory**:
    ```sh
    cd frontend
    ```

2. **Install Dependencies**:
    ```sh
    npm install
    ```

3. **Run the Application**:
    ```sh
    npm start
    ```

4. **Access the Application**:
   The application will be available at `http://localhost:3000`.

For detailed information, refer to the [Frontend README](frontend/README.md).

## Dependencies

### Backend
- Spring Boot
- Spring Security (for authentication and authorization)
- Spring Data JPA (for database interactions)
- H2 Database (for development and testing)
- Maven

### Frontend
- React
- react-router-dom
- axios (for API requests)
- jwt-decode (for decoding JWT tokens)
- Redux (for state management) - if applicable
- Material-UI (for UI components) - optional


## Contact

For any questions or issues, please contact [1853208@qq.com].