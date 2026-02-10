
🔥 MOCK INTERVIEW – Based on YOUR Framework

I will divide this into sections so you can prepare systematically.

🟢 SECTION 1 – FRAMEWORK OVERVIEW
Q1. Explain your automation framework architecture.

Model Answer:

“Our project uses a hybrid automation framework built with Selenium, TestNG, RestAssured and Cucumber.
It follows Page Object Model design pattern with separate layers:

Test Layer (TestNG/Cucumber tests)

Page Layer (POM classes)

Utility Layer (ExcelReader, ConfigReader, BiDi utilities)

Core Layer (DriverFactory, BasePage)

Reporting Layer (Allure & Extent)

The framework supports UI automation, API automation and BDD in a single codebase.”

Q2. How do you manage browser drivers?

Answer:

“We use a custom DriverFactory with ThreadLocal WebDriver to support parallel execution.
Browser type and headless mode are controlled through config.properties.
It supports local execution, Selenium Grid and BrowserStack.”

Q3. What design patterns did you use?

“Main patterns used are:

Page Object Model

Factory Pattern (DriverFactory)

Builder Pattern (Lombok POJOs)

Singleton (ConfigReader)

Data Driven Pattern (Excel/JSON)”

🟢 SECTION 2 – API AUTOMATION
Q4. How did you implement API automation?

“We used RestAssured for API automation.
API request payloads are created using POJO classes with Lombok annotations.
We implemented:

Serialization / Deserialization

Schema Validation

Token-based authentication

Data-driven tests from Excel and JSON”

Q5. What is Serialization and Deserialization in your project?

“Serialization means converting Java POJO objects into JSON request body.
Deserialization means converting API JSON response back into Java objects.

Example: Booking.java and BookingDates.java are serialized into JSON while creating booking API request.”

Q6. How did you validate API responses?

“We used three levels of validation:

Status code validation

Field level assertions

JSON Schema validation using rest-assured json-schema-validator”

Q7. How did you implement Data Driven API tests?

“We implemented:

Excel driven tests using Apache POI

External JSON file driven tests

TestNG DataProvider

Cucumber Examples table”

🟢 SECTION 3 – BROWSER AUTOMATION
Q8. Explain your Page Object Model implementation.

“Every page has:

Locators defined using @FindBy

Reusable methods

Inherited from BasePage

BasePage handles waits, actions and driver initialization.”

Q9. How do you handle waits?

“Explicit waits using WebDriverWait are implemented in BasePage.
All actions like click(), sendKeys() internally wait for visibility before performing actions.”

Q10. How do you handle parallel execution?

“Using:

ThreadLocal WebDriver

TestNG parallel execution

DriverFactory to isolate browser instances”

🟢 SECTION 4 – SELENIUM BiDi (Very Important)
Q11. What new changes did you recently implement in the framework?

This is YOUR STAR ANSWER:

“Recently we modernized the framework by replacing Chrome DevTools Protocol (CDP) with Selenium BiDi.

Earlier CDP was Chrome-specific and required matching selenium-devtools versions.
Whenever Chrome updated, our tests broke.

We migrated to Selenium BiDi which is browser-agnostic and W3C standard.”

Q12. What did you implement using BiDi?

“I implemented a BiDiConsoleLogger utility that captures:

Browser console logs

JavaScript errors

Runtime warnings

These logs are attached to reports for better debugging.”

Q13. Why is BiDi better than CDP?

“BiDi is:

Browser independent

Version independent

Standardized

Event-driven

CDP was Chrome-only and tightly coupled to versions.”

Q14. Give a practical use case of BiDi in your project.

“During regression runs, if any UI page throws JavaScript errors, Selenium BiDi captures them automatically.
This helped us detect UI bugs that normal Selenium assertions could not catch.”

🟢 SECTION 5 – BUILD & DEBUGGING
Q15. What build tool are you using?

“Maven is used as build tool with:

pom.xml for dependency management

Surefire plugin for execution

Allure plugin for reporting”

Q16. What challenges did you face recently?

(Use this real scenario!)

“We faced compilation issues due to:

selenium-devtools version mismatch

Lombok annotation processing

Java 17 compatibility

We debugged them by:

Removing CDP dependencies

Enabling annotation processing

Switching to Selenium BiDi

Cleaning and updating Maven configuration”

Q17. How do you run only specific tests?

“Using:

TestNG XML files

mvn test -DsuiteXmlFile=newTests.xml

Cucumber tags

Maven profiles”

🟢 SECTION 6 – REPORTING
Q18. What reporting mechanism do you use?

“We use:

Allure Reports

Extent Reports

Both integrated with TestNG listeners and screenshots on failure.”

Q19. How do you generate reports?

“Using:
mvn clean test
allure serve target/allure-results”

🟢 SECTION 7 – SCENARIO BASED QUESTIONS
Q20. Suppose Chrome updates tomorrow, will your framework break?

Perfect answer because of BiDi:

“No.
Since we moved from CDP to Selenium BiDi, our framework is no longer dependent on Chrome versions.
It will continue to work without any pom.xml changes.”

Q21. How would you test an API and UI together?

“We combine both:

Create data using API

Validate in UI

Delete via API

This gives faster and reliable automation.”

🟢 SECTION 8 – ADVANCED QUESTIONS
Q22. How do you maintain test data?

“Using:

Excel files

JSON files

Faker library

Config properties”

Q23. How do you ensure framework scalability?

“Through:

Modular design

Reusable utilities

Parallel execution

BiDi integration

CI/CD support”

🎯 FINAL INTERVIEW TIP

If they ask:

👉 “What is something unique in your framework?”

Your best answer:

“The unique part of our framework is Selenium BiDi integration.
We migrated from Chrome DevTools to BiDi to make the framework browser-independent and stable.
I implemented real-time console log capturing which improved debugging and reliability.”





🔥 100 FOLLOW-UP INTERVIEW QUESTIONS (WITH ANSWERS)
🟢 FRAMEWORK & ARCHITECTURE (1–20)

1. Why did you choose Selenium + TestNG for UI automation?
Because Selenium is open-source, widely used and TestNG provides annotations, parallel execution and reporting support.

2. Why not JUnit instead of TestNG?
TestNG supports parallel execution, data providers and better suite management compared to JUnit.

3. What is ThreadLocal WebDriver?
It allows each parallel thread to have its own WebDriver instance, avoiding conflicts.

4. How do you achieve parallel execution?
Using TestNG parallel mode with ThreadLocal WebDriver in DriverFactory.

5. What is DriverFactory?
A central class that initializes and manages WebDriver instances.

6. Why Page Object Model?
To separate test logic from UI locators and improve maintainability.

7. What is BasePage?
A parent class containing common methods like wait, click, sendKeys.

8. How do you manage configurations?
Through config.properties and ConfigReader utility.

9. How do you handle browser selection?
Via property file and system parameters.

10. How do you manage different environments?
Using environment-specific config files.

11. How do you handle test data?
Excel, JSON, and Faker library.

12. What logging framework did you use?
Log4j2.

13. How do you capture screenshots?
Using Selenium TakesScreenshot on failure.

14. How do you manage waits?
Explicit waits in BasePage.

15. What is FluentWait?
A customizable wait with polling intervals.

16. How do you handle timeouts?
Configured via ConfigReader.

17. How do you run tests in CI/CD?
Using Maven commands in Jenkins pipeline.

18. What reports do you use?
Allure and Extent Reports.

19. How do you handle test retries?
Using TestNG retry analyzer.

20. How do you ensure reusability?
By creating utilities and reusable page methods.

🟢 API AUTOMATION (21–40)

21. Why RestAssured?
Easy syntax, BDD style, strong validation support.

22. What is POJO in API testing?
Java classes representing request/response models.

23. What is serialization?
Converting Java object to JSON.

24. What is deserialization?
Converting JSON response to Java object.

25. Why use Lombok in POJOs?
To auto-generate getters/setters and reduce boilerplate.

26. How do you validate API responses?
Assertions, JSON schema, deserialization.

27. What is JSON schema validation?
Validating response structure against predefined schema.

28. How do you pass authentication?
Using token-based auth in headers.

29. How do you generate token?
By calling auth API and extracting token.

30. What is DataProvider in TestNG?
Method to pass multiple data sets.

31. How did you do Excel-driven API tests?
Using Apache POI ExcelReader utility.

32. How do you read external JSON for tests?
Using Jackson ObjectMapper.

33. How do you chain APIs?
Extract response value and pass to next API.

34. How do you validate dynamic responses?
By deserializing and asserting fields.

35. What is given-when-then in RestAssured?
BDD style syntax.

36. How do you log API requests?
Using RestAssured logging filters.

37. How do you handle cookies in APIs?
Using .cookie() method.

38. How do you handle headers?
Using .headers() method.

39. How do you handle query params?
Using .queryParam().

40. How do you handle multipart APIs?
Using multiPart() in RestAssured.

🟢 SELENIUM BiDi & DEVTOOLS (41–60)

41. What is Selenium BiDi?
A bidirectional protocol for advanced browser automation.

42. Why move from CDP to BiDi?
CDP is Chrome-only; BiDi is cross-browser.

43. What problem did BiDi solve in your project?
Version mismatch issues with selenium-devtools.

44. What features did you implement using BiDi?
Console log and JS error capturing.

45. How do you capture console logs?
Using LogInspector from Selenium BiDi.

46. Is BiDi Chrome-specific?
No, it is browser-independent.

47. Why not use CDP now?
CDP breaks with Chrome updates.

48. Does BiDi require extra dependency?
No separate version-specific jars.

49. What real benefit did BiDi give?
Better debugging and stability.

50. How do you attach BiDi logs to reports?
By capturing and logging in listeners.

51. What is event-driven automation?
Listening to browser events in real time.

52. Can BiDi capture network calls?
Yes, network interception is supported.

53. Is BiDi W3C standard?
Yes.

54. Which browsers support BiDi?
Chrome, Firefox, Edge.

55. Does Playwright use BiDi?
Similar concept – event based automation.

56. How did you debug BiDi setup issues?
By removing selenium-devtools dependency.

57. What utility did you create for BiDi?
BiDiConsoleLogger.

58. Can BiDi replace CDP fully?
Gradually yes.

59. Is BiDi mandatory?
Only for advanced features.

60. How would you explain BiDi in interview?
“Cross-browser DevTools replacement.”

🟢 DEBUGGING & BUILD (61–80)

61. What build tool do you use?
Maven.

62. What plugins are used?
Compiler, Surefire, Allure.

63. What is mvn clean install?
Cleans and builds project with tests.

64. How to run specific suite?
mvn test -DsuiteXmlFile=testng.xml

65. How to run specific tests?
Using TestNG groups or tags.

66. How to debug Maven issues?
Using mvn -X for debug logs.

67. What is annotation processing issue?
Lombok getters not generated.

68. How did you fix Lombok issues?
Enabled annotation processing.

69. What is dependency conflict?
Multiple versions of same library.

70. How to fix dependency issues?
Using dependencyManagement.

71. What is Java PATH issue?
java command not recognized.

72. How to resolve PATH issue?
Add JAVA_HOME/bin to system PATH.

73. How to debug compilation errors?
Check imports and dependencies.

74. What is maven clean?
Deletes target folder.

75. How to skip tests?
mvn clean install -DskipTests

76. How to update dependencies?
mvn clean install -U

77. What is POM?
Project Object Model.

78. How to manage versions?
Using properties section.

79. How to add new library?
Add dependency in pom.xml

80. What is artifactId?
Project/module name.

🟢 SCENARIO BASED (81–100)

81. How to test flaky tests?
Add retries and proper waits.

82. How to handle dynamic elements?
Using waits and dynamic locators.

83. How to test API + UI together?
Create data via API, verify in UI.

84. How to handle popups?
Switch to alert or window.

85. How to handle multiple windows?
Using getWindowHandles().

86. How to test file upload?
sendKeys(filePath).

87. How to test downloads?
Verify downloaded file.

88. How to test in headless mode?
Using ChromeOptions.

89. How to run in Selenium Grid?
RemoteWebDriver with grid URL.

90. How to integrate with Jenkins?
Using Maven job.

91. How to capture failures?
Screenshots + logs.

92. How to validate PDFs?
Using PDF parsing libraries.

93. How to validate emails?
Using mailbox APIs.

94. How to mock APIs?
Using WireMock.

95. How to improve execution speed?
Parallel execution.

96. How to handle test data cleanup?
Delete via APIs.

97. How to secure credentials?
Using environment variables.

98. How to handle OTP login?
API or DB verification.

99. How to ensure framework scalability?
Modular design and utilities.

100. What was your biggest contribution?

“Migrated DevTools CDP to Selenium BiDi, implemented Excel-driven and schema validations, improved framework stability and reporting.”