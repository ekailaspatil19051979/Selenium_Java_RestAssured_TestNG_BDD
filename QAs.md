# 🎤 Automation Framework Interview Guide (QAs.md)

This document is designed to help you explain the automation framework during interviews. It covers architecture, flow, challenges, and best practices in a professional, structured manner.

---

## 1. 🌟 High Level Framework Overview
**"Can you explain your automation framework?"**

> "I have designed and developed a **Hybrid BDD Automation Framework** using **Java** covering both **UI (Selenium)** and **API (Rest Assured)** testing. It follows the **Page Object Model (POM)** design pattern for maintainability and uses **Cucumber** for behavior-driven scenarios.
>
> The framework is **Data-Driven**, supporting Excel, JSON, and CSV data sources. It allows **Parallel Execution** using TestNG and Selenium Grid. For CI/CD, it is integrated with **Jenkins** and **Azure DevOps**, and the entire execution environment is **Dockerized** for consistency."

---

## 2. 🏗️ Architecture Diagram Explanation
**"Describe the architecture of your framework."**

> "The framework is built in layers to ensure separation of concerns:
>
> 1.  **Test Layer (BDD)**: We have **Feature Files** written in Gherkin that describe business scenarios. These map to **Step Definitions** where the actual test logic resides.
> 2.  **Test Runner**: We use **TestNG** as the execution engine to orchestrate the Cucumber tests.
> 3.  **Page Layer (POM)**: For UI, we have **Page Classes** (like `LoginPage`, `BookingPage`) that contain WebElements and methods to interact with them.
> 4.  **API Layer**: We have an `APIBase` and `RequestBuilder` to construct Rest Assured calls cleanly, separating test logic from HTTP specifics.
> 5.  **Base Layer**: A `DriverFactory` manages the WebDriver lifecycle, ensuring thread safety for parallel execution.
> 6.  **Utility Layer**: Helper classes for **Excel/JSON reading**, **Logging (Log4j2)**, and **Reporting (Allure/Extent)**.
> 7.  **CI/CD & Infrastructure**: The code resides in Git, triggered by Jenkins/Azure, and runs inside Docker containers using Selenium Grid."

---

## 3. 🔄 Framework Flow (End-to-End)
**"Walk me through the execution flow from code push to report generation."**

> "Sure, here is the end-to-end flow:
>
> 1.  **Trigger**: A developer pushes code to the repository, or a scheduled job acts as a trigger.
> 2.  **CI Pipeline**: **Jenkins** (or Azure DevOps) detects the change and pulls the latest code.
> 3.  **Build**: Maven resolves all dependencies defined in `pom.xml` and compiles the project.
> 4.  **Execution**: Maven triggers the **TestNG Runner**.
> 5.  **Test Logic**:
>     *   For **API steps**, Rest Assured sends requests to the backend and validates responses.
>     *   For **UI steps**, the `DriverFactory` initializes a WebDriver (local or remote Grid), and Selenium interacts with the browser.
> 6.  **Reporting**: Once execution finishes, **Allure** and **Extent Reports** are generated from the results.
> 7.  **Notification**: The pipeline publishes the results and artifacts, sending notifications to the team."

---

## 4. 📂 Folder Structure Explanation
**"How is your project structure organized?"**

> "We follow a standard Maven structure with logical separation:
>
> *   `src/main/java`: Contains the core framework logic.
>     *   **`base`**: `DriverFactory`, `BasePage`, `BaseTest` – the foundational classes.
>     *   **`pages`**: Page Object classes (e.g., `LoginPage`, `HomePage`).
>     *   **`api`**: API utilities like `RequestBuilder` and `ResponseValidator`.
>     *   **`utils`**: `ExcelReader`, `JsonReader`, `LoggerUtil`, `FakerUtil`.
>     *   **`config`**: `ConfigReader` to load properties.
> *   `src/test/java`: Contains the test execution logic.
>     *   **`stepdefinitions`**: Implementation of Gherkin steps.
>     *   **`runners`**: TestNG runner classes.
>     *   **`hooks`**: Setup and teardown logic (`@Before`, `@After`).
> *   `src/test/resources`: Non-code assets.
>     *   **`features`**: Cucumber `.feature` files.
>     *   **`testdata`**: Excel sheets, JSON, CSV files.
>     *   **`config.properties`**: Global settings (URL, credentials).
> *   **Root**: `pom.xml`, `Jenkinsfile`, `Dockerfile`, `docker-compose.yml`."

---

## 5. 🔑 Key Components Explanation
**"Explain the key components you built."**

*   **Page Object Model (POM)**: "I used POM to separate locators from test logic. Each web page has a corresponding Java class. This makes code reusable and maintenance easy—if a locator changes, I update it in just one place."
*   **Step Definitions**: "This is the glue code where Gherkin steps are translated into Java methods. It acts as the bridge between the business requirement and the automation code."
*   **Hooks**: "I use Cucumber Hooks for setup and teardown. `@Before` initializes the API base or WebDriver, and `@After` captures screenshots on failure and quits the driver."
*   **DriverFactory**: "I implemented a **ThreadLocal** WebDriver pattern. This ensures that when running tests in parallel, each thread gets its own isolated browser instance, preventing session conflicts."
*   **Config Logic**: "A `ConfigReader` class loads `config.properties`, allowing us to switch environments (QA/Stage/Prod) or browsers without changing code."

---

## 6. 🔗 High Level UI & API Integration
**"How do you integrate UI and API testing in the same framework?"**

> "We advocate for an **API-First** testing approach to improve speed and stability.
>
> For example, instead of creating test data through the UI (which is slow and flaky), I use **Rest Assured** in the `@Before` hook or a `Given` step to create the necessary data via API.
>
> **Scenario**: Validate a Booking on the UI.
> 1.  **API**: Send a `POST /booking` request to create a booking and get the Booking ID.
> 2.  **UI**: Open the browser, login, and search for that specific Booking ID.
> 3.  **Validation**: Verify the details match on the screen.
>
> This reduces execution time significantly and ensures we are testing the UI logic, not just data entry."

---

## 7. 📊 Data Driven Approach
**"How do you handle test data?"**

> "The framework is designed to be agnostic of the data source. We support:
> *   **Excel (Apache POI)**: For complex scenarios with multiple permutations (e.g., registration forms).
> *   **JSON**: For API payloads where nested structures are common.
> *   **CSV**: For simple flat data lists.
> *   **Dynamic Data (Faker)**: I integrated `JavaFaker` to generate random names, emails, and dates at runtime to ensure we don't have unique constraint violations."

---

## 8. 🚀 CI/CD Flow Explanation
**"Explain your CI/CD setup."**

> "We use **Jenkins** as our CI tool. I created a declarative **Jenkinsfile** with specific stages.
>
> 1.  **Checkout**: Pulls the code from Git.
> 2.  **Build**: Runs `mvn clean compile` to check for compilation errors.
> 3.  **Test**: Runs `mvn test`. We can pass parameters like `-Dbrowser=firefox` or `-Dcucumber.filter.tags="@smoke"` dynamically from Jenkins.
> 4.  **Docker**: We can try running the tests inside a Docker container using the `Dockerfile` stage or spin up a Selenium Grid using `docker-compose`.
> 5.  **Report**: Finally, the **Allure** plugin in Jenkins publishes the trend report."

---

## 9. 📈 Reporting Mechanism
**"What kind of reports does your framework generate?"**

> "Reporting is crucial for analysis. We use:
> *   **Allure Reports**: This is our primary report. It provides a rich, interactive dashboard showing valid defects, flaky tests, and execution history. It effectively maps steps to results.
> *   **Extent Reports**: Used for a quick HTML summary shareable via email.
> *   **Log4j2**: We capture detailed execution logs (Info/Error/Debug) which are essential for debugging failures."

---

## 10. ⚡ Parallel Execution Strategy
**"How do you achieve parallel execution?"**

> "In **TestNG**, I configured the `testng.xml` suite to run tests in parallel using `<suite parallel="tests" thread-count="4">`.
>
> Crucially, to support this, my `DriverFactory` uses `ThreadLocal<WebDriver>`. This ensures that each test thread has its own isolated instance of the WebDriver. For API tests, Rest Assured is inherently thread-safe for the most part, but we ensure distinct variable scopes for scenario contexts."

---

## 11. 📝 Real-Time Example Scenario
**"Give me a concrete example of a test flow."**

> "**Scenario: Verify User can View a Newly Created Booking.**
>
> 1.  **Given**: I Create a booking for 'John Doe' using the API (`POST /booking`).
> 2.  **And**: I capture the 'Booking ID' from the JSON response.
> 3.  **When**: I launch the application and navigate to the Admin Dashboard.
> 4.  **And**: I search for the 'Booking ID'.
> 5.  **Then**: I verify that the name 'John Doe' and the dates are correctly displayed in the grid.
>
> This tests the full integration of the backend and frontend."

---

## 12. ⚠️ Challenges Faced and Solutions
**"What challenges did you face and how did you solve them?"**

*   **Synchronization Issues**: "Tests failed intermittently due to slow page loads. I solved this by implementing **Fluent Waits** and explicit waits in `BasePage` instead of using `Thread.sleep`."
*   **Stale Element Exceptions**: "I implemented a retry mechanism that attempts to find the element again if the DOM updates unexpectedly."
*   **Test Data Management**: "Hardcoded data caused failures when running repeatedly. I moved to using **Faker** for dynamic data and **API** for data creation/cleanup (teardown)."
*   **Docker Network Issues**: "When running in Docker, `localhost` refers to the container, not the host. I updated the configuration to use the Docker service name (`selenium-hub`) via environment variables."

---

## 13. ✅ Best Practices Followed
**"What best practices do you follow?"**

1.  **Reusability**: "Common methods (Click, Type, Wait) are wrapped in `BasePage` and `APIBase`."
2.  **Maintainability**: "Strict adherence to POM and BDD ensures that UI changes only require updates in Page classes, not the test logic."
3.  **Coding Standards**: "Followed Java conventions, used Lombok to reduce boilerplate, and enforced code formatting."
4.  **Robust Exception Handling**: "Used Try-Catch blocks in utilities and specific assertions to provide meaningful failure messages."
5.  **Secrets Management**: "Never stored passwords in code; passed them via CI/CD environment variables."

---

## 14. 💬 Sample Interview Answers

**Q: "How do you handle authentication in your framework?"**
> "I handle it via API. Before the test starts, I send a POST request to the `/auth` endpoint, extract the token, and inject it into the browser cookies or use it in subsequent API headers. This bypasses the slow login UI for non-login scenarios."

**Q: "How do you run tests on different environments?"**
> "I use a property file `config.properties` and Maven profiles. I can pass a flag like `-Denv=qa` or `-Denv=stage` commands to pick the correct URL and credentials at runtime."

**Q: "Explain how you handle flaky tests."**
> "First, I analyze the root cause—usually timing or data. I improve the Wait strategies. For strictly flaky tests (network blips), I implemented the `IRetryAnalyzer` interface in TestNG to automatically retry failed tests once before marking them as failed."

---

## 15. 🧠 Advanced QA - For 5+ Years Experience

**Q: "How do you optimize test execution time in a large suite (500+ tests)?"**
> "Execution time is a major bottleneck. I optimized it by:
> 1.  **Parallel Execution**: Running tests in parallel at the class or method level using TestNG and Selenium Grid, scaling up to 10-15 nodes via Docker.
> 2.  **API Seeding**: Reducing UI dependence by using APIs for data creation (PRE-REQUISITES) instead of UI interactions, which is 10x faster.
> 3.  **Headless Execution**: Running smoke tests in Headless Chrome (`--headless`) on CI pipelines.
> 4.  **Selective Execution**: Running only relevant tests using 'Tagging' (`@regression`, `@module`) instead of the full suite for every commit."

**Q: "How do you handle Singleton Design Pattern in your automation?"**
> "I use the **Singleton Pattern** for configuration management. The `ConfigReader` class is designed as a Singleton so that we only load the properties file once during the entire execution lifecycle, preventing unnecessary I/O operations."

**Q: "How do you manage Merge Conflicts in automation code?"**
> "Since automation code is treated like production code, merge conflicts happen.
> *   I follow a strict **Feature Branch Workflow** (Gitflow).
> *   I pull the latest `main` branch into my local feature branch frequently (`git pull origin main`) to resolve conflicts early.
> *   For `page objects`, conflicts are common; I ensure we check *line-by-line* changes to keep locators valid."

**Q: "Explain how you designed the 'DriverFactory' for Thread Safety?"**
> "In parallel execution, a static WebDriver instance would be shared across threads, causing chaos.
> *   I used `ThreadLocal<WebDriver>` in my `DriverFactory`.
> *   This creates a **separate copy** of the WebDriver for each thread (test).
> *   I have a `getDriver()` method that returns the driver for the *current thread*, ensuring total isolation between parallel tests."

**Q: "How do you differentiate between a Bug in the Application vs. a Bug in the Script?"**
> "I follow a triage process:
> 1.  **Manual Verification**: If a test fails, I first try to replicate the steps manually.
> 2.  **Logs & Screenshots**: I check the Allure report screenshot and Log4j logs.
>     *   If ElementNotFound -> Likely script issue (Timing/Locator change).
>     *   If 500 error / Data mismatch -> Likely Application Bug.
> 3.  **Environment Check**: I verify if the environment was stable during execution."

**Q: "Have you implemented CI/CD Pipelines yourself? Explain the YAML structure."**
> "Yes, I set up the **Azure DevOps Pipeline**. It consists of:
> *   **Trigger**: Defines when to run (`branches: include: [ main ]`).
> *   **Pool**: The agent VM (`vmImage: 'ubuntu-latest'`).
> *   **Variables**: Defined globally or in variable groups.
> *   **Steps**:
>     1.  `Maven@3`: To run `clean test`.
>     2.  `PublishTestResults@2`: To parse `surefire-reports` XMLs.
>     3.  `PublishBuildArtifacts@1`: To upload HTML reports for the team."

**Q: "How do you handle 'Dynamic Elements' or 'Shadow DOM'?"**
> "For **Dynamic Elements**, I use robust XPaths with `contains()`, `starts-with()`, or anchor them relative to stable parent elements.
> For **Shadow DOM**, standard Selenium locators don't work. I use `SearchContext` or **JavascriptExecutor** (`return document.querySelector('host').shadowRoot.querySelector('target')`) to pierce the Shadow DOM and interact with elements."

**Q: "How do you implement Database Validation in your framework?"**
> "For database validation, I use **JDBC** to connect to the database.
> *   I created a `DBUtil` class with methods like `executeQuery()` and `getResultSet()`.
> *   Connection details are stored in `config.properties`.
> *   Example: After creating a booking via API, I query the database to verify the record was inserted correctly with the right status.
> *   I ensure connections are closed in a `finally` block to prevent connection leaks."

**Q: "Explain your approach to API Contract Testing."**
> "API Contract Testing ensures the API response structure doesn't break.
> *   I use **JSON Schema Validation** with Rest Assured's `matchesJsonSchemaInClasspath()`.
> *   I store schema files in `src/test/resources/schemas/`.
> *   This validates that the response has the expected fields, data types, and structure even if values change."

**Q: "How do you handle Test Data Cleanup?"**
> "Data cleanup is critical to avoid test pollution.
> *   **API Cleanup**: In the `@After` hook, I call DELETE endpoints to remove test data created during the test.
> *   **Database Cleanup**: For integration tests, I use transactions with rollback or direct SQL DELETE statements.
> *   **Isolation**: Each test creates unique data (using Faker) to minimize dependencies."

**Q: "What metrics do you track for automation?"**
> "I track several KPIs:
> 1.  **Test Coverage**: Percentage of features automated vs. manual.
> 2.  **Pass Rate**: Percentage of tests passing in each run.
> 3.  **Execution Time**: Average time per test and total suite time.
> 4.  **Flaky Test Rate**: Tests that fail intermittently.
> 5.  **Defect Detection Rate**: Bugs found by automation vs. manual testing.
> These are tracked in Allure's trend charts and custom dashboards."

**Q: "How do you mentor junior automation engineers?"**
> "As a senior engineer with 5+ years experience:
> *   **Code Reviews**: I review their PRs, providing constructive feedback on design patterns and best practices.
> *   **Pair Programming**: I pair with them on complex scenarios like handling iframes or API chaining.
> *   **Documentation**: I maintain detailed framework documentation and conduct knowledge transfer sessions.
> *   **Standards**: I enforce coding standards and encourage them to write reusable, maintainable code."

**Q: "Explain the Factory Design Pattern in your framework."**
> "I use the **Factory Pattern** in `DriverFactory` to create browser instances.
> *   Based on the `browser` property (chrome/firefox/edge), the factory returns the appropriate WebDriver instance.
> *   This centralizes driver creation logic and makes it easy to add new browsers without changing test code.
> *   It also handles remote driver creation for Selenium Grid."

**Q: "How do you handle Cross-Browser Testing?"**
> "Cross-browser testing is achieved through:
> 1.  **Parameterization**: I pass `-Dbrowser=firefox` or `-Dbrowser=chrome` at runtime.
> 2.  **TestNG XML**: I create multiple `<test>` blocks with different browser parameters.
> 3.  **Selenium Grid**: I run tests in parallel across Chrome, Firefox, and Edge nodes.
> 4.  **Docker**: I use different browser images in `docker-compose.yml` for consistent environments."

**Q: "What's your strategy for Mobile Automation?"**
> "While this framework focuses on web, I've worked with mobile automation using **Appium**.
> *   Similar architecture: Page Object Model for screens, TestNG for execution.
> *   **DriverFactory** extended to support `AndroidDriver` and `IOSDriver`.
> *   **Cloud Devices**: Integration with BrowserStack or Sauce Labs for real device testing.
> *   **Gestures**: Implemented swipe, tap, and scroll actions using TouchActions."

**Q: "How do you ensure your framework is scalable?"**
> "Scalability is built into the design:
> 1.  **Modular Architecture**: Each layer (API, UI, Utils) is independent.
> 2.  **Thread Safety**: ThreadLocal ensures parallel execution without conflicts.
> 3.  **Configuration-Driven**: Easy to add new environments or browsers via properties.
> 4.  **Docker & Grid**: Can scale horizontally by adding more Grid nodes.
> 5.  **Minimal Dependencies**: Only essential libraries to reduce build time."

**Q: "Describe a complex bug you found through automation that manual testing missed."**
> "We had a race condition bug in the booking system.
> *   When two users tried to book the same room simultaneously, both bookings succeeded (double booking).
> *   Manual testing couldn't reproduce this consistently.
> *   I created a parallel test using TestNG that simulated concurrent API calls.
> *   The automation consistently reproduced the issue, leading to a fix in the backend locking mechanism."

---

## 16. 🎯 Leadership & Process Questions

**Q: "How do you decide what to automate and what to keep manual?"**
> "I use the **Automation Pyramid** principle:
> *   **High Priority**: Regression tests, smoke tests, critical user journeys.
> *   **Medium Priority**: Data-driven scenarios with multiple permutations.
> *   **Low Priority**: One-time tests, exploratory testing, UI-heavy tests with frequent changes.
> *   **Never Automate**: Tests that require human judgment (UX, visual design)."

**Q: "How do you handle framework maintenance?"**
> "Framework maintenance is ongoing:
> *   **Dependency Updates**: Monthly review of Maven dependencies for security patches.
> *   **Refactoring**: Quarterly refactoring sessions to remove technical debt.
> *   **Monitoring**: Track flaky tests and fix root causes immediately.
> *   **Documentation**: Keep README and QAs.md updated with new features."

**Q: "What's your approach to Continuous Testing in DevOps?"**
> "Continuous Testing means tests run at every stage:
> 1.  **Commit Stage**: Smoke tests run on every commit (5-10 min).
> 2.  **Nightly Builds**: Full regression suite runs overnight.
> 3.  **Pre-Production**: Sanity tests run before deployment to staging.
> 4.  **Production**: Smoke tests run post-deployment to verify critical paths.
> All integrated via Jenkins pipelines with automatic rollback on failures."

---

## 17. ⚡ The "Mega 100" - Rapid Fire Interview Prep

### **Phase 1: Core Java for Automation (1-25)**

1. **Q: Difference between `String`, `StringBuilder`, and `StringBuffer`?**
   > `String` is immutable. `StringBuilder` is mutable and not thread-safe (faster). `StringBuffer` is mutable and thread-safe (synchronized).
2. **Q: What is a `ThreadLocal` variables?**
   > Variables that provide a separate copy for each thread. Crucial for parallel WebDriver execution.
3. **Q: Explain Java Reflection API in automation.**
   > Used to inspect or modify classes at runtime. Helpful for dynamically calling methods in unit testing frameworks.
4. **Q: Difference between `HashMap` and `LinkedHashMap`?**
   > `HashMap` doesn't maintain order. `LinkedHashMap` maintains the insertion order.
5. **Q: What is the `super` keyword?**
   > Used to refer to the immediate parent class object or to call parent constructors/methods.
6. **Q: Can we override a static method?**
   > No, it's called Method Hiding. Static methods belong to the class, not the instance.
7. **Q: Difference between `throw` and `throws`?**
   > `throw` is used to explicitly throw an exception. `throws` is used in a method signature to declare exceptions.
8. **Q: What is a `Functional Interface`?**
   > An interface with exactly one abstract method (e.g., `Runnable`, `Comparator`). Used with Lambda expressions.
9. **Q: Difference between `Abstract Class` and `Interface`?**
   > Abstract classes can have state (fields) and constructors; Interfaces define behavior and support multiple inheritance.
10. **Q: What is the purpose of `finally` block?**
    > To execute important code (like closing DB connections) regardless of whether an exception is thrown.
11. **Q: How does `ArrayList` work internally?**
    > It uses a dynamic array. When it reaches capacity, it creates a new larger array (usually 1.5x) and copies elements.
12. **Q: What is `Polymorphism` in automation?**
    > Overloading (same method name, different params) and Overriding (child class providing specific implementation).
13. **Q: Difference between `Checked` and `Unchecked` exceptions?**
    > Checked are verified at compile-time (e.g., `IOException`). Unchecked occur at runtime (e.g., `NullPointerException`).
14. **Q: What is a `Constructor`?**
    > A special method used to initialize objects. It has the same name as the class.
15. **Q: Explain `Encapsulation` in POM.**
    > Declaring WebElements as `private` and providing `public` methods to interact with them.
16. **Q: What is the `static` keyword?**
    > Indicates a member belongs to the class itself rather than instances.
17. **Q: Difference between `==` and `.equals()`?**
    > `==` checks reference equality. `.equals()` checks content equality.
18. **Q: What is a `Wrapper Class`?**
    > Converts primitive data types into objects (e.g., `int` to `Integer`).
19. **Q: What are `Java Streams`?**
    > Introduced in Java 8 to process collections of objects in a functional way (filter, map, reduce).
20. **Q: What is a `Lambda Expression`?**
    > A short way to write anonymous functions.
21. **Q: Can we have a `try` block without `catch`?**
    > Yes, if followed by a `finally` block.
22. **Q: What is `Garbage Collection`?**
    > Automatic memory management process that deletes unused objects.
23. **Q: What is the `volatile` keyword?**
    > Ensures that the value of a variable is always read from main memory, not from thread cache.
24. **Q: What is an `Enum`?**
    > A special data type that represents a group of constants (e.g., `BrowserType.CHROME`).
25. **Q: What is a `List` vs `Set`?**
    > `List` allows duplicates and maintains order. `Set` is unique and usually unordered.

### **Phase 2: Advanced Selenium WebDriver (26-50)**

26. **Q: Difference between `driver.close()` and `driver.quit()`?**
    > `close()` shuts the current active window. `quit()` shuts all windows and the driver session.
27. **Q: How to handle `Window Handles`?**
    > Use `driver.getWindowHandles()` to get a set of IDs and `driver.switchTo().window(id)` to switch.
28. **Q: What is `JavascriptExecutor`?**
    > An interface to execute JavaScript code from Selenium (useful for clicking hidden elements or scrolling).
29. **Q: How to handle `Alerts`?**
    > `driver.switchTo().alert()` then use `accept()`, `dismiss()`, or `getText()`.
30. **Q: What is `FluentWait`?**
    > Defines the maximum time to wait for a condition and the frequency to check it (polling).
31. **Q: How to perform `Drag and Drop`?**
    > Use the `Actions` class: `actions.dragAndDrop(source, target).perform()`.
32. **Q: What is a `Headless` browser?**
    > Running a browser without a GUI (faster, used in CI/CD).
33. **Q: How to take a `Screenshot`?**
    > Cast driver to `TakesScreenshot` and use `getScreenshotAs(OutputType.FILE)`.
34. **Q: How to handle `Frames`?**
    > `driver.switchTo().frame(index/name/element)`. Always switch back using `defaultContent()`.
35. **Q: Difference between `Absolute` and `Relative` XPath?**
    > Absolute starts from root (`/`). Relative starts from anywhere (`//`) - always prefer relative.
36. **Q: What are `SVG` elements in Selenium?**
    > Use `//*[local-name()='svg']` to locate them.
37. **Q: How to handle `Shadow DOM`?**
    > Use `getShadowRoot()` in Selenium 4 or JSExecutor in older versions.
38. **Q: What is the `StaleElementReferenceException`?**
    > Occurs when an element is no longer attached to the DOM. Solution: Relocate the element.
39. **Q: How to handle `Dropdowns` without `Select` tag?**
    > Locate the parent, click to expand, and find the specific list item.
40. **Q: What is a `Wait` in Selenium?**
    > Strategies to synchronize the script with the application (Implicit, Explicit, Fluent).
41. **Q: How to find all links on a page?**
    > `driver.findElements(By.tagName("a"))`.
42. **Q: How to verify an element is missing?**
    > Check if `driver.findElements(...)` returns an empty list.
43. **Q: What is `PageFactory`?**
    > An extension of POM to initialize elements using `@FindBy` annotations.
44. **Q: What is the `Actions` class?**
    > Used for complex user gestures like double-click, right-click, mouse-hover.
45. **Q: How to refresh a page?**
    > `driver.navigate().refresh()` or `driver.get(driver.getCurrentUrl())`.
46. **Q: How to handle multiple tabs?**
    > Get handles, iterate, and switch based on title or URL.
47. **Q: What is `DesiredCapabilities` vs `Options`?**
    > `Capabilities` were for older versions; Selenium 4 uses specific `Options` (e.g., `ChromeOptions`).
48. **Q: How to upload a file?**
    > Use `sendKeys(filePath)` on the `input` element of type `file`.
49. **: How to handle cookie?**
    > `driver.manage().getCookies()`, `addCookie()`, `deleteCookie()`.
50. **Q: What is a `Cdp` object in Selenium 4?**
    > Chrome DevTools Protocol integration to simulate network issues, geolocation, etc.

### **Phase 3: API & Rest Assured Automation (51-75)**

51. **Q: What is `Rest Assured`?**
    > A Java library used for testing RESTful web services.
52. **Q: Difference between `GET` and `POST`?**
    > `GET` retrieves data. `POST` creates new data.
53. **Q: How to send a Request Body in Rest Assured?**
    > Use `.body(object/string)` within the `given()` section.
54. **Q: What is `Serialization`?**
    > Converting a POJO (Java Object) into JSON/XML format.
55. **Q: What is `Deserialization`?**
    > Converting a JSON/XML response back into a Java Object (POJO).
56. **Q: How to handle `Query Parameters`?**
    > Use `.queryParam("key", "value")` in `given()`.
57. **Q: How to validate `Status Code`?**
    > `then().statusCode(200)`.
58. **Q: What is `BaseURI` vs `BasePath`?**
    > `BaseURI` is the main URL (e.g., api.site.com). `BasePath` is the endpoint path (e.g., /v1/users).
59. **Q: How to extract a value from a JSON Response?**
    > Use `JsonPath`: `response.jsonPath().getString("path.to.field")`.
60. **Q: What are `RequestSpecification` and `ResponseSpecification`?**
    > Interfaces used to pre-define and reuse request/response configurations.
61. **Q: How to handle `Authentication`?**
    > Use `.auth().basic(user, pass)`, `.oauth2(token)`, or headers.
62. **Q: What is `JSON Schema Validation`?**
    > Verifying the structure (keys, types) of a response against a predefined schema.
63. **Q: Difference between `PUT` and `PATCH`?**
    > `PUT` replaces the entire resource. `PATCH` updates specific fields.
64. **Q: How to log request and response?**
    > Use `.log().all()` or `.log().ifValidationFails()`.
65. **Q: What is `GPath` in Rest Assured?**
    > A path expression language used to navigate and query JSON/XML.
66. **Q: How to send `Headers`?**
    > Use `.header("name", "value")` or `.headers(map)`.
67. **Q: What is a `Payload`?**
    > The data sent in the body of an HTTP request.
68. **Q: How to handle dependent APIs (API Chaining)?**
    > Extract data from Response A and pass it as a parameter/body to Request B.
69. **Q: Difference between `200` and `201` status codes?**
    > `200` is OK (Success). `201` is Created (Resource successfully created).
70. **Q: What is `RestAssured.config()`?**
    > Used to configure global settings like SSL, timeouts, or redirection.
71. **Q: How to validate Response Header?**
    > `then().header("Content-Type", "application/json")`.
72. **Q: What is `Hamcrest` matchers?**
    > A library used with Rest Assured for expressive assertions (e.g., `equalTo()`, `hasItem()`).
73. **Q: How to send multi-part form data (file upload)?**
    > Use `.multiPart("file", new File(path))`.
74. **Q: What is a `Root Path` in Rest Assured?**
    > `RestAssured.rootPath = "store.book"` simplifies queries by setting a default starting point.
75. **Q: How to handle SSL verification issues?**
    > Use `.relaxedHTTPSValidation()`.

### **Phase 4: BDD (Cucumber), TestNG & DevOps (76-100)**

76. **Q: What is `Gherkin`?**
    > A structured language used to write business-readable test scenarios (Given/When/Then).
77. **Q: Difference between `Scenario` and `Scenario Outline`?**
    > `Scenario` runs once. `Scenario Outline` runs multiple times using an `Examples` table.
78. **Q: What are `Cucumber Tags`?**
    > Used to group and execute specific tests (e.g., `@smoke`, `@regression`).
79. **Q: What is a `Background` in Cucumber?**
    > Steps that run before every scenario in a feature file.
80. **Q: What is `Step Definition`?**
    > The Java implementation of a Gherkin step.
81. **Q: Difference between `Dry Run` and `Strict` mode?**
    > `Dry Run` checks if steps have implementations without running. `Strict` fails if steps are missing.
82. **Q: What is a `Hook`?**
    > Blocks of code that run before/after scenarios.
83. **Q: What is `TestNG Listener`?**
    > Interfaces that allow customizing TestNG's behavior (e.g., logging results, taking screenshots on failure).
84. **Q: What are `TestNG Groups`?**
    > Allow running tests sorted into logical categories (`groups = {"smoke"}`).
85. **Q: Difference between `@BeforeMethod` and `@BeforeClass`?**
    > `@BeforeMethod` runs before every test method. `@BeforeClass` runs once per class.
86. **Q: What is `Priority` in TestNG?**
    > Defines execution order (lower numbers run first).
87. **Q: How to achieve `Parallelism` in TestNG?**
    > Using `parallel` and `thread-count` attributes in `testng.xml`.
88. **Q: What is `Maven Lifecycle`?**
    > Stages like `compile`, `test`, `package`, `install`, `deploy`.
89. **Q: Difference between `Dependency` and `Plugin` in Maven?**
    > `Dependency` is a library used by your code. `Plugin` is used by Maven to perform tasks (like building or running tests).
90. **Q: What is a `SNAPSHOT` version in Maven?**
    > Indicates a version under development (not stable).
91. **Q: What is `Git Stash`?**
    > Temporarily saves uncommitted changes so you can switch branches.
92. **Q: Difference between `Merge` and `Rebase`?**
    > `Merge` creates a new commit joining histories. `Rebase` rewrites history by moving commits to a new base.
93. **Q: What is a `Pull Request`?**
    > A way to propose changes to a repository and request a review.
94. **Q: What is `CI/CD`?**
    > Continuous Integration (frequent merges/tests) and Continuous Deployment (automated releases).
95. **Q: What is a `Docker Image`?**
    > A lightweight, standalone package that includes everything needed to run software.
96. **Q: What is `Docker Compose`?**
    > A tool for defining and running multi-container Docker applications.
97. **Q: Explain `Jenkins Pipeline`?**
    > A suite of plugins that supports implementing and integrating continuous delivery pipelines.
98. **Q: What is `Allure Environment` properties?**
    > A file (`environment.properties`) used to show environment info in the report.
99. **Q: How to run tests on a `Remote Agent` in Jenkins?**
    > By defining nodes/slaves in Jenkins configuration and assigning jobs to them using labels.
100. **Q: What is your approach to handling `Environment Secrets`?**
    > Use Jenkins Credentials or Azure Key Vault to store passwords, never hardcode them.

---

## 18. 🌟 Advanced Framework Innovations (The 'Expert' Tier)

**Q: "How do you handle flaky tests in your enterprise framework?"**
> "I implemented a two-fold approach. First, I use a **Retry Analyzer** (via TestNG's `IRetryAnalyzer`) that automatically reruns failed tests once to rule out transient environment issues. Second, I built a centralized **Wait Factory** that standardizes all synchronization (Explicit/Fluent waits) across the framework, ensuring we don't rely on brittle `Thread.sleep()` calls."

**Q: "How do you perform 'Back-end' validation in your automation?"**
> "I developed a native **DB Utility** using JDBC. This allows us to query our **MySQL or PostgreSQL** databases directly within a test. For instance, after creating a booking via the UI, the script automatically queries the database to verify the record was correctly saved with the expected values."

**Q: "Can your framework detect UI pixel-level glitches or accessibility issues?"**
> "Yes, I integrated **AShot** for visual regression, enabling us to compare live UI states against 'golden' baseline images. Additionally, I integrated **Axe-core** to perform automated accessibility audits (WCAG compliance) during every UI test run, ensuring the application remains inclusive."

**Q: "How do you handle complex API request bodies efficiently?"**
> "Instead of using messy Maps or hardcoded JSON strings, I use **Lombok-driven POJO models**. This makes our payloads type-safe and our code much cleaner. I also implemented a **Global Filter** in Rest Assured that automatically logs all request and response details into our reporting system without any additional code in the test scripts."

**Q: "Have you implemented any security testing in your functional suite?"**
> "I integrated **OWASP ZAP** as a proxy. This allows our functional tests to pass traffic through a security scanner, enabling us to identify passive vulnerabilities like XSS or missing security headers as part of our regular CI/CD execution."

**Q: "How do you scale your tests for high-volume cross-browser execution?"**
> "We use **Dockerized Selenium Grid** for local scaling. For broader coverage, I've configured the framework to run on **BrowserStack**, allowing us to test across hundreds of real mobile and desktop browser combinations in the cloud with a simple configuration change."