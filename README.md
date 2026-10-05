# Sales Management System - Unit Testing & CI

## 1. Project
This is a simulated SWP391 Sales Management System created for the Unit Testing and CI exercise.

## 2. Technology
- Java 17
- Maven
- JUnit 5
- Mockito
- JaCoCo
- GitHub Actions

## 3. Core features
1. Product Management
2. Order Management
3. User Authentication

## 4. Test scope
- Unit tests for ProductService
- Unit tests for OrderService
- Unit tests for AuthService
- Integration tests using the real in-memory repository
- 30 test methods in total
- Happy path, edge cases and error handling

## 5. Run locally
```bash
mvn clean verify
```

Coverage report:
```text
target/site/jacoco/index.html
```

The Maven build is configured to fail verification when line coverage is below 80%.

## 6. CI
GitHub Actions runs on push and pull request to `main` or `dev`.
It checks out the repository, installs JDK 17, executes `mvn verify`, and uploads the JaCoCo report.

## 7. AI prompts used
Prompt 0:
Guide me through setting up JUnit 5 and JaCoCo for a Java 17 Maven project with coverage reporting.

Prompt 1:
Analyze the ProductService, OrderService, and AuthService code and identify the core functions that need unit testing. Categorize them by happy path, edge cases, and error handling.

Prompt 2:
Generate comprehensive JUnit 5 test cases for the selected service methods. Include happy path, edge cases, invalid input, missing data, and dependency failures.

Prompt 3:
Create JUnit 5 and Mockito unit tests for these service methods. Include setup, teardown, mocks, and meaningful assertions.

Prompt 4:
Help debug any failing unit test and explain the root cause and fix.

Prompt 5:
Create mock objects for repository, password hashing, and token dependencies and update the unit tests accordingly.

Prompt 6:
Generate documentation for this test suite including how to run tests, coverage metrics, test descriptions, and CI workflow.
