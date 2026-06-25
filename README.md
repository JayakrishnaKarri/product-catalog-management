# Product Catalog Management API

## 🎯 Project Overview

This is a comprehensive **Product Catalog Management System** project. The project demonstrates all key concepts including REST controllers, validation, exception handling, and modern API design patterns.

### 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │───▶│     Service     │───▶│   Repository    │───▶│   In-Memory     │
│     Layer       │    │     Layer       │    │     Layer       │    │    Storage      │
└─────────────────┘    └─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │                       │
    REST Endpoints          Business Logic         Data Access           ConcurrentHashMap
    Bean Validation         DTO Conversion         Thread-Safe Ops       Auto-ID Generation
    Exception Handling      Duplicate Checks      Search & Filter       Atomic Operations
    CORS Support           Status Management       Sorting Methods
```

## 🚀 Features

### Core Functionality
- ✅ **CRUD Operations**: Create, Read, Update, Delete products
- ✅ **Search & Filter**: Name search, category filter, status filter, price range
- ✅ **Validation**: Comprehensive input validation with detailed error messages
- ✅ **Status Management**: Activate, deactivate, discontinue products
- ✅ **Stock Management**: Update inventory levels
- ✅ **Sorting**: Sort by name, price, creation date

### Concepts Demonstrated
- ✅ **Spring MVC**: [`@RestController`](src/main/java/com/example/productcatalog/controller/ProductController.java), [`@RequestMapping`](src/main/java/com/example/productcatalog/controller/ProductController.java)
- ✅ **Request Mapping**: [`@PathVariable`](src/main/java/com/example/productcatalog/controller/ProductController.java), [`@RequestParam`](src/main/java/com/example/productcatalog/controller/ProductController.java), [`@RequestBody`](src/main/java/com/example/productcatalog/controller/ProductController.java)
- ✅ **Bean Validation**: [`@Valid`](src/main/java/com/example/productcatalog/dto/CreateProductRequest.java), JSR-380 annotations
- ✅ **Exception Handling**: [`@ControllerAdvice`](src/main/java/com/example/productcatalog/exception/GlobalExceptionHandler.java), [`@ExceptionHandler`](src/main/java/com/example/productcatalog/exception/GlobalExceptionHandler.java)
- ✅ **Response Handling**: [`ResponseEntity`](src/main/java/com/example/productcatalog/controller/ProductController.java), ProblemDetails (RFC 7807)
- ✅ **CORS Support**: [`@CrossOrigin`](src/main/java/com/example/productcatalog/controller/ProductController.java)

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.5.x |
| **Web Layer** | Spring MVC | 6.2.x |
| **Validation** | Jakarta Bean Validation | 3.1 |
| **Documentation** | Documentation: springdoc-openapi (OpenAPI 3) | 2.8.x |
| **Build Tool** | Maven | 3.9.x |
| **Java Version** | Oracle JDK | 21 |
| **Storage** | In-Memory (ConcurrentHashMap) | - |

## 📋 API Endpoints

### Product Management

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/api/products` | Create new product | [`CreateProductRequest`](src/main/java/com/example/productcatalog/dto/CreateProductRequest.java) | [`ProductDTO`](src/main/java/com/example/productcatalog/dto/ProductDTO.java) |
| `GET` | `/api/products` | Get all products | None | `List<ProductDTO>` |
| `GET` | `/api/products/{id}` | Get product by ID | None | [`ProductDTO`](src/main/java/com/example/productcatalog/dto/ProductDTO.java) |
| `PUT` | `/api/products/{id}` | Update product | [`UpdateProductRequest`](src/main/java/com/example/productcatalog/dto/UpdateProductRequest.java) | [`ProductDTO`](src/main/java/com/example/productcatalog/dto/ProductDTO.java) |
| `DELETE` | `/api/products/{id}` | Delete product | None | `204 No Content` |

### Search & Filter

| Method | Endpoint | Description | Query Parameters |
|--------|----------|-------------|------------------|
| `GET` | `/api/products/search` | Search products | `name`, `category`, `status`, `minPrice`, `maxPrice` |
| `GET` | `/api/products/category/{category}` | Get by category | None |
| `GET` | `/api/products/status/{status}` | Get by status | None |

### Product Operations

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| `PATCH` | `/api/products/{id}/stock` | Update stock | `stock` (query param) |
| `PATCH` | `/api/products/{id}/activate` | Activate product | None |
| `PATCH` | `/api/products/{id}/deactivate` | Deactivate product | None |
| `PATCH` | `/api/products/{id}/discontinue` | Discontinue product | None |

### Utility Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products/count` | Get total product count |
| `GET` | `/api/products/{id}/exists` | Check if product exists |

## 📊 Data Models

### Product Entity
```java
Product {
    Long id;                    // Auto-generated ID
    String name;               // 2-100 chars, alphanumeric + spaces/hyphens
    String description;        // Optional, max 500 chars
    BigDecimal price;         // Positive, max 2 decimal places
    ProductCategory category; // Predefined enum values
    Integer stockQuantity;    // Non-negative integer
    ProductStatus status;     // ACTIVE, INACTIVE, DISCONTINUED
    LocalDateTime createdAt;  // Auto-set on creation
    LocalDateTime updatedAt;  // Auto-updated on changes
}
```

### Product Categories
```java
ELECTRONICS, CLOTHING, BOOKS, HOME_GARDEN, SPORTS_OUTDOORS,
TOYS_GAMES, HEALTH_BEAUTY, AUTOMOTIVE, FOOD_BEVERAGES, OFFICE_SUPPLIES
```

### Product Status
```java
ACTIVE      // Available for purchase
INACTIVE    // Temporarily unavailable
DISCONTINUED // Permanently discontinued
```

## 🔧 Setup & Installation

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd product-catalog-api
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - **Base URL**: http://localhost:8080
   - **Swagger UI**: http://localhost:8080/swagger-ui.html
   - **API Docs**: http://localhost:8080/api-docs
   - **Health Check**: http://localhost:8080/actuator/health

## 📝 Usage Examples

### Create a Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wireless Headphones",
    "description": "High-quality wireless headphones with noise cancellation",
    "price": 199.99,
    "category": "ELECTRONICS",
    "stockQuantity": 50
  }'
```

### Search Products
```bash
# Search by name
curl "http://localhost:8080/api/products/search?name=wireless"

# Filter by category and price range
curl "http://localhost:8080/api/products/search?category=ELECTRONICS&minPrice=100&maxPrice=300"

# Get products sorted by price
curl "http://localhost:8080/api/products?sortBy=price"
```

### Update Product Stock
```bash
curl -X PATCH "http://localhost:8080/api/products/1/stock?stock=75"
```

### Deactivate Product
```bash
curl -X PATCH http://localhost:8080/api/products/1/deactivate
```

## ✅ Validation Rules

### Product Name
- ✅ **Required**: Cannot be null or empty
- ✅ **Length**: 2-100 characters
- ✅ **Pattern**: Letters, numbers, spaces, hyphens, underscores only

### Price
- ✅ **Required**: Cannot be null
- ✅ **Positive**: Must be greater than 0
- ✅ **Precision**: Maximum 2 decimal places

### Category
- ✅ **Required**: Must be one of predefined categories
- ✅ **Enum Validation**: Invalid values return 400 Bad Request

### Stock Quantity
- ✅ **Required**: Cannot be null
- ✅ **Non-negative**: Must be >= 0

### Description
- ✅ **Optional**: Can be null or empty
- ✅ **Length**: Maximum 500 characters

## 🚨 Error Handling

The API uses **ProblemDetails (RFC 7807)** for standardized error responses:

### Validation Error (400 Bad Request)
```json
{
  "type": "https://api.productcatalog.com/errors/validation-failed",
  "title": "Validation Failed",
  "status": 400,
  "detail": "Validation failed for one or more fields",
  "instance": "/api/products",
  "timestamp": "2024-01-15T10:30:00",
  "errors": [
    {
      "field": "name",
      "rejectedValue": "A",
      "message": "Product name must be between 2 and 100 characters"
    }
  ]
}
```

### Product Not Found (404 Not Found)
```json
{
  "type": "https://api.productcatalog.com/errors/product-not-found",
  "title": "Product Not Found",
  "status": 404,
  "detail": "Product not found with id: 999",
  "instance": "/api/products/999",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Duplicate Name (409 Conflict)
```json
{
  "type": "https://api.productcatalog.com/errors/duplicate-product-name",
  "title": "Duplicate Product Name",
  "status": 409,
  "detail": "Product with name 'Wireless Headphones' already exists",
  "instance": "/api/products",
  "timestamp": "2024-01-15T10:30:00"
}
```

## 📊 Monitoring & Observability

### Actuator Endpoints
- **Health**: `/actuator/health` - Application health status
- **Info**: `/actuator/info` - Application information
- **Metrics**: `/actuator/metrics` - Application metrics

### Logging
- **Level**: DEBUG for application, INFO for Spring
- **Format**: Structured logging with timestamps
- **File**: `logs/product-catalog-api.log`

## 🔒 Security Considerations

### CORS Configuration
- **Allowed Origins**: `*` (configure for production)
- **Allowed Methods**: `GET, POST, PUT, DELETE, PATCH, OPTIONS`
- **Max Age**: 3600 seconds

### Input Validation
- **Server-side validation**: All inputs validated with Bean Validation
- **SQL Injection**: Not applicable (in-memory storage)
- **XSS Prevention**: JSON responses, no HTML rendering

## 🚀 Production Readiness

### Configuration Management
- ✅ **Externalized Config**: `application.properties` with environment-specific profiles
- ✅ **Graceful Shutdown**: Completes in-flight requests before stopping
- ✅ **Health Checks**: Actuator health endpoints for load balancers

### Performance Considerations
- ✅ **Thread Safety**: `ConcurrentHashMap` for concurrent access
- ✅ **Memory Efficiency**: In-memory storage with cleanup capabilities
- ✅ **Response Optimization**: DTO pattern prevents over-fetching

### Deployment
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/product-catalog-api-1.0.0.jar

# Docker (if Dockerfile added)
docker build -t product-catalog-api .
docker run -p 8080:8080 product-catalog-api
```

## 📚 Learning Outcomes

This project demonstrates mastery of **Spring MVC & Web Layer** concepts:

### Learning Objectives
1. **MVC Architecture**: Understanding of DispatcherServlet, HandlerMapping, ViewResolver
2. **REST Controllers**: Building RESTful APIs with proper HTTP methods and status codes
3. **Request Mapping**: Handling path variables, request parameters, and request bodies
4. **Bean Validation**: Implementing comprehensive input validation with custom messages
5. **Exception Handling**: Global exception handling with standardized error responses
6. **Response Handling**: Using ResponseEntity for typed responses and proper status codes
7. **CORS Configuration**: Enabling cross-origin requests for frontend integration

## 📞 Support

For questions or issues related to this  mini project:

- **Documentation**: Check this README and inline code comments
- **API Documentation**: Visit `/swagger-ui.html` when running
- **Logs**: Check `logs/product-catalog-api.log` for debugging

---

**🎉 Congratulations!** You have successfully completed the **Spring MVC & Web Layer** mini project. This implementation demonstrates all key concepts and prepares you for the next phase of the Spring Boot learning journey.
