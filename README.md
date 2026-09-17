# Smart Employee Leave Management

A role-based employee leave management system built using **Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate, MySQL, HTML, CSS, and JavaScript**.

The application provides a structured leave management workflow where employees can apply for leave, managers can handle requests from their direct reports, and administrators can oversee the complete system.

---

## 🚀 Features

* Role-based login and access control
* Employee leave application
* Leave balance tracking
* Manager-based leave approval
* Direct manager hierarchy
* Manager team management
* Employee account creation
* Admin employee management
* Leave request status tracking
* Audit trail
* REST API based backend
* MySQL database integration
* Swagger API documentation

---

## 🔄 Leave Approval Workflow

The application follows a **direct-manager approval hierarchy**.

```text
Employee
   ↓
Direct Manager
   ↓
Approve / Reject
```

Managers are also employees in the system, so a manager can apply for leave to their own manager.

For example:

```text
Admin
  ↓
Manager
  ↓
Employee
```

In this structure:

* Employee's leave → Manager handles it
* Manager's leave → Admin or their assigned manager handles it
* Admin → Can oversee all leave requests

---

## 👥 User Roles

### Employee

Employees can:

* Log in to the system
* Apply for leave
* View their own leave requests
* Track leave status
* View their leave information

### Manager

Managers can:

* Apply for their own leave
* View their direct team
* View leave requests from direct reports
* Approve or reject team leave requests
* Create employee accounts
* Manage employees in their team

### Admin

Administrators can:

* View employees
* Create employee, manager, and admin accounts
* Assign managers
* View all leave requests
* Approve or reject leave requests
* View audit history
* Oversee the complete leave management system

---

## 📋 Business Rules

1. Employees can submit leave only for their own account.
2. Leave cannot start in the past.
3. The end date cannot be before the start date.
4. Pending or approved leave periods cannot overlap.
5. Approved leave deducts the employee's leave balance.
6. Rejected leave does not deduct leave balance.
7. Cancelling approved leave restores the leave balance.
8. Managers can handle leave requests only from their direct reports.
9. Admin can oversee all leave requests.
10. Managers can create employees for their own team.
11. Employees created by a manager are automatically assigned to that manager.
12. Admin can create supported roles and assign managers.
13. Deactivating an employee disables their login while preserving historical records.

---

## 🛠️ Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven
* REST APIs

### Frontend

* HTML5
* CSS3
* JavaScript

### Database

* MySQL

### API Documentation

* Swagger / OpenAPI

---

## 📁 Project Structure

```text
Smart-Employee-Leave-Management
│
├── backend
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── sneha
│   │   │   │           └── leavemanagement
│   │   │   │               ├── config
│   │   │   │               ├── controller
│   │   │   │               ├── dto
│   │   │   │               ├── exception
│   │   │   │               ├── model
│   │   │   │               ├── repository
│   │   │   │               ├── security
│   │   │   │               └── service
│   │   │   │
│   │   │   └── resources
│   │   │       └── static
│   │   │           ├── index.html
│   │   │           ├── style.css
│   │   │           └── app.js
│   │   │
│   │   └── test
│   │
│   └── pom.xml
│
├── frontend
│   ├── index.html
│   ├── style.css
│   └── app.js
│
├── database.sql
├── ARCHITECTURE.md
├── README.md
└── .gitignore
```

---

## 🧩 Backend Architecture

The backend is organized into different layers:

| Layer        | Purpose                                  |
| ------------ | ---------------------------------------- |
| `controller` | Handles REST API requests                |
| `service`    | Contains application and business logic  |
| `repository` | Handles database operations              |
| `model`      | Contains JPA entities                    |
| `dto`        | Handles API request and response data    |
| `security`   | Handles authentication and authorization |
| `config`     | Application and security configuration   |
| `exception`  | Handles API exceptions and errors        |

---

## 🗄️ Database Setup

Create the MySQL database before running the application.

```sql
CREATE DATABASE IF NOT EXISTS employee_leave_management;
```

The application uses Hibernate with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This allows Hibernate to create and update the required database tables based on the application's entities.

The project also includes:

```text
database.sql
```

for database-related setup.

---

## ▶️ How to Run

### 1. Clone or download the repository

Download the project and open it in **VS Code** or another Java IDE.

### 2. Configure MySQL

Make sure MySQL is running and create the database:

```sql
CREATE DATABASE employee_leave_management;
```

Configure your local MySQL username and password in the Spring Boot application configuration.

**Do not commit real database passwords to GitHub.**

### 3. Open the backend folder

Open a terminal inside:

```text
backend
```

### 4. Start the Spring Boot application

Run:

```powershell
mvn clean spring-boot:run
```

### 5. Open the application

Once the server starts, open:

```text
http://localhost:8080
```

The frontend is served by the Spring Boot application.

---

## 📖 API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

It can be used to view and test the available REST APIs.

---

## 🔐 Security

The application uses **Spring Security** for authentication and role-based authorization.

Access to different features is controlled based on the user's role:

```text
ADMIN
MANAGER
EMPLOYEE
```

Users can access only the functionality permitted for their role.

---

## 🖥️ Application Modules

The application includes:

* Login
* Overview Dashboard
* My Leave
* Leave Approvals
* My Team
* Add Employee
* Audit Trail

The available modules depend on the user's role.

---

## 📌 Project Purpose

The purpose of this project is to provide a simple and structured system for managing employee leave requests while maintaining a clear manager-based approval workflow.

It demonstrates the use of:

* Java and Spring Boot
* REST API development
* Database integration
* Role-based authorization
* JPA/Hibernate
* Manager hierarchy
* Frontend and backend integration

---

## 👩‍💻 Author

**Sneha Challa**

B.Tech – Computer Science and Engineering

RGUKT Ongole
