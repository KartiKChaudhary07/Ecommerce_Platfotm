# BookNest - Modular Monolith E-commerce Bookstore

BookNest is a full-stack bookstore platform built with **Spring Boot 3** and **Angular 17**. It features a **Modular Monolith** architecture designed for future microservices conversion.

## 🚀 Features

- **Modular Monolith**: Each domain (Auth, User, Book, Cart, Order, Wallet, Review, Wishlist, Notification) is isolated.
- **Security**: JWT-based authentication with Role-Based Access Control (RBAC).
- **Premium UI**: Modern dark-themed UI with glassmorphism and smooth transitions.
- **Clean Architecture**: Domain-Driven Design (DDD) principles applied.
- **Admin Dashboard**: Comprehensive management of books and orders.
- **Wallet System**: Integrated wallet for purchases and credits.

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.5, Spring Security, JPA, MySQL, Lombok, Swagger.
- **Frontend**: Angular 17.3.0, RxJS, Reactive Forms, CSS3.

## 🏗️ Getting Started

### Prerequisites

- JDK 21
- Node.js 18+
- MySQL Server

### Database Setup

1. Create a MySQL database named `booknest`.
2. Update `backend/src/main/resources/application.properties` with your MySQL credentials.

### Run Backend

```bash
cd backend
mvn spring-boot:run
```
Swagger API Docs: `http://localhost:8080/swagger-ui/index.html`

### Run Frontend

```bash
cd frontend
npm install
npm start
```
Access the app at: `http://localhost:4200`

## 📦 API Modules

- `/api/v1/auth`: Registration and Login.
- `/api/v1/books`: Catalog, Search, Featured.
- `/api/v1/cart`: Cart management.
- `/api/v1/orders`: Order placement and history.
- `/api/v1/wallet`: Balance and transactions.
- `/api/v1/reviews`: Book ratings and reviews.
- `/api/v1/wishlist`: Save for later.
- `/api/v1/notifications`: User alerts.

## 🛣️ Future Roadmap (Microservices)

The code is structured to be easily split:
1. Each module has its own package `com.booknest.<module>`.
2. Inter-module communication is via Service interfaces.
3. Replace Service injections with Feign clients or WebClients for HTTP communication.
4. Split the single database into per-service databases.
