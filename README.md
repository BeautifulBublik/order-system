# Order System

Demonstration project for a microservice order processing system: order → payment → delivery, with synchronous REST interaction and asynchronous integration via Kafka.

## Technology Stack

- **Java 21**
- **Spring Boot 4.1.0** (Spring MVC, Spring Data JPA, Spring Kafka)
- **Gradle (Kotlin DSL)** — multi-module build
- **PostgreSQL** — data storage (shared `orders` database for all services)
- **Apache Kafka** — asynchronous event-driven integration between services
- **MapStruct** — DTO ↔ Entity mapping
- **Lombok**
- **springdoc-openapi** — Swagger UI / OpenAPI documentation
- **JUnit 5** — testing
- **Docker Compose** — local infrastructure deployment (Postgres, Kafka)

## Architecture

The project consists of four Gradle modules:

| Module | Port | Purpose |
|---|---|---|
| `order-service` | 8080 | Order creation, payment initiation, final order status |
| `payment-service` | 8081 | Order payment processing |
| `delivery-service` | 8082 | Courier assignment and delivery after payment |
| `common-libs` | — | Shared DTOs (`http.order`, `http.payment`) and Kafka events (`kafka.*`) used by all services |

### Order Processing Flow

1. Client creates an order: `POST /api/v1/orders` (`order-service`) — order is saved with `PENDING_PAYMENT` status, amount is calculated.
2. Client initiates payment: `POST /api/v1/orders/{id}/pay` — `order-service` synchronously (REST) calls `payment-service` to create a payment.
3. `payment-service` creates/finds a payment and returns the result (`payment-service` — REST API, separate `payments` endpoint).
4. `order-service` transitions the order to `PAID` or `PAYMENT_FAILED` status and publishes `OrderPaidEvent` to the Kafka topic `order.event`.
5. `delivery-service` listens to `order.event`, assigns a courier, and publishes `DeliveryAssignedEvent` to the `delivery.events` topic.
6. `order-service` listens to `delivery.events` and transitions the order to `DELIVERY_ASSIGNED` status, storing courier data and ETA.

Interaction between `order-service` and `payment-service` is synchronous (HTTP), while communication with `delivery-service` is asynchronous (Kafka), which decouples services in terms of execution time.

## Running

1. Start the infrastructure (Postgres, Kafka):
```bash
   docker-compose up -d
```
2. Run the services (each as a separate Gradle module):
```bash
   ./gradlew :order-service:bootRun
   ./gradlew :payment-service:bootRun
   ./gradlew :delivery-service:bootRun
```
3. Swagger UI is available on each service at `/swagger-ui.html`.

## Repository Structure

```
order-system/
├── common-libs/       
├── order-service/      
├── payment-service/      
├── delivery-service/      
├── docker-compose.yaml    
└── settings.gradle.kts
```
