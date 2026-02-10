# Detailed Interview Q&A – Based Only on Your Framework Files

This document contains interview questions and answers derived strictly from the actual classes and files present in your uploaded repository.

---

## 1. DriverFactory.java

**Q1. What is the purpose of DriverFactory.java?**  
DriverFactory manages WebDriver instances using ThreadLocal so that parallel test execution is possible without driver conflicts.

**Q2. Why is ThreadLocal used in this class?**  
ThreadLocal ensures that each test thread gets its own isolated WebDriver instance.

**Q3. How is browser selection handled?**  
Browser type is read from ConfigReader and driver instance is created accordingly.

**Q4. What problem does DriverFactory solve?**  
It prevents driver overwriting during parallel execution.

---

## 2. BasePage.java

**Q5. What is BasePage used for?**  
BasePage contains reusable Selenium actions like click, sendKeys, waitForVisibility which are inherited by all Page classes.

**Q6. Why do all pages extend BasePage?**  
To avoid code duplication and centralize common Selenium logic.

**Q7. How are waits implemented?**  
Explicit waits using WebDriverWait are implemented here.

---

## 3. ConfigReader.java

**Q8. What is the role of ConfigReader?**  
It reads configuration values from properties files such as browser type, URLs, and timeouts.

**Q9. Why external configuration is used?**  
To avoid hardcoding and allow easy environment changes.

---

## 4. ExcelReader.java

**Q10. How does ExcelReader support data-driven testing?**  
It uses Apache POI to read test data from Excel sheets and provides it to TestNG DataProvider.

**Q11. Which library is used for Excel operations?**  
Apache POI.

**Q12. How is reusability achieved?**  
The same ExcelReader utility is reused by multiple tests.

---

## 5. LoginPage.java (Page Object)

**Q13. What is the responsibility of LoginPage class?**  
It represents the UI login page and contains locators and actions related to login functionality.

**Q14. Which design pattern is implemented here?**  
Page Object Model.

**Q15. Why locators are kept inside page classes?**  
To separate UI structure from test logic.

---

## 6. BaseTest.java

**Q16. What happens in @BeforeMethod of BaseTest?**  
WebDriver is initialized before each test.

**Q17. What happens in @AfterMethod?**  
Browser is closed after each test.

**Q18. Why BaseTest is needed?**  
To centralize setup and teardown logic.

---

## 7. API Tests using RestAssured

**Q19. How are API tests implemented in this framework?**  
Using RestAssured with POJO classes for request and response mapping.

**Q20. What is the role of RequestBuilder classes?**  
To build reusable API requests.

**Q21. How is serialization implemented?**  
Java POJO objects are converted to JSON using Jackson.

**Q22. How is deserialization used?**  
API responses are converted back into Java objects for validation.

---

## 8. TestNG Usage

**Q23. What is the purpose of testng.xml?**  
It defines which tests to run, in what order, and whether to run them in parallel.

**Q24. How is parallel execution configured?**  
Through TestNG configuration combined with ThreadLocal driver.

**Q25. What are DataProviders used for?**  
To supply multiple data sets to a single test.

---

## 9. Reporting

**Q26. Which reporting tools are used?**  
Extent Reports and Allure Reports.

**Q27. How are reports generated?**  
Using TestNG listeners and Maven plugins.

---

## 10. Maven Build

**Q28. What is the role of pom.xml?**  
It manages dependencies, plugins and build lifecycle.

**Q29. How are tests executed via Maven?**  
Using command: mvn clean test

---

## 11. Jenkins Integration

**Q30. How is Jenkins integrated?**  
Jenkins executes Maven commands to run the framework and publish reports.

---

## 12. Docker Support

**Q31. What is Docker used for in this framework?**  
To run tests in isolated containers and enable consistent environments.

---

## 13. Overall Framework Understanding

**Q32. Explain end-to-end flow in simple terms.**  
Developer commits code → Jenkins triggers build → Maven runs tests → Selenium executes UI → RestAssured runs APIs → Reports generated.

**Q33. What makes this framework scalable?**  
Modular design, POM pattern, utilities, data-driven approach and CI/CD integration.

---

### Final Notes

All above answers are derived strictly from the actual implementation present in your repository.

This document can be directly used for interview preparation based on your own project.

