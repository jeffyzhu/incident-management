# Incident Management Frontend

## Overview

This is the frontend application for the Incident Management system. It is built using React and communicates with the backend service to provide a user-friendly interface for managing incidents.

## Prerequisites

- Node.js (version 14 or later)
- npm (version 6 or later)

## Setup and Running

### Local Development

1. **Install Dependencies**:
    ```sh
    npm install
    ```

2. **Run the Application**:
    ```sh
    npm start
    ```

3. **Access the Application**:
   The application will be available at `http://localhost:3000`.

### Docker

1. **Build Docker Image**:
    ```sh
    docker build -t incident-management-frontend .
    ```

2. **Run Docker Container**:
    ```sh
    docker run -p 3000:3000 incident-management-frontend
    ```

## Environment Variables

Create a `.env` file in the root directory of the project and add the following variable:

```env
REACT_APP_API_URL=http://localhost:8080
```

- `REACT_APP_API_URL`: The base URL of the backend API.

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Authentication

The frontend uses JWT-based authentication to interact with the backend. Upon successful login, a token is stored in the local storage and is included in the Authorization header for subsequent requests.

## API Endpoints

The frontend communicates with the following backend API endpoints:

- **Authentication**:
    - `POST /auth/login`: Authenticate users and obtain a JWT.

- **Incidents**:
    - `GET /incidents`: List all incidents.
    - `POST /incidents`: Create a new incident.
    - `GET /incidents/{id}`: Get incident by ID.
    - `PUT /incidents/{id}`: Update incident by ID.
    - `DELETE /incidents/{id}`: Delete incident by ID.

## Dependencies

- React
- react-router-dom
- axios (for API requests)
- jwt-decode (for decoding JWT tokens)
- Redux (for state management) - if applicable
- Material-UI (for UI components) - optional

## License

This project is licensed under the MIT License. See the [LICENSE](../LICENSE) file for more details.