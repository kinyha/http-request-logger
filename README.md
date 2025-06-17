# HTTP Request Logger Spring Boot Starter

A simple and configurable Spring Boot starter for logging HTTP requests and responses.

## Features

- 🚀 Zero configuration required - works out of the box
- 📝 Logs HTTP method, URL, status code, and execution time
- 🔧 Highly configurable via properties
- 📦 Supports request/response body logging
- 📋 Can include/exclude headers
- 🎯 URL pattern exclusion support
- 🎨 Fun ASCII art for successful GET requests
- 📊 Configurable log levels (INFO, DEBUG, TRACE)

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.kinyha</groupId>
    <artifactId>http-request-logger-spring-boot-starter</artifactId>
    <version>1.0.3</version>
</dependency>
```

## Configuration

All configurations are optional. The logger works with default settings.

```properties
# Enable/disable the logger (default: true)
http.request.logger.enabled=true

# Include request headers in logs (default: false)
http.request.logger.include-headers=false

# Include request body in logs (default: false)
http.request.logger.include-payload=false

# Include response body in logs (default: false)
http.request.logger.include-response=false

# Maximum payload length to log (default: 1000)
http.request.logger.max-payload-length=1000

# URL patterns to exclude from logging
http.request.logger.exclude-patterns=/health,/metrics,/swagger-ui/**

# Log level: INFO, DEBUG, TRACE (default: INFO)
http.request.logger.log-level=INFO

# Show ASCII art for successful GET requests (default: true)
http.request.logger.show-ascii-art=true
```

## Usage Examples

### Basic Usage

Just add the dependency - no additional configuration needed!

```
2024-01-15 10:23:45 INFO  HTTP Request: GET /api/users | Status: 200 | Duration: 45ms
2024-01-15 10:23:46 INFO  HTTP Request: POST /api/users | Status: 201 | Duration: 123ms
```

### With Headers and Payload

```properties
http.request.logger.include-headers=true
http.request.logger.include-payload=true
```

Output:
```
HTTP Request: POST /api/users | Status: 201 | Duration: 78ms | Headers: {Content-Type: application/json, Authorization: Bearer ***} | Request Body: {"name":"John","email":"john@example.com"}
```

### Excluding Patterns

```properties
http.request.logger.exclude-patterns=/actuator/**,/health,/metrics
```

### Different Log Levels

```properties
# For development - more detailed logs
http.request.logger.log-level=DEBUG
http.request.logger.include-payload=true
http.request.logger.include-response=true

# For production - minimal logs
http.request.logger.log-level=INFO
http.request.logger.include-payload=false
```

## How It Works

The starter automatically registers a servlet filter that intercepts all HTTP requests and logs them according to your configuration. It uses Spring's `ContentCachingRequestWrapper` and `ContentCachingResponseWrapper` to safely read request and response bodies without affecting your application.

## Requirements

- Java 17 or higher
- Spring Boot 3.0.0 or higher
- Spring Web (automatically included)

## License

MIT License

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

**Vladislav Bratchikov**
- GitHub: [@kinyha](https://github.com/kinyha)
- Email: kinyhaha@gmail.com