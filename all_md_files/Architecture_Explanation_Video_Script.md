
# Architecture Explanation Video Script

---

## Opening (0:20)

Hi, in this video I will explain the architecture of my automation framework and how it supports both UI and API automation using BDD principles.

This framework is built using Java, Selenium, RestAssured, Cucumber, and TestNG, and is designed to be scalable, reusable, and CI/CD ready.

---

## High Level Overview (0:45)

At a high level, this is a hybrid automation framework.

Hybrid means:

- It supports UI automation using Selenium  
- It supports API automation using RestAssured  
- And both are integrated under a single Cucumber BDD layer  

All test scenarios are written in Gherkin feature files, which makes the framework business-friendly and easy to understand.

---

## Layered Architecture Explanation (1:00)

The framework follows a clean layered architecture:

1. Feature Files  
2. Step Definitions  
3. Service Layer  
4. Utility Layer  
5. Base Layer  
6. Execution Layer  

Let me explain each layer one by one.

---

## Feature Files Layer (0:40)

The topmost layer is the Feature Files layer.

Here we write test scenarios in plain English using Gherkin syntax.

For example:

- Login scenarios  
- End-to-end booking flows  
- Negative test cases  
- API validation scenarios  

These feature files act as the single source of truth for business requirements.

---

## Step Definitions Layer (0:50)

Below feature files we have the Step Definitions layer.

This layer converts Gherkin steps into executable Java code.

The step definition classes:

- Read input from feature files  
- Call API service methods  
- Call UI page object methods  
- Perform validations  

Importantly, the step definitions do not contain Selenium or RestAssured code directly.  
That logic is abstracted to lower layers.

---

## Dependency Injection – ScenarioContext (1:00)

To share data between steps, we use constructor-based dependency injection.

Cucumber automatically provides a shared ScenarioContext object to all step definition classes.

This allows us to:

- Pass data between steps  
- Store API responses  
- Share dynamic values like IDs and tokens  
- Avoid static variables  

This design makes the framework:

- Thread safe  
- Parallel execution friendly  
- Clean and maintainable  

---

## API Automation Layer (1:00)

Now coming to the API Automation layer.

All API interactions are handled using RestAssured.

We have a dedicated service layer consisting of:

- RequestBuilder – to construct API requests  
- ResponseValidator – to validate API responses  
- APIBase – common API configurations  
- Logging filters  

This abstraction ensures that step definitions remain simple and readable, while all API complexity is centralized.

---

## UI Automation Layer – Page Object Model (1:00)

For UI automation, the framework follows the Page Object Model design pattern.

All web pages are implemented as Java classes under the pages package.

For example:

- LoginPage  
- HomePage  
- BookingPage  

Each page class contains:

- Web element locators  
- Business methods  
- Reusable UI actions  

Driver management is handled by DriverFactory, and smart waits are implemented using WaitFactory.

---

## Utility Layer (0:45)

The framework has a rich utility layer which provides reusable components such as:

- ExcelReader for Excel data  
- JSONReader and CSV utilities  
- FakerUtil for dynamic test data  
- LoggerUtil for logging  
- DB utilities  
- Accessibility and visual testing helpers  

This keeps the core automation code clean and reusable.

---

## Test Execution Layer (0:40)

For execution, we use:

- TestNG as the test runner  
- Maven as the build tool  
- Cucumber for BDD integration  

Tests can be executed through:

- IDE  
- Maven commands  
- Jenkins pipelines  
- Docker containers  

Parallel execution and retry mechanisms are also supported.

---

## Reporting (0:30)

The framework supports multiple reporting tools:

- Allure Reports  
- Extent Reports  

After every execution we get:

- Detailed test results  
- Screenshots  
- Logs  
- Execution trends  

---

## CI/CD Integration (0:45)

This framework is fully CI/CD ready.

It includes:

- Jenkinsfile for Jenkins pipelines  
- Azure DevOps pipeline configuration  
- Dockerfile and docker-compose  

So tests can run:

- On local machines  
- In Jenkins  
- In cloud pipelines  
- Inside Docker containers  

This makes it enterprise-ready.

---

## OOPs & Design Patterns (0:40)

From a design perspective, the framework uses:

OOPs principles like:

- Encapsulation  
- Abstraction  
- Inheritance  
- Polymorphism  

And design patterns such as:

- Page Object Model  
- Factory Pattern  
- Builder Pattern  
- Dependency Injection  

---

## Closing Summary (0:30)

To summarize:

This is a modern, scalable hybrid automation framework that supports UI, API, and BDD automation with strong architecture principles and full CI/CD integration.

Thank you for watching.

---

### End of Script
