# Rentique: Equipment Rental System

## Overview

**Rentique** is a web-based Equipment Rental Management System that allows customers to rent various equipment like cameras, power tools, and musical instruments. The system manages equipment availability, rental transactions, overdue tracking, returns, and reporting.

This project demonstrates end-to-end development using modern web and backend technologies, quality-assurance tools, and enterprise-level architecture.

---

## Features

- ✅ **User Authentication & Authorization**
  - JWT Token-based security for secure access.
  - Separate roles: Admin and Customer.
  
- ✅ **Equipment & Category Management**
  - Equipment Master & Category Master forms.
  - Track stock, rental price, and availability status.

- ✅ **Customer & Rental Management**
  - Customer Registration and login.
  - Create rental orders with multiple items.
  - Return management with late fees and condition tracking.

- ✅ **Reports & Charts**
  - Equipment-wise rental report.
  - Daily rental summary.
  - Return and overdue status reports.
  - Visual dashboards:
    - 📊 Bar chart: Rentals per category.
    - 📈 Line chart: Daily rental trends.
    - 🥧 Pie chart: Availability status.

- ✅ **Exception Handling & Logging**
  - Robust exception management.
  - Centralized logging with meaningful error messages.

- ✅ **Code Quality & Testing**
  - ✔️ **SonarQube** integration for static code analysis and quality checks.
  - ✅ **JUnit & Mockito** based unit testing.

---

## Technologies Used

### Backend
- **Java**, **Spring Boot**
- **Hibernate** (JPA)
- **MySQL** (Relational Database)
- **JWT** (JSON Web Tokens)
- **JUnit**, **Mockito** (Testing)

### Frontend
- **HTML**, **CSS**, **JavaScript**

### DevOps & Tools
- **SonarQube** for code quality
- **Postman** for API testing
- **Maven** for build management

---

## Database Schema

The system uses the following main tables:

| Table | Description |
|-------|-------------|
| `Users` | User credentials and type (Admin/Customer) |
| `Equipment` | Item details including price and availability |
| `Categories` | Equipment categories |
| `Rentals` | Rental transactions |
| `RentalItems` | Items per rental transaction |
| `Returns` | Return records and late fees |

---

## Use Cases

- 🧑‍💼 Admins can manage equipment, categories, and generate reports.
- 🧑 Customers can register, log in, rent equipment, and return items.
- 📅 The system tracks rentals, calculates charges based on date, and handles overdue scenarios.

---

## Installation & Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/rentique.git
