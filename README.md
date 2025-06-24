# DineConnect

DineConnect is a modern dining application that allows users to discover restaurants, filter them by rating and cuisine, and reserve tables seamlessly. The platform is designed to provide a smooth and secure experience for both diners and restaurant owners.

## Features

- **Browse Restaurants:** View a curated list of restaurants.
- **Filter Options:** Filter restaurants by rating and cuisine type.
- **Table Reservations:** Reserve tables at your favorite restaurants.
- **Secure Authentication:** User authentication and authorization with JWT.
- **API Documentation:** Interactive API docs with Swagger.

## Tech Stack

### Backend

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** with JWT integration for secure authentication.
- **MongoDB** for data storage.
- **Spring Data JPA** for MongoDB connectivity.
- **Swagger** for API documentation.

### Frontend

- **React** with **Vite** for fast development and build.
- **Tailwind CSS** for modern, responsive UI.

## Getting Started

### Prerequisites

- Java 21
- Node.js & npm
- MongoDB

### Backend Setup

1. Clone the repository.
2. Navigate to the backend directory.
3. Configure MongoDB connection in `application.properties`.
4. Run the Spring Boot application:

```sh
   ./mvnw spring-boot:run
 ```

Access Swagger docs at http://localhost:8080/swagger-ui.html.

### Frontend Setup
Navigate to the frontend directory.
Install dependencies:
```
    npm install
```
Start the development server:
 ```
    npm run dev
```