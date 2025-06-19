# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

This is a Maven multi-module Spring Boot starter project. Use these commands:

```bash
# Build the entire project
mvn clean compile

# Package all modules
mvn clean package

# Install to local Maven repository
mvn clean install

# Deploy to GitHub Packages (requires authentication)
mvn clean deploy
```

## Project Architecture

This is a Spring Boot starter library that provides HTTP request logging functionality through auto-configuration:

### Multi-Module Structure
- **Parent POM** (`pom.xml`): Defines Spring Boot 3.2.0 dependency management and Java 17
- **Autoconfigure Module** (`http-request-logger-spring-boot-autoconfigure/`): Contains the core functionality
- **Starter Module** (`http-request-logger-spring-boot-starter/`): Provides the dependency aggregation

### Core Components

**HttpRequestLoggerAutoConfiguration** (`http-request-logger-spring-boot-autoconfigure/src/main/java/.../HttpRequestLoggerAutoConfiguration.java:16-46`): 
- Spring Boot auto-configuration class
- Conditionally registers the logging filter based on web application presence and property settings
- Enabled by default unless `http.request.logger.enabled=false`

**HttpRequestLoggingFilter** (`http-request-logger-spring-boot-autoconfigure/src/main/java/.../HttpRequestLoggingFilter.java:18-140`):
- Servlet filter that intercepts HTTP requests/responses
- Uses Spring's `ContentCachingRequestWrapper` and `ContentCachingResponseWrapper`
- Supports URL pattern exclusion via Ant-style matching
- Contains ASCII art feature for successful GET requests

**HttpRequestLoggerProperties** (`http-request-logger-spring-boot-autoconfigure/src/main/java/.../HttpRequestLoggerProperties.java:11-119`):
- Configuration properties class with prefix `http.request.logger`
- Controls logging behavior: headers, payloads, response bodies, log levels, ASCII art
- Supports exclude patterns and payload length limits

### Key Configuration Properties
- `http.request.logger.enabled`: Enable/disable logging (default: true)
- `http.request.logger.include-headers/payload/response`: Control what gets logged
- `http.request.logger.exclude-patterns`: URL patterns to skip
- `http.request.logger.log-level`: INFO/DEBUG/TRACE levels
- `http.request.logger.show-ascii-art`: ASCII art for successful GET requests

### Auto-Configuration Integration
- Uses `META-INF/spring.factories` and `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- Provides `META-INF/additional-spring-configuration-metadata.json` for IDE support

### Current Version
The project is at version 1.0.4 with GitHub Packages distribution configured.