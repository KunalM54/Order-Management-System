# Order Management System

## Overview
Order Management System is a Spring Boot backend project for product and order handling with inventory updates, billing calculation, WhatsApp notifications, low-stock email alerts, and scheduled product report generation in CSV format.

## Features
- Add multiple products.
- Fetch all products.
- Purchase flow with request validation checks.
- Product existence and stock availability validation.
- Bill generation with GST calculation (18%).
- Payment simulation (success/failure).
- Auto stock reduction after successful order.
- Low-stock email alert when stock reaches threshold.
- WhatsApp message on successful order.
- WhatsApp message on payment failure.
- Scheduled CSV report generation and email attachment delivery.

## Tech Stack
- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Validation (DTO annotations present)
- Spring Mail
- Spring Scheduling
- MySQL
- Twilio WhatsApp API
- Maven

## System Design / How It Works
1. Product data is stored in the `products` table.
2. Client places order using `/purchase`.
3. Service validates:
   - Name
   - Mobile number (10 digits)
   - Product ID
   - Quantity (> 0)
4. Product is fetched by `productId` and stock is checked.
5. Bill is calculated:
   - `total = price * quantity`
   - `gst = total * 18%`
   - `finalAmount = total + gst`
6. Payment is simulated randomly.
   - On failure: WhatsApp payment-failed message is sent.
   - On success:
     - stock is reduced
     - order is saved in `orders`
     - low-stock alert email is sent if `stock <= threshHold`
     - WhatsApp order confirmation is sent
7. Scheduler runs every minute and:
   - fetches all products
   - generates `products_report.csv`
   - sends report as email attachment

## Project Structure
```text
src/main/java/com/ordermanagement/system
├── controller
│   └── ProductsController.java
├── service
│   ├── ProductsService.java
│   ├── OrdersService.java
│   ├── SmsAndWhatsAppService.java
│   ├── EmailService.java
│   ├── CsvService.java
│   └── ProductReportScheduler.java
├── entity
│   ├── Products.java
│   └── Orders.java
├── repository
│   ├── ProductsRepository.java
│   └── OrdersRepository.java
├── dto
│   ├── RequestOrdersDto.java
│   └── BillDetails.java
└── Application.java
```

## Setup & Installation
1. Install Java 17, Maven, and MySQL.
2. Create a MySQL database (currently configured as `ecommerce`).
3. Configure `src/main/resources/application.properties`:
   - `spring.datasource.username`
   - `spring.datasource.password`
   - `twilio.account.sid`
   - `twilio.auth.token`
   - `twilio.whatsapp.number`
   - `spring.mail.username`
   - `spring.mail.password`
4. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### 1) Save Products
- **POST** `/saveProduct`
- **Body**: list of products

Example:
```json
[
  {
    "productName": "Laptop",
    "price": 50000,
    "stock": 15,
    "threshHold": 5
  }
]
```

### 2) Get All Products
- **GET** `/getAllProducts`

### 3) Purchase Product
- **POST** `/purchase`
- **Body**:
```json
{
  "name": "Kunal",
  "mobileNumber": "9876543210",
  "productId": 1,
  "quantity": 2
}
```

**Current response behavior:**
- Returns validation/error text if any check fails.
- Returns `"Success"` when purchase flow completes in controller.

## Database Schema

### `products`
- `id` (PK, auto-generated)
- `product_name`
- `price`
- `stock`
- `thresh_hold`

### `orders`
- `id` (PK, auto-generated)
- `name` (not null)
- `mobile_number` (not null, length 10)
- `product_id` (not null)
- `quantity` (not null)
- `total`
- `gst_on_total`
- `total_amount`

## Configuration Notes
- `spring.jpa.hibernate.ddl-auto=update` is enabled.
- Scheduling is enabled with `@EnableScheduling`.
- Report scheduler cron: `0 * * * * ?` (every minute).
- Report receiver is read from:
  - `app.report.email.to` if set
  - otherwise fallback to `spring.mail.username`
- From email is read from:
  - `app.mail.from` if set
  - otherwise fallback to `spring.mail.username`

## Future Improvements
- Add `@Valid` in controller request handling to use DTO validation annotations directly.
- Return structured API responses instead of plain strings.
- Add global exception handling.
- Add order history and order fetch APIs.
- Add unit and integration tests.
- Move payment simulation to real payment gateway integration.
