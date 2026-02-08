# 🏗️ Enterprise Hybrid Automation Framework: Comprehensive Architecture Guide

## 1. Project Overview
This project is an **Enterprise-Grade Hybrid Automation Framework** designed to provide a unified platform for **UI (Selenium)**, **API (RestAssured)**, and **Database (JDBC)** testing. Built on **Java 17**, it utilizes a **BDD (Behavior Driven Development)** approach via **Cucumber** and **TestNG** to bridge the gap between technical execution and business requirements. The framework is engineered for scalability, parallel execution, and seamless integration into modern **DevOps pipelines (Jenkins/Azure)**, ensuring high-quality software delivery through continuous testing.

---

## 2. High Level Architecture
The framework is built on a **Modular Layered Architecture** to ensure the separation of concerns, high maintainability, and reusability.

*   **Execution Layer**: Orchestrated by Maven and TestNG. It manages threads, listeners, and the entry point for the test suite.
*   **Logic Layer (BDD)**: Converts Gherkin features into executable code through Step Definitions and Hooks.
*   **Implementation Layer**:
    *   **UI Layer**: Uses Page Object Model (POM) to store locators and actions.
    *   **API Layer**: Uses a service-oriented approach with request builders and response validators.
    *   **DB Layer**: Uses JDBC utilities for direct platform-level assertions.
*   **Utility & Core Layer**: Provides foundational services like Driver Management, Logging, Wait Strategies, and Data Parsing.
*   **Reporting Layer**: Aggregates execution metadata into interactive Allure and Extent reports.

---

## 3. Folder Structure Explanation

| Folder Path | Purpose & Responsibility |
| :--- | :--- |
| **`src/main/java/base`** | **Core Registry**: Houses the `DriverFactory` which manages the lifecycle of WebDrivers using `ThreadLocal`. |
| **`src/main/java/pages`** | **UI Repository**: Contains Page Object classes where each class represents a web page with its specific locators and methods. |
| **`src/main/java/api`** | **Service Layer**: Contains `RequestBuilder` for RestAssured wrappers and `ResponseValidator` for enterprise assertions. |
| **`src/main/java/utils`** | **Engine Room**: Reusable technical helpers like `DBUtil` (JDBC), `WaitFactory` (Sync), and `RetryAnalyzer`. |
| **`src/main/java/models`** | **Data Blueprints**: Lombok-driven POJOs (Plain Old Java Objects) used for type-safe API request/reponse handling. |
| **`src/test/java/runners`** | **Command Center**: Entry points for Cucumber tests using TestNG annotations. |
| **`src/test/java/stepdefinitions`**| **Glue Code**: Maps Gherkin scenarios to the underlying Java implementation logic. |
| **`src/test/resources/features`** | **Business Requirements**: Gherkin files describing test scenarios in plain English. |
| **`target/allure-results`** | **Reporting Sink**: Raw metadata generated during execution used to build final reports. |

---

## 4. Important Files Explanation

*   **`pom.xml`**: 
    *   **Role**: Dependency and Build Manager. 
    *   **Execution**: Read by Maven at the start. 
    *   **Output**: Downloads libraries and configures build plugins.
*   **`testng.xml`**:
    *   **Role**: Suite Orchestrator. 
    *   **Execution**: Called by Maven Surefire. 
    *   **Output**: Controls parallel thread count and registers global listeners.
*   **`config.properties`**:
    *   **Role**: Global Configuration. 
    *   **Execution**: Loaded by `ConfigReader` utility at runtime. 
    *   **Output**: Provides URLs, browser types, and environment settings.
*   **`TestRunner.java`**:
    *   **Role**: Cucumber Entry Point. 
    *   **Execution**: TestNG invokes this class. 
    *   **Output**: Links features to step definitions and generates reports.
*   **`Hooks.java`**:
    *   **Role**: Lifecycle Interceptor. 
    *   **Execution**: Automatically triggered before/after every scenario. 
    *   **Output**: Initializes drivers and captures screenshots on failure.

---

## 5. Detailed Execution Flow (Step-by-Step)

1.  **Start**: User runs `mvn test` in CLI or CI.
2.  **Config Load**: `testng.xml` is identified, which triggers the `AnnotationTransformer`.
3.  **Runner Activation**: `TestRunner.java` starts scanning the `features/` directory.
4.  **Scenario Setup**: The `@Before` hook in `Hooks.java` calls `DriverFactory.initDriver()`.
5.  **Logic Execution**: 
    *   A step in the feature file is mapped to a **Step Definition**.
    *   The Step Definition calls a **Page Object** (UI) or a **RequestBuilder** (API).
    *   **WaitFactory** methods are invoked internally for UI stabilization.
    *   **Lombok Models** are used to construct API JSON payloads.
6.  **Validation**: **ResponseValidator** (for API) or TestNG **Assert** (for UI/DB) checks actual vs expected.
7.  **Teardown**: The `@After` hook in `Hooks.java` captures screenshots if a failure occurs and calls `DriverFactory.quitDriver()`.
8.  **Completion**: Allure listener compiles the results.

---

## 6. Data Flow Explanation

*   **Storage**: Data resides in `config.properties` (global), JSON/Excel files (bulk), or is generated dynamically.
*   **Reading**: `ConfigReader` and `FakerUtil` pull data into the Step Definition layer.
*   **Movement**: Data is passed as parameters from the Feature file -> Step Def -> Page Object/RequestBuilder.
*   **Validation**: Validation logic compares runtime response data against the original input data using POJO-to-JSON comparisons.

---

## 7. Integration Flow

*   **API**: Integrated via **RestAssured**, allowing tests to initialize application state before UI tests.
*   **Database**: Integrated via **JDBC**, used to verify that UI/API actions are persisted in the DB correctly.
*   **Security**: Proxy-integrated with **OWASP ZAP** for passive vulnerability scanning.
*   **Infrastructure**: Integrated with **Docker Compose** for Selenium Grid and **BrowserStack** for cloud-based device testing.

---

## 8. Configuration Management
Environment-specific execution is handled via **Maven Profiles** and the `config.properties` file. By passing `-Denv=qa` or `-Dbrowser=firefox`, the framework dynamically selects the correct URL and browser driver at runtime, allowing the same suite to run across Dev, QA, and Staging environments.

---

## 9. Execution Modes
1.  **Local IDE**: Running `TestRunner.java` directly for development.
2.  **CLI**: `mvn clean test` for local command-line validation.
3.  **CI/CD**: Continuous execution triggered by Git webhooks in **Jenkins Pipelines**.
4.  **Containerized**: Using `docker-compose up` to run tests inside isolated Linux containers with a remote Selenium Grid.

---

## 10. Reporting / Output Flow
1.  **Metadata Generation**: During execution, TestNG and Cucumber listeners save JSON/XML metadata to `target/`.
2.  **Artifact Capture**: Screenshots (Base64) and Traces are attached to the metadata object on scenario failure.
3.  **Aggregation**: After completion, the **Allure Maven Plugin** aggregates these into an interactive HTML dashboard.
4.  **Distribution**: The dashboard is hosted on Jenkins or published as an Azure Build Artifact.

---

## 11. End-to-End Example Scenario (User Booking)
*   **Scenario**: Create a hotel booking via API and verify it on the Admin UI.
*   **Flow**:
    *   `Step 1`: Step Def calls `RequestBuilder` with a `Booking` POJO.
    *   `Step 2`: API creates the record; ID is extracted.
    *   `Step 3`: `Step Def` calls `DriverFactory` to open the browser.
    *   `Step 4`: `AdminPage` (POM) logs in and searches for the extracted ID.
    *   `Step 5`: `WaitFactory` ensures search results are loaded.
    *   `Step 6`: Final Assertion verifies the UI matches the API input.

---

## 12. Error Handling Flow
*   **Failure Catching**: Handled via `ITestListener` and `@After` hooks.
*   **Screenshots**: Automatic capture of the browser state at the exact moment of failure.
*   **Logging**: `LoggerUtil` outputs stack traces to a centralized log file for post-mortem analysis.
*   **Retries**: If a test fails due to network instability, the **RetryAnalyzer** automatically reruns it once to ensure the failure is not a transient flake.

---

## 13. Best Practices Followed
*   **Page Object Model**: Strict separation of locators and test logic.
*   **DRY (Don't Repeat Yourself)**: Using common utilities for weights, clicks, and database connections.
*   **Single Responsibility**: Every class has one job (e.g., `DriverFactory` only handles browser instances).
*   **Encapsulation**: Using private locators and public methods in Page classes to protect object integrity.
*   **Data Externalization**: Zero hardcoded strings in the test logic layer.
