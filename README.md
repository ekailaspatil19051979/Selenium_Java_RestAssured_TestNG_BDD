# 🚀 Enterprise Automation Framework

## 1. Project Overview
This project is a **Hybrid Automation Framework** designed to support **End-to-End (E2E)** testing for enterprise applications. It unifies **UI (Selenium)**, **API (Rest Assured)**, and **BDD (Cucumber)** into a single, scalable architecture.

The framework follows the **Page Object Model (POM)** design pattern for UI and a **Service Object Model** for API, ensuring high maintainability and reusability. It is integrated with **CI/CD pipelines (Jenkins & Azure DevOps)** and supports **Dockerized execution**.

### 🎯 Key Capabilities
*   **Unified Testing**: Run UI and API tests in the same execution cycle.
*   **Behavior Driven Development (BDD)**: Write tests in plain English using Gherkin syntax.
*   **Data-Driven**: Externalize test data in Excel, JSON, and CSV formats.
*   **Parallel Execution**: Run tests concurrently using TestNG and Selenium Grid.
*   **Cross-Browser**: Support for Chrome, Firefox, and Edge (via Docker/Grid).
*   **Robust Reporting**: Integrated Extent Reports and Allure Reports.

---

## 2. 🛠 Tech Stack Details

| Tool | Purpose | Description |
| :--- | :--- | :--- |
| **Java 17** | Language | Core programming language for the framework. |
| **Selenium WebDriver** | UI Automation | Interacts with web browsers to simulate user actions. |
| **Rest Assured** | API Automation | Fluent library for validating RESTful web services. |
| **Cucumber** | BDD | Enables writing test scenarios in Gherkin (Given-When-Then). |
| **TestNG** | Test Runner | Manages test execution, grouping, assertions, and parallel runs. |
| **Maven** | Build Tool | Manages dependencies and project lifecycle. |
| **Jenkins** | CI/CD | Orchestrates automated test execution pipelines. |
| **Azure DevOps** | CI/CD | Microsoft's pipeline solution for building and testing. |
| **Docker** | Containerization | Packages the test environment for consistent execution anywhere. |
| **Log4j2** | Logging | Provides detailed logs for debugging. |
| **Allure / Extent** | Reporting | Generates interactive and detailed test execution reports. |

---

## 3. 📂 Project Folder Structure

```
automation-framework
├── src/main/java
│   ├── base            # Core classes (DriverFactory, BasePage, APIBase)
│   ├── pages           # Page Object Classes (Locators & Actions)
│   │   ├── components  # Reusable UI components (NavBar, Footer)
│   ├── api             # API Utilities (RequestBuilder, ResponseValidator)
│   ├── utils           # Helpers (ExcelReader, JsonReader, Logger, FakerUtil)
│   ├── config          # Configuration Readers
│   └── constants       # Framework Constants
├── src/test/java
│   ├── stepdefinitions # Cucumber Step Definitions (Glue Code)
│   ├── runners         # TestNG Runner classes
│   └── hooks           # Setup/Teardown protocols
├── src/test/resources
│   ├── features        # BDD Feature Files (.feature)
│   ├── testdata        # External Data (Excel, JSON, CSV)
│   ├── config.properties # Global Configuration
│   ├── log4j2.xml      # Logging Configuration
│   └── testng.xml      # Test Suite Configuration
├── pom.xml             # Maven Dependencies
├── Jenkinsfile         # Jenkins Pipeline Script
├── azure-pipeline.yml  # Azure DevOps Pipeline Script
├── Dockerfile          # Docker Image Definition
└── docker-compose.yml  # Selenium Grid & Runner Setup
```

---

## 4. ✅ Prerequisites

Before setting up the project, ensure you have the following installed:

*   **Java Development Kit (JDK)**: Version 17 or higher.
*   **Maven**: Version 3.8+.
*   **IDE**: IntelliJ IDEA (recommended) or Eclipse.
*   **Plugins**:
    *   *Cucumber for Java* (IntelliJ Plugin)
    *   *Gherkin* (IntelliJ Plugin)
    *   *Lombok* (if used)
*   **Browser Drivers**: Not required manually (managed by Selenium Manager or Docker).
*   **Docker Desktop**: (Optional) For containerized execution.

---

## 5. ⚙️ How to Setup Project Locally

1.  **Clone the Repository**:
    ```bash
    git clone https://your-repo-url.git
    cd automation-framework
    ```

2.  **Import into IDE**:
    *   Open IntelliJ IDEA.
    *   Select `File` -> `Open` -> Select the `pom.xml` file.
    *   Choose "Open as Project".

3.  **Install Dependencies**:
    *   Run the following command in the terminal to download all libraries:
        ```bash
        mvn clean install -DskipTests
        ```

4.  **Verify Setup**:
    *   Run a simple check to ensure everything issues:
        ```bash
        mvn clean compile
        ```

---

## 6. 🔧 Configuration Management

Global configurations are managed in `src/test/resources/config.properties`.

**Key Properties:**
```properties
# Application URL
baseurl=https://automationintesting.online/

# Browser Selection (chrome / firefox / edge)
browser=chrome

# Execution Environment (local / grid)
execution_env=local

# Timeouts
explicit.wait=10

# Credentials (Avoid committing real passwords)
username=admin
password=password123
```

---

## 7. 🏃 How to Run Tests

### Command Line Execution (Maven)

**Run All Tests:**
```bash
mvn clean test
```

**Run UI Tests Only:**
```bash
mvn test -Dcucumber.filter.tags="@ui"
```

**Run API Tests Only:**
```bash
mvn test -Dcucumber.filter.tags="@api"
```

**Run Smoke Tests:**
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

**Run Browser Override:**
```bash
mvn test -Dbrowser=firefox
```

**Parallel Execution:**
Testing parallel execution is controlled via `testng.xml` and Maven Surefire plugin settings.
```bash
mvn test -Ddataproviderthreadcount=4
```

---

## 8. 🥒 BDD Structure

### Feature Files
Located in `src/test/resources/features`. Written in Gherkin structure:
```gherkin
Feature: Booking Management

  @api @smoke
  Scenario: Create a new booking
    Given I have a valid auth token
    When I create a booking with dynamic data
    Then I verify the booking is created successfully
```

### Step Definitions
Located in `src/test/java/stepdefinitions`. Maps Gherkin steps to Java code.
*   `StepDefs.java`: Contains the logic for the steps.
*   `Hooks.java`: Manages `@Before` and `@After` scenarios (e.g., Driver initialization, Screenshots).

---

## 9. 🔌 API Automation Details

Powered by **Rest Assured**.

*   **Base Setup**: `APIBase.java` configures the `BaseURI` and default headers.
*   **Request Builder**: `RequestBuilder.java` handles `GET`, `POST`, `PUT`, `DELETE` calls generic wrappers.
*   **Authentication**: Automated in `StepDefs` using the `/auth` endpoint to generate tokens for secure operations.
*   **Validation**: `ResponseValidator.java` provides assertions for Status Codes, Response Time, and JSON Schema.

---

## 10. 📊 Data Driven Testing

The framework supports multiple data sources:

1.  **Excel**: Use `ExcelReader.java` to fetch data from `.xlsx` files in `src/test/resources/testdata`.
2.  **JSON**: Use `JSONReader.java` for complex, nested data structures.
3.  **CSV**: Use `CSVReaderUtil.java` for flat data files.
4.  **Dynamic Data**: Use `FakerUtil.java` to generate random names, emails, and dates at runtime.

---

## 11. 📈 Reporting

### Extent Reports
*   Automatically generated at `test-output/ExtentReport.html`.
*   Contains Pass/Fail charts, execution time, and logs.

### Allure Reports
*   Raw data generated in `target/allure-results`.
*   **To view report**:
    ```bash
    mvn allure:serve
    ```
*   Provides detailed step-by-step execution details and history.

---

## 12. 🚀 CI/CD Execution

### Jenkins
The `Jenkinsfile` defines the pipeline:
1.  **Checkout**: Pulls code from SCM.
2.  **Build**: Compiles the code (`mvn compile`).
3.  **Test**: Executes tests (`mvn test`).
4.  **Report**: Generates Allure report and archives artifacts.

### Azure DevOps
The `azure-pipeline.yml` defines the pipeline:
1.  **Trigger**: On commit to `main`.
2.  **Job**: Runs on `ubuntu-latest`.
3.  **Task**: Maven build and test.
4.  **Publish**: Uploads test results to Azure Test Plans.

---

## 13. 🐳 Docker Execution

### Build the Image
```bash
docker build -t automation-framework .
```

### Run Tests in Docker
Run the container directly:
```bash
docker run -it --rm automation-framework
```

### Run via Docker Compose (Selenium Grid)
Starts a local Selenium Grid (Hub + Nodes) and runs tests against it.
```bash
docker-compose up --build
```
*   **Hub**: http://localhost:4444
*   **Test Runner**: Executes tests and shuts down.

---

## 14. ❓ Troubleshooting

| Issue | Solution |
| :--- | :--- |
| **Driver Not Found** | Ensure Selenium Manager is active or `DriverFactory` is pointing to the correct Grid URL. |
| **Timeout Exception** | Increase `explicit.wait` in `config.properties`. |
| **JSON Parse Error** | Validate your `testdata.json` format. |
| **Docker Connection Refused** | Ensure `docker-compose` is running and `grid.url` is correct. |

---

## 15. 🏆 Best Practices

*   **Atomic Tests**: Each test should be independent and valid on its own.
*   **Reuse Components**: Use `api/`, `pages/`, and `utils/` instead of duplicating code.
*   **Descriptive Tagging**: Use tags like `@sanity`, `@regression`, `@wip` for easy filtering.
*   **Clean Code**: Follow Java naming conventions (CamelCase for classes, lowerCamelCase for methods).
*   **No Hardcoding**: Always use `config.properties` or `constants/` for static data.

---

## 16. 🤝 Contribution Guidelines

1.  **Branching**: Create a feature branch for changes (`feature/new-test-scenario`).
2.  **Commit Messages**: Use clear messages (`feat: added login tests`, `fix: corrected timeout`).
3.  **Pull Requests**:
    *   Push key changes.
    *   Ensure all tests pass locally.
    *   Create a PR and assign a reviewer.

---
**Happy Testing!** 🕵️‍♂️
