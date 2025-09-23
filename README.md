# Library Management System

A Spring Boot-based library management system that provides a robust backend for managing library operations.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL Database
- JWT Authentication
- Maven

## Features

- User Authentication and Authorization
- Book Management
- Member Management
- Borrowing System
- Security with JWT

## Getting Started

1. Clone the repository
```bash
git clone https://github.com/AndrewNguyenITVN/Library-Management-Springboot.git
```

2. Configure MySQL database
- Create a new database
- Update application.properties with your database credentials

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

## Project Structure

```
src/main/java
├── com.librabry
│   ├── config/         # Configuration classes
│   ├── controller/     # REST controllers
│   ├── model/         # Entity classes
│   ├── repository/    # Data access layer
│   ├── service/       # Business logic layer
│   └── security/      # Security related classes
```

## Security

The application uses JWT (JSON Web Token) for authentication and authorization. Make sure to include the JWT token in the Authorization header for protected endpoints.

## License

This project is licensed under the MIT License.
