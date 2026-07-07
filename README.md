# Eventify Platform - Planning Bounded Context

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.4-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-orange)

## Summary
The Planning Bounded Context is the core module for social event management in Eventify Platform, handling the complete lifecycle of social events from creation to completion. Designed following Domain-Driven Design (DDD) principles with Clean Architecture and CQRS pattern implementation.

## Key Features
- **Event Lifecycle Management**: Full CRUD operations for social events
- **State Management**: Four-state system (Active/To Confirm/Cancelled/Completed)
- **Advanced Search**: Filter events by status, title, or organizer
- **Batch Operations**: Bulk event deletion capabilities
- **Temporal Planning**: Date validation ensuring future-dated events
- **Location Management**: Structured address handling with validation

## Technology Stack
- **Core Framework**: Spring Boot 3.1.4
- **Persistence**: Spring Data JPA + MySQL 8.0
- **Validation**: Jakarta Bean Validation 3.0
- **API Documentation**: Spring REST Docs
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Testcontainers

## Getting Started

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Installation
```bash
git clone https://github.com/AngelDevs-Open/eventify-platfom.git
cd eventify-platfom
mvn clean install
```

### Configuration
Create `application.properties` with:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventify
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## API Documentation
### Core Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | /api/events | Create new social event |
| PUT    | /api/events/{id} | Update existing event |
| DELETE | /api/events/{ids} | Delete single/multiple events |
| GET    | /api/events?status={status} | Filter events by status |

### Customer-specific Endpoints
```
GET /api/customers/{customerId}/events
```

## Data Model
```java
public class SocialEvent {
    private UUID id;
    private SocialEventTitle title;
    private SocialEventDate eventDate;
    private Place location;
    private CustomerName organizer;
    private SocialEventStatus status;
    // Additional audit fields
}
```

## Testing
Preloaded test data includes:
- 50+ sample events across categories:
    - Weddings
    - Corporate Events
    - Birthday Parties
    - Graduation Ceremonies
- Multiple status combinations for testing transitions

Run tests with:
```bash
mvn test
```

## Contributing
1. Fork the repository
2. Create feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m 'Add some feature'`
4. Push to branch: `git push origin feature/your-feature`
5. Open a Pull Request


## **Authors**

This project is maintained by the AngelDevs-Web team and contributors:

|            **Alumno**            | **Codigo** |
|:--------------------------------:|:----------:|
| Fabrizio Alexander Cutiri Agüero | U201914181 |
| Omar Christian Berrocal Ramirez  | U20201B529 |
|  Deybbi Anderson Crisanto Calle  | U202120569 |
|   July Zelmira Paico Calderon    | U20211D760 |
|     Jean Pierr Aldave Aldave     | U202120005 |

---
xuxa
