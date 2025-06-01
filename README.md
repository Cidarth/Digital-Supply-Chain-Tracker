# 📦 Digital Supply Chain Tracker

A Spring Boot-based simulation project that digitally tracks and manages supply chain activities — from supplier to transporter to warehouse to retailer — ensuring visibility, transparency, and timely alerts.

---

## 🌐 Domain

Logistics / Manufacturing / Retail

---

## 🎯 Objectives

- Digitally track and monitor items across the supply chain.
- Record item statuses at every checkpoint.
- Provide role-based access to different users.
- Generate alerts for delays or damage.
- Offer real-time visibility with reports and dashboards.

---

## 🧱 Tech Stack

| Layer        | Technology       |
|--------------|------------------|
| Framework    | Spring Boot      |
| Security     | Spring Security  |
| Persistence  | Spring Data JPA  |
| Database     | MySQL            |
| Build Tool   | Maven            |
| Utilities    | Lombok           |
| Testing      | JUnit            |
| Documentation| Swagger (springdoc-openapi) |

---

## 🧩 Key Modules

- User & Role Management
- Item & Shipment Tracking
- Checkpoints & Event Logs
- Alerts & Notifications
- Reporting & Analytics

---

## 🔐 Roles & Access

| Role             | Access Description                                |
|------------------|----------------------------------------------------|
| Admin            | Full access, manage users and roles, view reports |
| Supplier         | Add items, create shipments                       |
| Transporter      | Update shipment and checkpoint status             |
| Warehouse Manager| Receive goods, confirm delivery                   |

---

## 🗃 Entity Overview

- **User**: `id`, `name`, `email`, `password`, `role (ADMIN, SUPPLIER, TRANSPORTER, MANAGER)`
- **Item**: `id`, `name`, `category`, `supplierId`, `createdDate`
- **Shipment**: `id`, `itemId`, `fromLocation`, `toLocation`, `expectedDelivery`, `currentStatus`, `assignedTransporter`
- **CheckpointLog**: `id`, `shipmentId`, `location`, `status`, `timestamp`
- **Alert**: `id`, `shipmentId`, `type (DELAY, DAMAGE)`, `message`, `createdOn`, `resolved`

---

## 🔁 REST API Endpoints

### 🔐 AuthController
- `POST /api/auth/register`  
- `POST /api/auth/login`  

### 👤 UserController (Admin)
- `GET /api/users`  
- `PUT /api/users/{id}/role`  

### 📦 ItemController (Supplier/Admin)
- `POST /api/items`  
- `GET /api/items`  
- `GET /api/items/{id}`  

### 🚚 ShipmentController
- `POST /api/shipments`  
- `PUT /api/shipments/{id}/assign`  
- `GET /api/shipments`  
- `GET /api/shipments/{id}`  
- `PUT /api/shipments/{id}/status`  

### 📍 CheckpointLogController
- `POST /api/checkpoints`  
- `GET /api/checkpoints/shipment/{id}`  

### 🚨 AlertController
- `GET /api/alerts`  
- `PUT /api/alerts/{id}/resolve`  

### 📊 ReportController
- `GET /api/reports/delivery-performance`  
- `GET /api/reports/delayed-shipments`

---

## 🧪 Example Workflow

1. Supplier registers an item and creates a shipment.
2. Transporter updates shipment status and checkpoints.
3. System logs each event and detects delivery delays.
4. Alerts and reports are generated accordingly.

---

## 🖼 ER Diagram

![ER](https://github.com/user-attachments/assets/3f7254ef-ec91-4c6c-88fd-0f732f6f4240)


---

## 🧭 Class Diagram

![class](https://github.com/user-attachments/assets/a002fb0b-08e2-4a71-b923-24f474b9481c)


---

## ⚙ Sample Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/supply_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
## 📁 Suggested Project Structure
com.supplytracker
```
├── controller # REST API endpoints
├── dto # Data Transfer Objects
├── entity # JPA entities
├── repository # Data access layer
├── service # Business logic
├── config # App configuration (security, Swagger, etc.)
├── regexValidation # Regex-based input validation
├── exception # Exception handlers and classes
└── SupplyTrackerApplication.java # Main Spring Boot app entry point
```


---

## 🛠 Optional Enhancements

| Feature            | Technology        |
|--------------------|-------------------|
| Email alerts       | JavaMailSender    |
| Delay detection    | Scheduled jobs    |
| Real-time updates  | WebSocket / Kafka |
| Dashboard UI       | Angular / React   |


---

## 🗂 Suggested Sprints

| Week | Deliverables                                 |
|------|----------------------------------------------|
| 1    | Setup project, user/role module, Spring Security |
| 2    | Item and shipment management                 |
| 3    | Checkpoint tracking, event logs              |
| 4    | Alerts and notifications module              |
| 5    | Reporting and dashboards                     |
| 6    | Testing, documentation, Swagger UI           |


---

## ▶️ How to Use the Project

### 🛠 Prerequisites

- Java 17+
- Maven or Gradle
- MySQL database
- (Optional) Postman for API testing
- (Optional) Swagger UI for API documentation


---

### 🚀 Steps to Run

#### 1. Clone the Repository

```bash
git clone https://github.com/Cidarth/digital-supply-chain-tracker.git
cd digital-supply-chain-tracker
```

#### 2. Set up the Database
Create a MySQL database named supply_tracker.
```
spring.datasource.url=jdbc:mysql://10.9.215.64:3306/springboot_team_db
spring.datasource.username=team_user
spring.datasource.password=localpass123
```

#### 3. Build and Run
Using Maven:
```
./mvnw clean install
./mvnw spring-boot:run
```

Using Gradle:
```
./gradlew build
./gradlew bootRun
```

#### 4. Access Swagger UI
Visit:
```
http://localhost:8080/swagger-ui.html
```
or 
```
http://localhost:8080/swagger-ui/index.html
```

#### 5. Register Users and Use API
- `Register: POST /api/auth/register`

- `Login: POST /api/auth/login`

Explore endpoints:

- `/api/items`

- `/api/shipments`

- `/api/checkpoints`

- `/api/alerts`

- `/api/reports`


---

## 📌 Tips
Add initial users and test data using Swagger or a custom data.sql.

Customize roles, item categories, or statuses based on your domain needs.

Monitor console logs for real-time updates and debugging.


---

## 👥 Authors
**Cidarth Narayan J** – Project Lead & Backend Developer

**Rick Brenton** – Item and Shipment Tracking & Testing

**Dheeraj Reddy Kurre** – Weekly Report Design & Exception Handling

**Akhilesh P S** – User and Role Management & Testing

**Kodidela Mohana Sreenath** – Alerts and Notifications & API Testing

**Adhitya Baskaran** – Checkpoints & Event Logs & Database Design

---

## 🤝 Contributors
Thanks to everyone who contributed through feedback, testing, or documentation.
