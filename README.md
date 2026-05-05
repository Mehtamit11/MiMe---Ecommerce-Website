# MiMe - Premium Java E-Commerce Platform

## 🚀 Features
- User authentication (Spring Security + BCrypt)
- Product catalog
- Cart & checkout
- Wishlist
- Admin panel (CRUD + stats)
- Analytics dashboard (Chart.js)
- AI-based recommendations

## 🧱 Tech Stack
- Java (Spring Boot)
- Thymeleaf
- PostgreSQL
- Hibernate (JPA)
- Maven

## ▶️ Run Locally

### 0. Prerequisites
- JDK 17+ (`java -version`)
- Maven 3.9+ (`mvn -version`)

> If you see `release version 17 not supported`, your machine is using an older JDK. Install JDK 17+ and make sure `JAVA_HOME` points to it before running Maven.

### 1. Create Database (optional if using PostgreSQL)
CREATE DATABASE mime_db;

### 2. Configure database
- For PostgreSQL, set `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD`.
- For quick local run, defaults use H2 in-memory DB from `application.properties`.

### 3. Run
mvn spring-boot:run

## 🔐 Default Admin
admin@mime.com / admin123

## 🌐 Routes
- `/` Home
- `/products`
- `/admin/dashboard`
- `/admin/analytics`
- `/recommendations`

## 📊 Future Scope
- Stripe integration
- Microservices
- Docker deployment
