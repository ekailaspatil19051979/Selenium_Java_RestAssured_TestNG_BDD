# Automation Framework Deep Dive Analysis

This document provides a detailed technical explanation of the attached automation framework built using:

**Selenium + Java + RestAssured + Maven + TestNG + Jenkins + Docker + Page Object Model + Data Driven Framework**

---

## 1. FILE LEVEL ANALYSIS

### 1.1 pom.xml
**Purpose:**  
Central Maven build file.

**Layer:** Build Configuration

**Why Needed:**  
- Manages all dependencies
- Controls plugins (Surefire, Compiler, Allure)
- Defines Java version and build lifecycle

**Interactions:**  
Used by Maven CLI, Jenkins and Docker to build project.

**Problem Solved:**  
Eliminates manual jar management.

---

### 1.2 testng.xml
**Purpose:**  
Defines TestNG suite execution.

**Layer:** Test Execution

**Features:**
- Controls which tests run
- Parallel execution
- Grouping

**Flow:**  
Maven → Surefire Plugin → testng.xml → Test Classes

---

### 1.3 src/main/java

#### a) base package
Contains core framework classes

##### BaseTest.java
- Initializes WebDriver
- Contains @BeforeMethod and @AfterMethod
- Launches and quits browser

##### DriverFactory.java
- Implements ThreadLocal WebDriver
- Enables parallel execution
- Creates browser instances

##### BasePage.java
- Common Selenium methods
- Waits, clicks, sendKeys
- Parent of all page classes

---

#### b) pages package

Example: LoginPage.java

**Purpose:**  
Implements Page Object Model

**Responsibilities:**
- Store locators
- Business actions
- Reusable UI operations

**Interaction:**  
Test Classes → Page Classes → Selenium

---

#### c) utils package

Contains reusable helpers:

- ExcelReader.java – reads test data
- JsonReader.java – handles API payloads
- LoggerUtil.java – logging support
- ScreenshotUtil.java – failure screenshots

**Purpose:**  
Avoid code duplication

---

#### d) api package

- APIBase.java – base URI setup
- APIUtils.java – RestAssured helpers
- RequestBuilder.java – builds requests

**Layer:** API Automation

---

### 1.4 src/test/java

Contains actual test cases:

- UITests
- APITests
- StepDefinitions (BDD)

**Flow:**
Test → Page → BasePage → Driver

---

## 2. LINE BY LINE EXPLANATION (Sample)

### Example: BaseTest.java

```java
@BeforeMethod
public void setup() {
    DriverFactory.initDriver();
}
```

- @BeforeMethod: TestNG annotation
- initDriver(): launches browser before every test

```java
@AfterMethod
public void tearDown() {
    DriverFactory.quitDriver();
}
```

- Closes browser after test

---

## 3. FRAMEWORK FLOW

### A. UI Automation Flow

1. Maven starts execution  
2. testng.xml triggers tests  
3. BaseTest initializes WebDriver  
4. Page Object Model is used  
5. Tests call page methods  
6. Assertions validate results  
7. Reports generated  

---

### B. API Automation Flow

1. Base URI configured  
2. Request payload created  
3. Headers/auth added  
4. API called using RestAssured  
5. Response validated  
6. Schema validation  

---

### C. Data Driven Flow

- Excel/JSON used as data source  
- TestNG @DataProvider supplies data  
- Tests executed multiple times  

---

## 4. FOLDER STRUCTURE

- **src/main/java** – Framework code  
- **src/test/java** – Test cases  
- **resources** – configs  
- **testdata** – external data  
- **reports** – execution results  

---

## 5. MAVEN EXPLANATION

- pom.xml controls lifecycle  
- mvn clean install  
- surefire executes TestNG  

---

## 6. TESTNG EXPLANATION

- testng.xml drives execution  
- supports:
  - parallel
  - groups
  - priorities
  - listeners  

---

## 7. JENKINS INTEGRATION

- Jenkins pulls code  
- Runs:
  mvn clean test
- Generates Allure/Extent reports  

---

## 8. DOCKER INTEGRATION

- Dockerfile builds environment  
- Tests executed inside container  
- Supports Selenium Grid  

---

## 9. DESIGN PATTERNS

- Page Object Model  
- Factory Pattern  
- Utility Pattern  

Benefits:
- Reusability  
- Maintainability  
- Scalability  

---

## 10. END TO END FLOW

Developer commit  
→ Jenkins build  
→ Maven execution  
→ Docker container  
→ TestNG run  
→ UI/API tests  
→ Reports generated  

---

### Final Summary

This framework is:

✔ Modular  
✔ Scalable  
✔ Data Driven  
✔ CI/CD Enabled  
✔ Docker Ready  
✔ Industry Standard  

---

Prepared as a technical deep dive document for interview and knowledge sharing.
