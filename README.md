# 📚 Library Management System

A Spring Boot-based library management system that provides a robust backend for managing library operations.

## Technologies Used

- **Java** - Core programming language
- **Spring Boot** - Application framework
- **Spring Data JPA** - Database interactions using Hibernate
- **Spring Security** - Authentication and Authorization
- **JWT (JSON Web Token)** - Stateless authentication
- **MySQL** - Relational database
- **Maven** - Dependency management
- **Bean Validation** - Input validation (Hibernate Validator)

## Key Features

### Authentication & Authorization
- Secure login system using JWT
- Role-based access control (Admin, Staff)
- Password encryption with BCrypt
- Stateless session management

### Book Management
- Create, Read, Update, Delete (CRUD) operations for books
- Manage book categories, authors, and publishers
- Handle book stock and availability
- File upload support for book covers
- Advanced search and filtering

### Reader Management
- Manage reader profiles and library cards
- Track reading history and borrowing status
- Support for different card types (Student, Teacher, etc.)

### Borrowing System
- Streamlined borrowing and returning process
- Automatic due date calculation
- Fine calculation for overdue books
- Track book condition (Damage status)

### Fine & Payment
- Automated fine calculation based on overdue days
- Manage fine payments and history
- Support for different payment methods

### Notification System
- Email notifications for borrowing confirmations (and potentially overdue reminders)

## Project Structure

The project follows a standard 3-layer architecture (Controller-Service-Repository) with clear separation of concerns:

```
src/main/java/com/library/LibraryManagement
├── controller/     # REST API endpoints (Handling HTTP requests)
├── dto/            # Data Transfer Objects (Request/Response models)
├── entity/         # JPA Entities (Database tables mapping)
├── mapper/         # Object mapping (Entity <-> DTO)
├── payload/        # Standardized API response wrappers
├── repository/     # Data Access Layer (Spring Data JPA interfaces)
├── scheduler/      # Scheduled tasks (e.g., checking overdue books)
├── security/       # JWT filters and security configuration
├── service/        # Business logic interfaces
│   └── impl/       # Business logic implementations
└── utils/          # Utility classes (JwtUtils, etc.)
```

## Getting Started

### Prerequisites
- JDK
- Maven
- MySQL

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/AndrewNguyenITVN/Library-Management-Springboot.git
   cd Library-Management-Springboot
   ```

2. **Configure Database**
   - Create a MySQL database named `library_management`.
   - Open `src/main/resources/application.yml` and update your database credentials:
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/library_management
         username: your_username
         password: your_password
     ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## Security Configuration

The application uses a custom JWT filter to intercept requests.
- **Public endpoints:** Login, Public search APIs
- **Protected endpoints:** All management APIs require a valid Bearer Token in the `Authorization` header.

## Contribution

Contributions are welcome! Please fork the repository and submit a pull request.
