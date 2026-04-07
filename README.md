# 🍽️ Crave

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Authentication-6DB33F?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-black?logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)

Crave is a backend system for a online food ordering platform. It covers the core backend flow for user and restaurant onboarding, JWT-based authentication, restaurant management, menu publishing, cart handling, and order placement.

This repository is best understood as an engineering-focused portfolio project rather than a production-ready SaaS backend.

---

## 📖 Overview

Crave focuses on the backend mechanics behind a food delivery or ordering system:

- User registration and login
- JWT access and refresh token flow
- Restaurant onboarding
- Menu category and menu item management
- Cart creation and updates
- Order placement from cart state

---

## 🧱 Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Security
- PostgreSQL
- JJWT
- Maven

---

## Features Implemented

- User registration and login with JWT access and refresh tokens
- Refresh-token rotation with hashed refresh-token storage
- Restaurant registration with image upload
- Menu category and menu item creation with image upload
- Restaurant discovery with cursor-based pagination
- Menu browsing by restaurant and category
- Cart management per restaurant
- Order placement from a cart
- Centralized validation and exception handling
- Request correlation IDs in application logs

---

## 🏗️ Architecture

The project follows a layered backend structure:

- `controller` for HTTP endpoints
- `service` for business logic and transactions
- `repository` for persistence
- `entity` for domain models
- `dto` for request and response contracts
- `validation` for custom validation rules
- `config/security` for JWT and request security
- `global exception handler` for centralized error handling

This structure keeps the project readable and reasonably maintainable which is important for both team collaboration and long-term backend growth.

---

## 🌐 API Surface

Main endpoints currently available:

- `POST /user`  
  Register a new user account.

- `POST /auth/login`  
  Authenticate a user and return JWT access and refresh tokens.

- `POST /auth/refresh`  
  Validate the refresh token and issue a new token pair.

- `GET /user/restaurant`  
  Fetch restaurants owned by the authenticated user.

- `POST /restaurant`  
  Register a new restaurant with multipart form data and image upload.

- `GET /restaurant`  
  Browse restaurants using cursor-based pagination.

- `GET /restaurant/{restaurantId}/menu-category`  
  Fetch the full menu for a restaurant.

- `POST /restaurant/{restaurantId}/menu-category`  
  Create a new menu category for a specific restaurant.

- `PUT /restaurant/{restaurantId}/cart/cart-item/{menuItemId}`  
  Add or update a cart item for the selected restaurant and menu item.

- `GET /cart`  
  Fetch all carts for the authenticated user.

- `GET /cart/{cartId}`  
  Fetch a single cart with its current items.

- `POST /cart/{cartId}/order`  
  Place an order using the current state of a cart.

- `GET /menu-category/{menuCategoryId}/menu-item`  
  Fetch all menu items under a specific menu category.

- `POST /menu-category/{menuCategoryId}/menu-item`  
  Create a new menu item under a specific menu category.

- `GET /menu-item/{menuItemId}`  
  Fetch details for a single menu item.

- `GET /restaurant/{restaurantId}/image`  
  Fetch the image associated with a restaurant.

- `GET /menu-item/{menuItemId}/image`  
  Fetch the image associated with a menu item.

---

## 🧩 Core Domain

The project models the following backend entities:

- User
- Restaurant
- MenuCategory
- MenuItem
- Cart
- CartItem
- Order
- OrderItem
- RefreshToken

---

## 🚦 Project Status

Crave is a backend portfolio project that demonstrates the core building blocks of a transactional system. It is functionally complete for the main food-ordering workflow, while still leaving room for future production-grade additions.

---

## 📝 Closing Note

This project is intended to showcase backend engineering fundamentals beyond basic CRUD. The focus is on clean architecture, secure authentication, domain modeling, validation, and maintainable design in a realistic application setting.
