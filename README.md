# Order Management System

A Spring Boot REST API for managing products, processing orders with GST billing, and sending notifications via WhatsApp and email.

## Overview

This application provides product inventory management with stock tracking and threshold alerts. It processes customer orders with automatic bill generation including 18% GST calculation, integrates with Twilio for WhatsApp notifications, and sends email alerts for low stock conditions.

## Features

* Product Management
* Order Processing with Bill Generation
* 18% GST Calculation
* WhatsApp Order Notifications via Twilio
* Low Stock Email Alerts
* Scheduled CSV Product Reports

## Tech Stack

* Java 17
* Spring Boot 4.0.3
* Spring Data JPA
* Spring Web MVC
* Spring Validation
* Spring Mail
* MySQL
* Twilio SDK

## System Design / How It Works

1. Save products with name, price, stock quantity, and threshold level
2. Customer places order via `/purchase` endpoint with name, mobile, productId, quantity
3. Validate request fields and check product availability
4. Calculate bill: subtotal = price × quantity, GST = subtotal × 18%, total = subtotal + GST
5. Process payment (random success/failure simulation)
6. On payment failure: send WhatsApp message to customer
7. On payment success: reduce product stock, save order, send WhatsApp confirmation
8. If stock falls below threshold: send low stock alert email
9. Scheduled job runs every minute: generates CSV report and emails it

## Project Structure

```text
com.example.ordermanagementsystem
├── Application.java
├── controller
│   └── ProductsController.java
├── service
│   ├── ProductsService.java
│   ├── OrdersService.java
│   ├── EmailService.java
│   ├── SmsAndWhatsAppService.java
│   ├── CsvService.java
│   └── ProductReportScheduler.java
├── repository
│   ├── ProductsRepository.java
│   └── OrdersRepository.java
├── entity
│   ├── Products.java
│   └── Orders.java
└── dto
    ├── RequestOrdersDto.java
    └── BillDetails.java
```

## Setup & Installation

1. Ensure Java 17 and Maven are installed
2. Create MySQL database
3. Configure `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD
   
   twilio.account.sid=YOUR_TWILIO_ACCOUNT_SID
   twilio.auth.token=YOUR_TWILIO_AUTH_TOKEN
   twilio.whatsapp.number=YOUR_TWILIO_WHATSAPP_NUMBER
   
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=YOUR_EMAIL_USERNAME
   spring.mail.password=YOUR_EMAIL_PASSWORD
   ```
4. Run `mvn spring-boot:run`

## API Endpoints

Base path: `http://localhost:8080/`

### 1) Save Products

* **POST** `/saveProduct`
* **Request Body**: Array of products

```http
POST /saveProduct
[
  {"productName":"Laptop","price":50000,"stock":10,"threshHold":3}
]
```

### 2) Get All Products

* **GET** `/getAllProducts`

```http
GET /getAllProducts
```

### 3) Purchase Product

* **POST** `/purchase`
* **Request Body**: Order details

```http
POST /purchase
{
  "name":"John",
  "mobileNumber":"9876543210",
  "productId":1,
  "quantity":2
}
```

## Database Schema

### `products`

* `id` (PK)
* `productName`
* `price`
* `stock`
* `threshHold`

### `orders`

* `id` (PK)
* `name`
* `mobileNumber`
* `productId`
* `quantity`
* `total`
* `gstOnTotal`
* `totalAmount`

## Configuration Notes

* `spring.application.name=ordermanagementsystem`
* `spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME`
* `spring.datasource.username=YOUR_DB_USERNAME`
* `spring.datasource.password=YOUR_DB_PASSWORD`
* `spring.jpa.hibernate.ddl-auto=update`
* `twilio.account.sid=YOUR_TWILIO_ACCOUNT_SID`
* `twilio.auth.token=YOUR_TWILIO_AUTH_TOKEN`
* `twilio.whatsapp.number=YOUR_TWILIO_WHATSAPP_NUMBER`
* `spring.mail.host=smtp.gmail.com`
* `spring.mail.port=587`
* `spring.mail.username=YOUR_EMAIL_USERNAME`
* `spring.mail.password=YOUR_EMAIL_PASSWORD`

## Future Improvements

* Add authentication and authorization
* Implement actual payment gateway integration
* Add order status tracking
* Add pagination to product listing
* Support multiple payment methods
