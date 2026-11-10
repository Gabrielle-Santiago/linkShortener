# Link Shortener

A URL shortening service built with **Java 21, Spring Boot, and MongoDB**.

The application receives long URLs and generates shorter, unique links that redirect users to their original destinations. Shortened URLs are stored in MongoDB and remain available for **30 days** before expiration.

## Technologies

- Java 21
- Spring Boot
- Spring Data MongoDB
- MongoDB Atlas
- Maven
- JUnit 5
- Mockito

## Features

### URL Validation
Before shortening a URL, the application performs the following validations:

- Ensures the URL is not null or empty.
- Checks whether the URL has a valid format.
- Accepts only HTTP and HTTPS protocols.
- Checks whether the URL has already been shortened to prevent duplicate entries.

### Short URL Generation
- Generates a unique 7-character alphanumeric code.
- Uses uppercase letters, lowercase letters, and numbers.
- Associates the generated code with the original URL.

### URL Redirection
- Redirects users to the original destination when accessing a shortened URL through a GET request.

### Data Persistence and Expiration
- Stores URL records in MongoDB.
- Records the creation and expiration timestamps.
- Sets a 30-day expiration period for each shortened URL.

### Testing
- Unit tests for short URL generation.
- Tests for URL shortening and data persistence.
- Tests for URL retrieval and not-found scenarios.

## API Endpoints

### 1. Shorten a URL

**POST** `/url`

Receives a long URL and returns its shortened version.

**Request:**

```json
{
  "fullUrl": "https://www.example.com/some-path"
}
```

**Response — 200 OK:**

```json
{
  "shortUrl": "fIqG56r"
}
```

### 2. Redirect to the Original URL

**GET** `/url/{shortUrl}`

Receives a short code and redirects the user to the original URL.

**Example:**

```http
GET /url/fIqG56r
```

**Response:**

```http
HTTP/1.1 302 Found
Location: https://www.example.com/some-path
```

## Getting Started

### Prerequisites

- Java 21 or later.
- Maven.
- A MongoDB instance or MongoDB Atlas connection.

### Configuration

Configure your MongoDB connection in the application properties:

```properties
spring.mongodb.uri=${MONGODB_URI}
```

Set the `MONGODB_URI` environment variable with your MongoDB connection string.
