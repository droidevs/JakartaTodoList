# Jakarta TodoList Web Application

A simple and robust **Todo List Web Application** built using **Jakarta Servlet**, **JSP**, and **Bootstrap**, implementing user authentication, session management, validation, and full CRUD functionality for todos. This project follows a clean **MVC architecture** with repositories, services, and controllers.

---

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Error Handling](#error-handling)
- [Future Improvements](#future-improvements)
- [Author](#author)

---

## Features

- **User Authentication & Registration** with session management.
- **Secure Password Handling** with hashing (BCrypt).
- **CRUD Todos**: create, read, update, delete.
- **Todo Attributes**:
  - Title
  - Description
  - Status: `NEW`, `IN_PROGRESS`, `COMPLETED`, `OVERDUE`
  - Due Date (with validation)
- **Validation**:
  - Prevent invalid dates
  - Restrict status changes according to business rules
- **Automatic Overdue Detection** via scheduler.
- **Friendly UI** using Bootstrap and responsive design.
- **Global Exception Handling** with a unified error page.
- **MVC Architecture**:
  - **Controllers**: Servlets
  - **Services**: Business logic
  - **Repositories**: Database access
  - **Views**: JSPs under `/WEB-INF/views/`
- **Reusable Utilities**: `ViewResolver`, `ViewDispatcher`, `BusinessErrorMapper`.

---

## Technology Stack

- Jakarta Servlet 6.0+
- JSP / JSTL
- MySQL Database
- Bootstrap 5
- GlassFish Server (or compatible Jakarta EE server)
- Java 17+

---


## Setup and Installation

1. **Clone the repository**:

```bash
git clone https://github.com/droidevs/JakartaTodoList.git
cd jakartaTodolist
````

2. **Configure Database**:

* Create MySQL database:

```sql
CREATE DATABASE db_todo_list;
```

* Run the SQL script provided to create `users` and `todos` tables with relations.

3. **Update Database Credentials** in your `Datasource` or `DBUtils` class:

```java
private static final String URL = "jdbc:mysql://localhost:3306/db_todo_list";
private static final String USER = "root";
private static final String PASSWORD = "yourpassword";
```

4. **Build & Deploy**:

* Use NetBeans / Maven / your IDE to build and deploy to GlassFish.
* Access via: `http://localhost:8080/Jakarta-TodoList-web`

---

## Usage

1. **Home Page**: `/`

   * Shows app info and “Login” / “Sign Up” buttons.
   * If already logged in, redirects to `/todos`.

2. **Sign Up**: `/signup.jsp`

   * Full name, username, email, password, confirm password.

3. **Login**: `/login.jsp`

   * Authenticates user and starts session.

4. **Todos Dashboard**: `/todos`

   * List of todos for logged-in user.
   * Add, edit, delete todos with due dates and status.
   * Automatic overdue marking.

5. **Logout**: Ends session and redirects to home.

---

## Error Handling

* Unified **GlobalExceptionHandler** servlet at `/error-handler`.
* `error.jsp` handles:

  * 404 Not Found
  * Validation errors
  * Business errors
  * General internal errors
* Friendly UI with messages and optional redirect countdown.

---

## Future Improvements

* Add **search and filtering** of todos.
* Implement **user profile** with avatar.
* Add **pagination** for todos.
* Enhance UI with **Material Design** or **Tailwind CSS**.
* Add **email notifications** for overdue tasks.

---

## Author

**Mouad** – Android & Web Developer
Email: [mouadoumous@mail.com](mailto:mouadoumous@gmail.com)
LinkedIn: [https://linkedin.com/in/mouad-oumous](https://linkedin.com/in/https://www.linkedin.com/in/mouad-oumous/)


