
# Automation Framework – Detailed Implementation Analysis

This document explains in simple and clear terms what has been implemented in the uploaded repository. The explanation is written in interview-ready format and describes the architecture, tools, patterns, and concepts used.

---

## 1. Overall Framework Type

This repository implements a **Hybrid Automation Framework** that supports:

- Selenium UI Automation  
- RestAssured API Automation  
- Cucumber BDD  
- TestNG  
- Maven  
- Allure and Extent Reporting  
- Data Driven Testing (Excel, CSV, JSON)  
- CI/CD integration (Jenkins, Azure, Docker)

The framework supports both:

👉 UI Automation + API Automation in a single unified structure

---

## 2. Core Technologies Used

| Layer | Technology |
|-----|-----------|
| Language | Java |
| UI Automation | Selenium WebDriver |
| API Automation | RestAssured |
| BDD | Cucumber |
| Test Runner | TestNG |
| Build Tool | Maven |
| Reporting | Allure, Extent Reports |
| Logging | Log4j2 |
| CI/CD | Jenkins, Azure Pipelines |
| Containerization | Docker |
| Data Driven | CSV, JSON, Excel (Apache POI) |
| Utilities | Faker, DB, Accessibility, Visual testing |

---

## 3. Framework Architecture

The project follows a clean layered architecture:

```
features  → step definitions → service layer → utilities → base layer → selenium/restassured
```

Main packages:

- api – API request builders and validators  
- base – Base classes for tests and pages  
- pages – Page Object Model classes  
- utils – Common reusable utilities  
- context – ScenarioContext for DI  
- stepdefinitions – Cucumber step classes  
- runners – Cucumber TestNG runner  

This clearly indicates a well-structured enterprise automation framework.

---

## 4. Dependency Injection Implementation

The framework uses **Constructor-Based Dependency Injection**.

Example from the repository:

```java
public class AdvancedStepDefs {

    private final ScenarioContext context;

    public AdvancedStepDefs(ScenarioContext context) {
        this.context = context;
    }
}
```

### What This Means

We use constructor-based dependency injection where Cucumber automatically provides a shared ScenarioContext object to all step definition classes, allowing us to pass data between steps without using static variables.

### Benefits

- Loose coupling  
- Parallel execution safe  
- No static variables  
- Clean sharing of test data  
- Better maintainability  

This confirms that PicoContainer is NOT used. Instead, modern native Cucumber DI is implemented.

---

## 5. ScenarioContext – Shared Data Container

`ScenarioContext.java` is used as a central object to store:

- API responses  
- Tokens  
- Dynamic data  
- IDs generated during execution  

This allows steps from different classes to communicate easily.

---

## 6. Design Patterns Used

The following design patterns are clearly implemented:

### a) Page Object Model (POM)

All UI pages are implemented under:

```
src/main/java/pages
```

Examples:

- LoginPage.java  
- HomePage.java  
- BookingPage.java  

UI actions are encapsulated inside page classes.

---

### b) Factory Pattern

`DriverFactory.java` handles WebDriver creation dynamically.

---

### c) Builder Pattern

`RequestBuilder.java` is used to build API requests in a reusable manner.

---

### d) Singleton / Centralized Objects

ConfigReader and ScenarioContext act as shared single objects.

---

## 7. OOPs Concepts Used

### Encapsulation

- Private WebElements in page classes  
- Data hidden inside utility classes  
- Access only through public methods  

### Abstraction

- API interactions hidden inside RequestBuilder  
- Selenium actions abstracted via BasePage  

### Inheritance

- BaseTest  
- BasePage  
- APIBase  

### Polymorphism

- Overloaded methods in utilities  
- Interface-based design  

---

## 8. API Automation Layer

Under:

```
src/main/java/api
```

We have:

- RequestBuilder.java – builds API requests  
- ResponseValidator.java – validates responses  
- CustomLogFilter.java – logging for APIs  

This shows:

- Clean separation of API logic  
- Reusable API framework  
- Centralized validations  

---

## 9. Data Driven Testing

Multiple data driven techniques are implemented:

### Sources:

- CSV files  
- JSON files  
- Excel files  
- Feature file Examples tables  

Utilities used:

- CSVReaderUtil  
- JSONReader  
- ExcelReader  

This enables:

- Scenario Outline  
- Externalized test data  
- Large scale test coverage  

---

## 10. Cucumber BDD Implementation

Feature files located at:

```
src/test/resources/features
```

Step Definitions at:

```
src/test/java/stepdefinitions
```

Runner:

```
TestRunner.java
```

Tags, hooks, and reporting are configured.

---

## 11. Test Execution Layer

- TestNG used as execution engine  
- Parallel execution supported  
- Retry mechanism implemented  
- Hooks for setup and teardown  

---

## 12. Utilities Implemented

Rich utility layer includes:

- WaitFactory – smart waits  
- FakerUtil – dynamic test data  
- DBUtil – database testing  
- VisualUtil – visual testing  
- ZapUtil – security scanning  
- AccessibilityUtil – accessibility checks  

This indicates an advanced enterprise-grade framework.

---

## 13. Reporting

Two reporting mechanisms:

- Allure Reports  
- Extent Reports  

Configured through:

- allure.properties  
- extent-config.xml  

---

## 14. CI/CD Integration

The repository contains:

- Jenkinsfile  
- Azure pipeline yaml  
- Dockerfile  
- docker-compose.yml  

This proves:

👉 Framework is ready for enterprise CI/CD pipelines.

---

# FINAL SUMMARY (Interview Style)

This repository implements a modern hybrid automation framework using Selenium, RestAssured, Cucumber BDD, and TestNG. It follows Page Object Model design, uses constructor-based dependency injection with ScenarioContext for data sharing, and applies OOPs principles like Encapsulation, Abstraction, Inheritance, and Polymorphism. The framework supports data-driven testing via CSV, JSON, and Excel, includes robust utility layers, and integrates with Jenkins, Azure DevOps, and Docker for CI/CD execution. Reporting is implemented using Allure and Extent, making it a scalable enterprise-ready automation solution.

---

### End of Analysis
