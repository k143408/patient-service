# Patient Visit Management


## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Endpoints](#endpoints)
- [Testing](#testing)
- [Documentation](#documentation)
- [Dockerization](#dockerization)
- [Contributing](#contributing)

## Introduction

This web application is designed to manage patient data and their visits. It allows you to record and retrieve patient information and track their visits. Each visit contains details such as date and time, type of visit (e.g., home or doctor's office), reason for the visit, and family history.

## Features

- Create, read, update, and delete patient records.
- Create, read, update, and delete visit records for patients.
- Record visit details, including date and time, visit type, visit reason, and family history.
- API-driven architecture for easy integration with other systems.

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL (Database)
- Spring Web (RESTful API)
- Spring Validation
- OpenAPI (Swagger)
- Docker (Containerization)

## Getting Started

### Prerequisites

Before you start, make sure you have the following software installed on your system:

- Java Development Kit (JDK)
- Docker (if you plan to use Docker for containerization)
- PostgreSQL (if not using an in-memory database)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/patient-visit-webapp.git
   cd patient-visit-webapp

2. Build and run the application using Gradle:

    ```bash
    ./gradlew bootRun
The application should now be running locally.

### Usage
#### Endpoints
- Patient-related API endpoints:
    - GET /api/patients: Get a list of all patients.
    - GET /api/patients/{id}: Get a patient by ID.
    - POST /api/patients: Create a new patient.
    - PUT /api/patients/{id}: Update an existing patient.
    - DELETE /api/patients/{id}: Delete a patient.

- Visit-related API endpoints:

    - GET /api/visits: Get a list of all visits for a specific patient (provide patientId as a query parameter).
    - GET /api/visits/{id}: Get a visit by ID.
    - POST /api/visits: Create a new visit for a patient.
    - PUT /api/visits/{id}: Update an existing visit.
    - DELETE /api/visits/{id}: Delete a visit.

Please refer to the API documentation (Swagger) for more details on request and response formats.

### Testing
To run unit tests, use the following Gradle command:

```bash
./gradlew test
```

### Documentation
The API documentation is available via Swagger UI at /swagger-ui.html. You can access it by opening a web browser and navigating to http://localhost:8080/swagger-ui.html.

### Dockerization
To containerize the application using Docker, follow these steps:

Build a Docker image using Gradle:

```bash
docker -f DockerFile
```
Run the Docker container:

```bash
docker run -p 8080:8080 patient-visit-webapp
```

### Contributing
Contributions are welcome! If you have any improvements or bug fixes, please submit a pull request.