# rra-ne
## Project Overview
A Spring Boot backend system for RRA to track vehicle ownership history, plate number assignments, and ownership transfers in Rwanda.

## System Requirements
1. Track vehicle ownership history
2. Manage vehicle registration details
3. Handle plate number assignments
4. Record ownership transfers
5. Provide search capabilities

## Technical Specifications
- **Backend Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **API Documentation**: Swagger UI
- **Authentication**: JWT
- **Pagination**: Spring Data JPA Pagination
- **Logging**: SLF4J with Logback
- **Validation**: Bean Validation 3.0

## Entity Relationship Diagram
[Vehicle] 1---* [OwnershipRecord] *---1 [Owner]
[Owner] 1---* [PlateNumber]
[PlateNumber] 1---1 [Vehicle] (current assignment)


## API Endpoints

### Authentication
| Endpoint | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/auth/signup` | POST | User registration | Public |
| `/api/auth/login` | POST | User login | Public |

### Owner Management
| Endpoint | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/owners` | POST | Register new owner | ADMIN |
| `/api/owners` | GET | List all owners (paginated) | ADMIN |
| `/api/owners/search` | GET | Search by National ID/Email/Phone | ADMIN |

### Vehicle Management
| Endpoint | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/vehicles` | POST | Register new vehicle | ADMIN |
| `/api/vehicles/{id}` | GET | Get vehicle details | ADMIN |
| `/api/vehicles/search` | GET | Search by chassis/plate/owner | ADMIN |

### Transfer Management
| Endpoint | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/transfers` | POST | Transfer vehicle ownership | ADMIN |
| `/api/vehicles/{id}/history` | GET | Get ownership history | ADMIN |

## Setup Instructions

### Prerequisites
- Java 17+
- PostgreSQL 14+
- Maven 3.9+
