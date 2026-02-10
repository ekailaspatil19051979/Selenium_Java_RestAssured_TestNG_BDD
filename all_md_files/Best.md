
# Best.md – Complete Framework Documentation

This document consolidates all requested information into one structured markdown file.

---

## 1. Architecture Overview

The automation framework follows a layered hybrid architecture combining UI and API automation.

### Layered Design

```
Feature Files → Step Definitions → Service Layer → Utilities → Base Layer → Selenium / RestAssured
```

### Key Components

- **BDD Layer:** Cucumber feature files and step definitions  
- **Execution Layer:** TestNG runner  
- **Service Layer:** API Request builders and validators  
- **UI Layer:** Page Object Model classes  
- **Utility Layer:** Common reusable helpers  
- **Context Layer:** ScenarioContext for data sharing  

This structure ensures separation of concerns, reusability, and maintainability.

---

## 2. API Automation Design

API automation is implemented using RestAssured with a reusable service layer.

### Core Classes

- RequestBuilder – central class for building API requests  
- ResponseValidator – common response validation logic  
- CustomLogFilter – API request/response logging  
- APIBase – base setup for API tests  

### Features

- JSON schema validation  
- Status code validation  
- Response time checks  
- Dynamic payload creation  
- Token handling  
- Chained API calls  

The API layer is completely abstracted so step definitions remain clean and readable.

---

## 3. UI Framework Design

UI automation follows Page Object Model.

### Structure

- BasePage – common Selenium actions  
- Page classes – LoginPage, HomePage, BookingPage  
- DriverFactory – browser management  
- WaitFactory – smart waits  

### Benefits

- Reusable locators and methods  
- Easy maintenance  
- Clear separation between test logic and UI actions  

Selenium interactions are never written directly in step definitions.

---

## 4. CI/CD Explanation

The framework is fully CI/CD ready.

### Integrations Provided

- Jenkinsfile for Jenkins pipeline  
- Azure pipeline YAML  
- Dockerfile and docker-compose  
- Maven execution support  

### Execution Flow

Code → Git → CI Pipeline → Maven Test → Reports → Artifacts

This allows scheduled, parallel, and containerized execution.

---

## 5. OOPs Concepts Used

### Encapsulation

- Private WebElements  
- Access through public methods  
- Utilities hide internal implementation  

### Abstraction

- API calls hidden behind RequestBuilder  
- Selenium actions hidden behind BasePage  

### Inheritance

- BaseTest and BasePage extended by child classes  

### Polymorphism

- Overloaded utility methods  
- Interface-based design  

These principles make the framework scalable and clean.

---

## 6. Dependency Injection and ScenarioContext

We use constructor-based dependency injection where Cucumber automatically provides a shared ScenarioContext object to all step definition classes, allowing us to pass data between steps without using static variables.

### How It Works

```
Cucumber creates ScenarioContext
        ↓
Injects same object into all step classes
        ↓
Steps store and retrieve data via context
```

This approach is thread-safe and parallel friendly.

---

## 7. Interview Q&A Based on This Framework

### Q: What type of framework have you implemented?

A: It is a hybrid automation framework supporting Selenium UI, RestAssured API, and Cucumber BDD with TestNG.

### Q: How do you share data between steps?

A: Using ScenarioContext with constructor-based dependency injection.

### Q: Which design patterns are used?

A: Page Object Model, Factory Pattern, Builder Pattern, and Singleton-like utilities.

### Q: How is data-driven testing handled?

A: Through CSV, JSON, Excel, and Cucumber Scenario Outline.

### Q: How is CI/CD achieved?

A: Via Jenkins, Azure pipelines, and Dockerized execution.

---

## 8. Improving Structure or Refactoring Suggestions

- Introduce interfaces for service layers  
- Add stronger type-safe context instead of key-value map  
- Move hardcoded strings to constants  
- Add better logging wrappers  
- Introduce parallel execution strategy at feature level  
- Add centralized exception handling  

---

## 9. Architecture Diagram (Text Representation)

```
[Features]
    ↓
[Step Definitions]
    ↓
[Service Layer]
    ↓
[Utilities / Base Classes]
    ↓
[Selenium / RestAssured]
```

---

## Final Summary

This repository implements a modern, enterprise-ready hybrid automation framework using Java, Selenium, RestAssured, Cucumber BDD, and TestNG. It follows clean architecture, strong OOPs principles, constructor-based dependency injection, and full CI/CD readiness with Docker and Jenkins.

---

End of Document
