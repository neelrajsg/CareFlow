# CareFlow

**FHIR-Based Clinical Integration and Patient Event Platform**

CareFlow is an open-source healthcare integration platform built with Java and Spring Boot for managing clinical data, patient events, healthcare interoperability, and event-driven workflows.

The project explores how production healthcare systems can integrate data across EHR, laboratory, radiology, pharmacy, medical imaging, and patient monitoring systems while maintaining a consistent clinical data model and API layer.

CareFlow is being developed as a production-oriented system rather than a traditional CRUD-based hospital management application.

> Status: Active Development — Phase 1

---

## Overview

Modern healthcare environments rarely operate through a single application. Patient information is distributed across multiple systems:

- Electronic Health Record (EHR)
- Laboratory Information System (LIS)
- Radiology Information System (RIS)
- PACS and medical imaging systems
- Pharmacy systems
- Patient monitoring platforms
- External clinical applications

CareFlow aims to provide a common integration layer between these systems.

```text
                    External Healthcare Systems

          EHR        LIS        RIS/PACS       Pharmacy
           |          |            |              |
           +----------+------------+--------------+
                              |
                              v
                    +-------------------+
                    |     CareFlow      |
                    | Integration Layer |
                    +-------------------+
                              |
             +----------------+----------------+
             |                |                |
             v                v                v
         FHIR APIs       Clinical Events   Rules Engine
             |                |                |
             +----------------+----------------+
                              |
                              v
                     Patient Timeline
                              |
                              v
                    Clinical Applications
```

The long-term goal is to support standards-based healthcare interoperability while providing scalable backend infrastructure for clinical applications.

---

## Key Engineering Areas

CareFlow focuses on several areas commonly encountered in healthcare platform engineering:

- FHIR R4 interoperability
- Clinical domain modeling
- REST API design
- Patient clinical timelines
- Event-driven architecture
- Authentication and role-based authorization
- Clinical alert processing
- Audit trails
- Medical imaging metadata integration
- Database performance
- Distributed caching
- Observability
- Containerized deployment
- CI/CD

---

## Technology Stack

### Current

| Area | Technology |
|---|---|
| Language | Java 21 LTS |
| Framework | Spring Boot 3.5.x |
| API | Spring Web / REST |
| Persistence | Spring Data JPA |
| ORM | Hibernate |
| Database | PostgreSQL |
| Database Migration | Flyway |
| Validation | Jakarta Bean Validation |
| Build | Maven |
| Version Control | Git |

### Planned

| Area | Technology |
|---|---|
| Security | Spring Security, JWT, OAuth2 |
| Healthcare Standard | FHIR R4, HAPI FHIR |
| Messaging | Apache Kafka |
| Cache | Redis |
| Frontend | React, TypeScript |
| Testing | JUnit 5, Mockito, Testcontainers |
| Code Quality | SonarQube |
| Containers | Docker |
| Orchestration | Kubernetes |
| CI/CD | GitHub Actions |
| Metrics | Prometheus |
| Monitoring | Grafana |
| Tracing | OpenTelemetry |
| Cloud | AWS / Azure |

---

## Architecture

CareFlow currently follows a modular monolith architecture.

```text
                         Client
                           |
                     HTTP / JSON
                           |
                           v
                +----------------------+
                |     REST Layer       |
                |     Controllers      |
                +----------+-----------+
                           |
                           v
                +----------------------+
                |     DTO Layer        |
                | Validation / Mapping |
                +----------+-----------+
                           |
                           v
                +----------------------+
                |    Service Layer     |
                |   Business Logic     |
                +----------+-----------+
                           |
                           v
                +----------------------+
                |  Persistence Layer   |
                | Spring Data JPA      |
                +----------+-----------+
                           |
                           v
                +----------------------+
                |     PostgreSQL       |
                +----------------------+
```

A modular monolith is being used intentionally during the initial development stages.

Service boundaries and domain ownership will be established before introducing distributed infrastructure. Selected modules may later be extracted into independently deployable services where there is a clear architectural reason.

---

## Project Structure

CareFlow uses package-by-feature organization.

```text
src/main/java/com/careflow/

├── CareflowApplication.java
│
├── common/
│   ├── exception/
│   ├── response/
│   └── util/
│
├── organization/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── repository/
│   └── service/
│
├── practitioner/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── repository/
│   └── service/
│
└── patient/
    ├── controller/
    ├── dto/
    ├── entity/
    ├── mapper/
    ├── repository/
    └── service/
```

Each healthcare domain owns its controller, service, persistence, mapping, and API models.

This structure keeps domain boundaries explicit and provides a cleaner path toward future modularization.

---

## Current API Design

The API separates external contracts from persistence entities.

```text
HTTP Request
      |
      v
Controller
      |
      v
Request DTO
      |
      v
Validation
      |
      v
Service
      |
      v
Mapper
      |
      v
JPA Entity
      |
      v
Repository
      |
      v
PostgreSQL
      |
      v
Response DTO
      |
      v
HTTP Response
```

JPA entities are not directly exposed through REST endpoints.

---

## Organization API

Current base endpoint:

```text
/api/v1/organizations
```

### Create Organization

```http
POST /api/v1/organizations
```

Example request:

```json
{
  "organizationCode": "HOSP-BLR-001",
  "name": "Example Medical Center",
  "type": "HOSPITAL",
  "phone": "9876543210",
  "email": "contact@example.com",
  "addressLine1": "Yelahanka",
  "addressLine2": "North Bengaluru",
  "city": "Bengaluru",
  "state": "Karnataka",
  "postalCode": "560064",
  "country": "India"
}
```

Response:

```text
201 Created
```

### Get Organization

```http
GET /api/v1/organizations/{id}
```

Example:

```http
GET /api/v1/organizations/1
```

Responses:

```text
200 OK
404 Not Found
```

---

## API Error Handling

CareFlow provides centralized API exception handling using Spring `@RestControllerAdvice`.

Current error handling includes:

| Condition | HTTP Status |
|---|---|
| Request validation failure | 400 Bad Request |
| Resource not found | 404 Not Found |
| Duplicate resource | 409 Conflict |

Example:

```json
{
  "timestamp": "2026-09-19T19:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Organization not found with id: 100",
  "path": "/api/v1/organizations/100"
}
```

Validation errors additionally provide field-level information.

---

## Database Strategy

PostgreSQL is the primary relational database.

Database schema evolution is managed using Flyway migrations.

```text
src/main/resources/db/migration/

V1__create_organizations_table.sql
```

Hibernate is configured with:

```yaml
ddl-auto: validate
```

Therefore:

```text
Flyway    -> Database schema ownership
Hibernate -> Entity/schema validation
```

Application code does not rely on Hibernate to automatically modify the database schema.

Database-level constraints are retained even when equivalent validation exists at the application layer.

For example, organization codes are protected through both application validation and a database unique constraint.

---

## Healthcare Domain Model

The platform is being developed around healthcare domains.

```text
Organization
     |
     +---- Practitioner
     |
     +---- Patient
              |
              +---- Encounter
                       |
                       +---- Observation
                       |
                       +---- Condition
                       |
                       +---- Medication
                       |
                       +---- Diagnostic Report
                       |
                       +---- Imaging Study
```

The domain model will evolve as FHIR interoperability is introduced.

---

## FHIR Interoperability

FHIR R4 support is a major planned capability of CareFlow.

Initial resources will include:

```text
Patient
Practitioner
Organization
Encounter
Observation
Condition
DiagnosticReport
MedicationRequest
```

HAPI FHIR is planned for FHIR resource processing.

The architecture will maintain separation between internal domain models and external FHIR representations where appropriate.

Conceptually:

```text
Internal Clinical Model
          |
          v
     FHIR Mapping
          |
          v
     FHIR R4 API
          |
          v
External Healthcare Systems
```

---

## Event-Driven Architecture

Clinical events will eventually be published through Apache Kafka.

Example events:

```text
PatientRegistered
EncounterStarted
ObservationRecorded
LabResultAvailable
ImagingStudyAvailable
ClinicalAlertTriggered
```

Target architecture:

```text
                     Clinical Services
                            |
                            v
                     Apache Kafka
                            |
             +--------------+--------------+
             |              |              |
             v              v              v
       Alert Engine      Audit        Notification
```

The implementation will cover:

- Event producers and consumers
- Consumer groups
- Partitioning
- Event schemas
- Idempotency
- Retry strategies
- Dead-letter queues
- Eventual consistency

---

## Clinical Rules and Alerts

CareFlow will introduce a clinical rules layer for evaluating incoming patient observations and events.

```text
Clinical Event
      |
      v
Rules Evaluation
      |
  +---+---+
  |       |
Normal  Abnormal
          |
          v
     Clinical Alert
          |
          v
   Event Publication
```

Rules will initially remain deterministic and configurable.

The objective is to build a reliable clinical event-processing foundation before considering more advanced intelligence layers.

---

## Medical Imaging Integration

Medical imaging integration is planned at the metadata and workflow level.

Initial concepts include:

- DICOM
- PACS
- StudyInstanceUID
- SeriesInstanceUID
- Accession Number
- Modality
- Study metadata
- Imaging workflow events

CareFlow is not intended to replace a PACS. Instead, imaging metadata will be linked to the broader patient clinical timeline.

---

## Security

Planned security architecture includes:

```text
Spring Security
       |
       +---- Authentication
       |
       +---- JWT Access Tokens
       |
       +---- Refresh Tokens
       |
       +---- Role-Based Access Control
       |
       +---- Resource Authorization
```

Initial application roles are expected to include:

```text
ADMIN
DOCTOR
NURSE
LAB_TECHNICIAN
RADIOLOGIST
PATIENT
```

Security implementation will include both endpoint-level authorization and resource-level ownership checks.

---

## Testing Strategy

The project will use multiple levels of automated testing.

```text
Unit Tests
     |
Repository Tests
     |
Service Tests
     |
Controller Tests
     |
Integration Tests
     |
Container-Based Database Tests
     |
Performance Tests
```

Planned tools include:

- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers
- PostgreSQL

Later development phases will introduce SonarQube quality gates.

Initial quality targets:

```text
Coverage          >= 80%
Code Duplication  < 3%
Blocker Issues    0
Critical Issues   0
```

---

## Performance Engineering

CareFlow will include explicit performance testing rather than relying only on functional correctness.

Planned workload levels include:

```text
100 concurrent users
500 concurrent users
1,000 concurrent users
5,000 concurrent users
```

Metrics will include:

- p50 latency
- p95 latency
- p99 latency
- Throughput
- Error rate
- Database connection utilization
- CPU utilization
- Memory utilization

Optimization work will include database indexing, query analysis, connection pooling, pagination, N+1 detection, and caching.

---

## Observability

The planned observability stack includes:

```text
Spring Boot Actuator
Prometheus
Grafana
Structured Application Logs
OpenTelemetry
Distributed Tracing
Correlation IDs
```

The objective is to make system behavior measurable across HTTP requests, database operations, and eventually asynchronous events.

---

## Deployment Architecture

The deployment roadmap includes Docker, automated CI/CD, Kubernetes, and cloud infrastructure.

```text
Developer
    |
    v
GitHub
    |
    v
GitHub Actions
    |
    +---- Build
    +---- Unit Tests
    +---- Integration Tests
    +---- Quality Analysis
    +---- Security Checks
    |
    v
Docker Image
    |
    v
Container Registry
    |
    v
Kubernetes
    |
    v
Cloud Infrastructure
```

---

## Development Roadmap

| Phase | Scope | Status |
|---|---|---|
| 1 | Spring Boot Foundation | In Progress |
| 2 | Authentication, JWT and RBAC | Planned |
| 3 | Clinical Domain and Patient Timeline | Planned |
| 4 | FHIR R4 Interoperability | Planned |
| 5 | Automated Testing and SonarQube | Planned |
| 6 | Kafka Event-Driven Architecture | Planned |
| 7 | Clinical Rules and Alert Engine | Planned |
| 8 | Redis, Performance and Scalability | Planned |
| 9 | Medical Imaging Integration | Planned |
| 10 | Microservices Evolution | Planned |
| 11 | React Clinical Dashboard | Planned |
| 12 | Docker, CI/CD and Cloud | Planned |
| 13 | Observability and Reliability | Planned |
| 14 | Architecture and Engineering Documentation | Planned |

---

## Current Progress

### Implemented

- Java 21 application foundation
- Spring Boot 3.5.x
- PostgreSQL integration
- Environment-specific configuration
- Flyway schema migrations
- Organization persistence model
- Repository layer
- Request and response DTO separation
- Bean Validation
- DTO/entity mapping
- Service layer
- Transaction management
- Duplicate organization detection
- Centralized exception handling
- Standard API error responses
- Organization creation endpoint
- Organization lookup by ID
- HTTP 400 validation responses
- HTTP 404 resource handling
- HTTP 409 conflict handling

### In Development

- Organization listing
- Pagination and sorting
- Organization update
- Organization deactivation
- OpenAPI/Swagger documentation

### Planned Next

- Practitioner domain
- Patient domain
- Automated tests
- Security foundation

---

## Running Locally

### Prerequisites

- Java 21+
- PostgreSQL
- Git

Clone the repository:

```bash
git clone <repository-url>
cd careflow
```

Create the database:

```sql
CREATE DATABASE careflow_db;
```

Configure the required database environment variables.

Do not store database passwords directly in source-controlled configuration.

On Windows:

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```

On Linux/macOS:

```bash
./mvnw clean verify
./mvnw spring-boot:run
```

The API is available by default at:

```text
http://localhost:8080
```

Flyway validates and applies pending migrations during application startup.

---

## Engineering Principles

CareFlow follows several architectural principles:

1. Establish clear domain boundaries before introducing microservices.
2. Keep controllers focused on HTTP concerns.
3. Keep business rules inside the service/domain layer.
4. Separate API contracts from persistence entities.
5. Version database changes explicitly.
6. Enforce critical data integrity rules at the database level.
7. Prefer healthcare standards over proprietary representations where appropriate.
8. Measure performance before optimizing.
9. Introduce distributed infrastructure only when justified.
10. Treat testing, security, observability, and documentation as core engineering concerns.

---

## Why CareFlow?

CareFlow is intended to bridge the gap between a typical Spring Boot portfolio application and the engineering problems encountered in real healthcare platforms.

Instead of stopping at REST CRUD operations, the project progressively introduces:

```text
REST APIs
   |
   v
Healthcare Domain Modeling
   |
   v
FHIR Interoperability
   |
   v
Security
   |
   v
Event-Driven Processing
   |
   v
Clinical Rules
   |
   v
Performance Engineering
   |
   v
Medical Imaging Integration
   |
   v
Observability
   |
   v
Cloud-Native Deployment
```

The repository is designed to evolve alongside the implementation, with architecture decisions, performance results, API documentation, and engineering trade-offs documented as the platform grows.

---

## Contributing

CareFlow is currently under active development.

Issues, technical discussions, architecture suggestions, healthcare interoperability improvements, and pull requests are welcome as the project matures.

Before contributing, please open an issue describing significant architectural changes or new modules.

---

## Disclaimer

CareFlow is an engineering and educational project.

It is not a certified medical device, clinical decision support system, or production healthcare product. It must not be used for diagnosis, treatment decisions, or real-world patient care without the required clinical validation, regulatory review, security controls, and organizational approvals.

---

## License

A license will be added as the project approaches its first public release.
