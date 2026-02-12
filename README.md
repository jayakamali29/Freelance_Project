💼 Freelance Project Management System (Java)

A modular Freelance Project Management System developed in Java using layered architecture principles.
The application manages users, projects, and bids with proper validation, DAO-based database access, and custom exception handling. It demonstrates clean separation between business logic and persistence layers using JDBC with Oracle.

✨ Key Features
👤 User Management

Create and manage freelance users

Store and retrieve user details from database

DAO-based persistence handling

📁 Project Management

Create and track freelance projects

Maintain project lifecycle data

Prevent invalid project operations using validations

💰 Bid Handling

Place and manage bids for projects

Structured Bid entity with DAO operations

Business-rule validation before bid approval

🧠 Service Layer Logic

Centralized business logic in Service layer

Validation before database operations

Exception-driven workflow control

⚠️ Custom Exception Handling

Custom exceptions used to enforce business rules:

ValidationException

ProjectAwardingException

ActiveEngagementsExistException

🗄 Database Integration

Oracle database connectivity via JDBC

Centralized DB connection utility

ojdbc driver configured in project

🏗 Architecture

The project follows a layered architecture:

Main (App Layer)
   ↓
Service Layer (Business Logic)
   ↓
DAO Layer (Database Access)
   ↓
Oracle Database


This ensures:
Maintainability
Testability
Clean separation of concerns

📂 Project Structure
src/
 ├── com.freelance.app
 │     └── FreelanceMain.java
 │
 ├── com.freelance.bean
 │     ├── User.java
 │     ├── Project.java
 │     └── Bid.java
 │
 ├── com.freelance.dao
 │     ├── UserDAO.java
 │     ├── ProjectDAO.java
 │     └── BidDAO.java
 │
 ├── com.freelance.service
 │     └── FreelanceService.java
 │
 └── com.freelance.util
       ├── DBUtil.java
       ├── ValidationException.java
       ├── ProjectAwardingException.java
       └── ActiveEngagementsExistException.java

🛠 Technologies Used

Java SE (JDK 21)
JDBC
Oracle Database
DAO Pattern
Layered Architecture
Custom Exception Handling
OOP Principles

⚙️ Setup & Configuration
✅ Requirements

Java JDK 11+
Eclipse / IntelliJ IDEA

Oracle Database
JDBC Driver (ojdbc11.jar) added to project
SQL tables created as per project schema

🔌 Database Configuration

Update database credentials in:
com.freelance.util.DBUtil.java


Example fields to configure:

DB URL
Username
Password
Driver class

Provides:

DB connectivity
Custom exceptions
Shared utilities

🛡 Validation Rules (Examples)

Invalid user/project data rejected
Duplicate operations prevented
Project awarding rules enforced
Active engagement conflicts detected
Bid validation checks applied

🚀 Possible Enhancements

Add REST API layer
Add web interface (Servlet / Spring Boot)
Add authentication & roles
Add transaction management
Add logging framework
Add unit tests (JUnit)

👨‍💻 Author

Developed as a Java layered architecture project demonstrating DAO, Service, Bean, and Exception-driven design with Oracle JDBC integration.

<img width="1057" height="442" alt="image" src="https://github.com/user-attachments/assets/7d9354bf-8140-4ccc-859a-a47d5174de9f" />

<img width="1890" height="413" alt="image" src="https://github.com/user-attachments/assets/a8b965e2-2919-402c-8974-eae2329fdd5b" />

<img width="708" height="219" alt="image" src="https://github.com/user-attachments/assets/6265f8cb-57ce-4e70-8edf-14cb9f123d30" />


