# E-Commerce Microservices Architecture



## Overview

This project is an **E-Commerce Microservices Architecture** built using **Spring Boot 3** and **Spring Cloud**. It follows a distributed microservices approach with key components for handling customer data, product catalog, orders, payments, and notifications.



![E-Commerce Microservices Architecture](https://github.com/user-attachments/assets/4474e1ba-fc96-4755-833c-27309726efea)




## Tech Stack

- **Backend:** Spring Boot 3, Spring Cloud
- **Frontend:** Angular
- **API Gateway:** Spring Cloud Gateway
- **Service Discovery:** Eureka Server
- **Configuration Management:** Spring Cloud Config Server
- **Database:** MongoDB, PostgreSQL
- **Messaging:** Apache Kafka
- **Distributed Tracing:** Zipkin
- **Containerization:** Docker

## Microservices Architecture

The system consists of the following microservices:

### 1. **API Gateway**

- Acts as the single entry point for frontend applications.
- Routes requests to the respective microservices.

### 2. **Customer Service**

- Manages customer-related data.
- Stores data in MongoDB.

### 3. **Product Service**

- Handles product details and inventory.
- Uses PostgreSQL for storage.

### 4. **Order Service**

- Manages customer orders.
- Stores order details in a database.
- Communicates with the **Payment Service**.

### 5. **Payment Service**

- Handles payment transactions.
- Sends payment confirmation asynchronously via Kafka.

### 6. **Notification Service**

- Listens to Kafka topics for order and payment confirmations.
- Sends email or push notifications to customers.

### 7. **Eureka Server**

- Service registry for discovering microservices.

### 8. **Config Server**

- Manages external configurations for microservices.

### 9. **Zipkin**

- Provides distributed tracing for monitoring requests across microservices.

## How It Works

1. The **Angular frontend** sends requests to the **API Gateway**.
2. The gateway routes requests to the appropriate microservices:
   - `/customers` → **Customer Service**
   - `/products` → **Product Service**
   - `/orders` → **Order Service**
3. The **Order Service** interacts with the **Payment Service**.
4. The **Payment Service** sends confirmations asynchronously using **Kafka**.
5. The **Notification Service** listens to Kafka events and notifies users.
6. **Zipkin** traces requests across services for monitoring and debugging.
7. **Eureka Server** enables service discovery, and the **Config Server** provides centralized configuration management.

## Prerequisites

- Java 17+
- Spring Boot 3
- Docker & Docker Compose
- MongoDB & PostgreSQL
- Apache Kafka & Zookeeper
- Angular CLI (for frontend development)

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/ecommerce-microservices.git
   cd ecommerce-microservices
   ```
2. Start dependent services (Kafka, MongoDB, PostgreSQL) using Docker Compose:
   ```sh
   docker-compose up -d
   ```
3. Build and run each microservice:
   ```sh
   cd customer-service
   mvn spring-boot:run
   ```
   Repeat this for other services.
4. Run the Angular frontend:
   ```sh
   cd frontend
   npm install
   ng serve
   ```

## Future Enhancements

- Implement OAuth2 for authentication.
- Add circuit breaker using Resilience4J.
- Improve monitoring with Prometheus & Grafana.

## License

This project is licensed under the MIT License. Feel free to contribute and improve it!

