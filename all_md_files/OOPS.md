# Object-Oriented Programming (OOP) Concepts in This Project

## Table of Contents
1. [Encapsulation](#1-encapsulation)
2. [Inheritance](#2-inheritance)
3. [Polymorphism](#3-polymorphism)
4. [Abstraction](#4-abstraction)
5. [Additional OOP Principles](#5-additional-oop-principles)
6. [Interview Tips](#6-interview-tips)

---

## 1. Encapsulation

### Definition
Encapsulation is the bundling of data (variables) and methods that operate on that data within a single unit (class), while restricting direct access to some of the object's components.

### Implementation in Our Project

#### Example 1: ScenarioContext Class
**File**: `src/test/java/context/ScenarioContext.java`

```java
public class ScenarioContext {
    private Response response;      // Private fields
    private int bookingId;
    private String token;

    public Response getResponse() {  // Public getter
        return response;
    }

    public void setResponse(Response response) {  // Public setter
        this.response = response;
    }
    
    // Similar getters/setters for bookingId and token
}
```

**Why This is Encapsulation:**
- Private fields (`response`, `bookingId`, `token`) cannot be accessed directly from outside the class
- Public getter/setter methods provide controlled access
- Data integrity is maintained - you can add validation in setters if needed

#### Example 2: BasePage Class
**File**: `src/main/java/base/BasePage.java`

```java
public class BasePage {
    protected WebDriver driver;      // Protected - accessible to child classes
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }
}
```

**Why This is Encapsulation:**
- `driver` and `wait` are protected, not public
- Complex logic (initialization, waiting) is hidden inside methods
- Users of BasePage don't need to know implementation details

### Interview Explanation

**Question**: "Explain encapsulation with an example from your framework."

**Answer**: 
> "In my framework, I've implemented encapsulation extensively. For example, in the `ScenarioContext` class, I store test data like API responses, booking IDs, and authentication tokens. All these fields are declared as **private**, which means they cannot be accessed directly from outside the class. 
>
> To interact with these fields, I provide **public getter and setter methods**. This approach gives me several benefits:
> 1. **Data Protection**: No external class can accidentally modify the data
> 2. **Validation**: I can add validation logic in setters if needed
> 3. **Flexibility**: I can change internal implementation without affecting other classes
> 4. **Maintainability**: Changes are localized to one class
>
> For instance, if tomorrow I need to log every time a booking ID is set, I just modify the `setBookingId()` method without touching any other code."

---

## 2. Inheritance

### Definition
Inheritance is a mechanism where a new class (child/subclass) derives properties and behaviors from an existing class (parent/superclass).

### Implementation in Our Project

#### Example 1: Page Object Model Hierarchy
**File**: `src/main/java/pages/LoginPage.java`, `HomePage.java`, etc.

```java
// Parent Class
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }
}

// Child Class
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    public void login(String username, String password) {
        sendKeys(usernameField, username);  // Inherited method
        sendKeys(passwordField, password);   // Inherited method
        click(loginButton);                  // Inherited method
    }
}

// Another Child Class
public class HomePage extends BasePage {
    @FindBy(css = "img.hotel-logoUrl")
    private WebElement logo;

    public void submitContactForm(String name, String email, String phone, String subject, String message) {
        sendKeys(contactName, name);        // Inherited method
        sendKeys(contactEmail, email);      // Inherited method
        click(submitContactButton);         // Inherited method
    }
}
```

**Benefits:**
- Code reusability: `click()`, `sendKeys()`, `waitForVisibility()` written once, used everywhere
- All page classes inherit `driver` and `wait` objects
- Common functionality centralized in BasePage

#### Example 2: Step Definition Classes
**File**: `src/test/java/stepdefinitions/StepDefs.java`, `AdvancedStepDefs.java`

All step definition classes can inherit from a common base if needed, or use dependency injection (PicoContainer) to share context.

### Interview Explanation

**Question**: "How have you used inheritance in your automation framework?"

**Answer**:
> "I've implemented inheritance in my Page Object Model design. I created a `BasePage` class that contains all common WebDriver operations like `click()`, `sendKeys()`, `getText()`, and `waitForVisibility()`. 
>
> Then, all my page classes like `LoginPage`, `HomePage`, `BookingPage`, and `AdminPage` **extend** this `BasePage` class. This means:
> 1. **Code Reusability**: I don't have to rewrite click or sendKeys logic in every page class
> 2. **Consistency**: All pages use the same waiting strategy and interaction patterns
> 3. **Maintainability**: If I need to change how clicks work (e.g., add JavaScript click fallback), I update it once in BasePage
> 4. **IS-A Relationship**: LoginPage IS-A BasePage, which makes logical sense
>
> For example, in `LoginPage`, I can directly call `sendKeys(usernameField, username)` without implementing it, because it's inherited from `BasePage`. This follows the DRY (Don't Repeat Yourself) principle."

---

## 3. Polymorphism

### Definition
Polymorphism means "many forms" - the ability of objects to take on multiple forms. It allows methods to do different things based on the object calling them.

### Types of Polymorphism

#### A. Method Overloading (Compile-time Polymorphism)

**Example 1: RequestBuilder Class**
**File**: `src/main/java/api/RequestBuilder.java`

```java
public class RequestBuilder {
    
    // Method 1: POST with just body
    public static Response postRequest(String endpoint, Object body) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().contentType("application/json").body(body).when().post(endpoint);
    }

    // Method 2: POST with body AND custom headers (Overloaded)
    public static Response postRequest(String endpoint, Object body, Map<String, String> headers) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().headers(headers).contentType("application/json").body(body).when().post(endpoint);
    }
}
```

**Why This is Polymorphism:**
- Same method name `postRequest`
- Different parameters (different signature)
- Compiler decides which method to call based on arguments

**Example 2: ResponseValidator Class**
**File**: `src/main/java/api/ResponseValidator.java`

```java
public class ResponseValidator {
    
    // Validate single status code
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    // Validate either of two status codes (Overloaded)
    public static void validateStatusCodeEither(Response response, int s1, int s2) {
        int actual = response.getStatusCode();
        Assert.assertTrue(actual == s1 || actual == s2);
    }
}
```

#### B. Method Overriding (Runtime Polymorphism)

**Example: Selenium WebDriver Interface**

While not directly in our code, we use this concept:

```java
// In DriverFactory
WebDriver driver;

if (browser.equalsIgnoreCase("chrome")) {
    driver = new ChromeDriver(options);  // ChromeDriver overrides WebDriver methods
} else if (browser.equalsIgnoreCase("firefox")) {
    driver = new FirefoxDriver(options); // FirefoxDriver overrides WebDriver methods
}

driver.get(url);  // Same method call, different implementation based on browser
```

**Example in Our Code: BasePage Methods**

```java
public class BasePage {
    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }
}

// Child class can override if needed
public class LoginPage extends BasePage {
    @Override
    public void click(WebElement element) {
        // Custom click logic for login page if needed
        super.click(element);  // Call parent method
        LoggerUtil.info("Clicked on login page element");
    }
}
```

### Interview Explanation

**Question**: "Explain polymorphism with examples from your framework."

**Answer**:
> "I've used both types of polymorphism in my framework:
>
> **1. Method Overloading (Compile-time Polymorphism):**
> In my `RequestBuilder` class, I have overloaded the `postRequest()` method:
> - One version takes just endpoint and body
> - Another version takes endpoint, body, AND custom headers
> 
> This allows me to call the same method name with different parameters based on my needs. The compiler decides which version to use at compile time.
>
> **2. Method Overriding (Runtime Polymorphism):**
> I use this with Selenium's WebDriver interface. I declare a `WebDriver` reference, but at runtime, it can be a `ChromeDriver`, `FirefoxDriver`, or `RemoteWebDriver` object. When I call `driver.get(url)`, the actual implementation depends on which driver object is instantiated. This is decided at runtime.
>
> The benefit is flexibility - I can switch browsers without changing my test code, just by changing the driver initialization."

---

## 4. Abstraction

### Definition
Abstraction is hiding complex implementation details and showing only essential features. It focuses on WHAT an object does rather than HOW it does it.

### Implementation in Our Project

#### Example 1: Utility Classes as Abstraction Layers

**File**: `src/main/java/utils/Selenium4FeaturesUtil.java`

```java
public class Selenium4FeaturesUtil {
    
    // Abstract away complex CDP (Chrome DevTools Protocol) logic
    public static void enableNetworkInterception(WebDriver driver) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            
            devTools.addListener(Network.requestWillBeSent(), request -> {
                LoggerUtil.info("Request URL: " + request.getRequest().getUrl());
            });
        }
    }
    
    // Simple method call hides complex geolocation mocking
    public static void mockGeolocation(WebDriver driver, double latitude, double longitude, int accuracy) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Emulation.setGeolocationOverride(
                    Optional.of(latitude),
                    Optional.of(longitude),
                    Optional.of(accuracy)));
        }
    }
}
```

**Why This is Abstraction:**
- Test engineers don't need to know how DevTools works
- They just call `mockGeolocation(driver, 40.7128, -74.0060, 100)`
- Complex CDP protocol details are hidden

#### Example 2: RequestBuilder Class

**File**: `src/main/java/api/RequestBuilder.java`

```java
public class RequestBuilder {
    
    public static Response postRequest(String endpoint, Object body) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().contentType("application/json").body(body).when().post(endpoint);
    }
    
    public static Response deleteRequest(String endpoint, String token) {
        LoggerUtil.info("Sending DELETE request to: " + endpoint);
        return given().cookie("token", token).when().delete(endpoint);
    }
}
```

**Why This is Abstraction:**
- Step definitions don't need to know RestAssured syntax
- They just call `RequestBuilder.postRequest("/booking", body)`
- Implementation details (headers, content-type, logging) are abstracted away

#### Example 3: DriverFactory

**File**: `src/main/java/base/DriverFactory.java`

```java
public class DriverFactory {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = ConfigReader.getBrowser();
        // Complex logic for local, grid, BrowserStack
        // All hidden from test classes
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }
}
```

**Why This is Abstraction:**
- Tests just call `DriverFactory.getDriver()`
- They don't care if it's local Chrome, Selenium Grid, or BrowserStack
- All complexity is hidden

### Interview Explanation

**Question**: "How have you implemented abstraction in your framework?"

**Answer**:
> "Abstraction is a core principle in my framework design. I've created several abstraction layers:
>
> **1. RequestBuilder Class:**
> Instead of writing RestAssured syntax in every step definition, I created a `RequestBuilder` utility class. Test engineers just call simple methods like `postRequest(endpoint, body)` or `deleteRequest(endpoint, token)`. They don't need to know about:
> - RestAssured's `given().when().then()` syntax
> - Setting content-type headers
> - Cookie handling
> - Logging implementation
>
> **2. DriverFactory:**
> My `DriverFactory` class abstracts all WebDriver initialization complexity. Tests just call `DriverFactory.getDriver()` and get a driver instance. Behind the scenes, it handles:
> - Browser selection (Chrome, Firefox)
> - Local vs Grid vs BrowserStack execution
> - Thread-safety with ThreadLocal
> - Options and capabilities
>
> **3. Selenium4FeaturesUtil:**
> For advanced Selenium 4 features like Chrome DevTools Protocol, I created utility methods. Instead of writing 10 lines of CDP code, testers call `mockGeolocation(driver, lat, lon, accuracy)`.
>
> The benefit is that my framework is easy to use - team members can write tests without knowing the underlying complexity. This follows the principle of 'hiding complexity, exposing simplicity'."

---

## 5. Additional OOP Principles

### A. Static Methods and Variables

**Example: Utility Classes**

```java
public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void info(String message) {
        logger.info(message);
    }
}

public class FakerUtil {
    private static final Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }
}
```

**Why Static:**
- No need to create objects: `LoggerUtil.info("message")` instead of `new LoggerUtil().info("message")`
- Shared across all instances
- Utility methods don't need instance state

### B. Dependency Injection (via PicoContainer)

**Example: Step Definitions**

```java
public class StepDefs {
    private final ScenarioContext context;

    public StepDefs(ScenarioContext context) {  // Constructor injection
        this.context = context;
    }

    @Given("I create a booking...")
    public void createBooking(...) {
        context.setResponse(RequestBuilder.postRequest("/booking", body));
        context.setBookingId(context.getResponse().jsonPath().getInt("bookingid"));
    }
}

public class AdvancedStepDefs {
    private final ScenarioContext context;

    public AdvancedStepDefs(ScenarioContext context) {  // Same context injected
        this.context = context;
    }

    @When("I retrieve the details...")
    public void getBookingDetails() {
        context.setResponse(RequestBuilder.getRequest("/booking/" + context.getBookingId()));
    }
}
```

**Why This Matters:**
- Both step definition classes share the SAME `ScenarioContext` instance
- Data flows between different step files
- PicoContainer manages object lifecycle

### C. ThreadLocal for Thread Safety

**File**: `src/main/java/base/DriverFactory.java`

```java
public class DriverFactory {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void initDriver() {
        threadLocalDriver.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }
}
```

**Why ThreadLocal:**
- Parallel test execution support
- Each thread gets its own WebDriver instance
- No conflicts between concurrent tests

### D. Composition Over Inheritance

**Example: Using Utilities Instead of Inheriting**

```java
// Instead of inheriting from a "LoggingClass"
public class StepDefs {
    public void someMethod() {
        LoggerUtil.info("Message");  // Composition - using utility
        FakerUtil.getFirstName();    // Composition - using utility
    }
}
```

**Why Composition:**
- More flexible than inheritance
- Can use multiple utilities
- Avoids deep inheritance hierarchies

---

## 6. Interview Tips

### Common Questions and How to Answer

#### Q1: "What OOP concepts have you used in your framework?"

**Answer Structure:**
1. List all four: Encapsulation, Inheritance, Polymorphism, Abstraction
2. Give ONE concrete example for each from your project
3. Explain the benefit

**Example Answer:**
> "I've implemented all four OOP pillars in my framework:
> 
> **Encapsulation**: In `ScenarioContext`, I use private fields with public getters/setters to protect test data.
> 
> **Inheritance**: My Page Object Model uses `BasePage` as parent class, with `LoginPage`, `HomePage` etc. as children inheriting common methods.
> 
> **Polymorphism**: I've overloaded `postRequest()` method in `RequestBuilder` to accept different parameter combinations.
> 
> **Abstraction**: My `DriverFactory` hides all WebDriver initialization complexity behind simple methods like `getDriver()`.
> 
> These principles make my framework maintainable, reusable, and easy to extend."

#### Q2: "Explain the difference between Abstraction and Encapsulation."

**Answer:**
> "Both hide complexity, but with different goals:
> 
> **Encapsulation** is about **data protection** - hiding data using private fields and providing controlled access via getters/setters. Example: `ScenarioContext` with private `response`, `bookingId` fields.
> 
> **Abstraction** is about **hiding implementation complexity** - showing only essential features. Example: `RequestBuilder.postRequest()` hides RestAssured syntax complexity.
> 
> Think of a car: Encapsulation is like the locked hood protecting the engine. Abstraction is like the steering wheel - you don't need to know how the steering mechanism works, you just turn the wheel."

#### Q3: "Why did you use inheritance in Page Object Model?"

**Answer:**
> "I used inheritance for code reusability and consistency. All page classes need common operations like click, sendKeys, wait. Instead of duplicating this code in every page class, I:
> 1. Created `BasePage` with these common methods
> 2. Made all page classes extend `BasePage`
> 3. Now every page automatically has these methods
> 
> Benefits:
> - DRY principle - code written once
> - Consistency - all pages use same interaction pattern
> - Maintainability - change once in BasePage, affects all pages
> - Follows IS-A relationship - LoginPage IS-A BasePage"

#### Q4: "What is the difference between method overloading and overriding?"

**Answer:**
> "**Overloading** (Compile-time Polymorphism):
> - Same method name, different parameters
> - In the same class or parent-child
> - Example: `postRequest(endpoint, body)` and `postRequest(endpoint, body, headers)`
> - Decided at compile time
> 
> **Overriding** (Runtime Polymorphism):
> - Same method name, same parameters
> - In parent-child relationship
> - Child class provides different implementation
> - Example: `ChromeDriver.get()` vs `FirefoxDriver.get()` - both override `WebDriver.get()`
> - Decided at runtime based on object type"

#### Q5: "How do you ensure thread safety in your framework?"

**Answer:**
> "I use `ThreadLocal` in `DriverFactory`:
> ```java
> private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
> ```
> 
> This ensures each thread (parallel test execution) gets its own WebDriver instance. When Thread-1 calls `getDriver()`, it gets its own driver. Thread-2 gets a different instance. This prevents conflicts during parallel execution.
> 
> Similarly, PicoContainer creates a new `ScenarioContext` instance for each scenario, ensuring test data isolation."

### Key Points to Remember for Interviews

1. **Always give examples from YOUR project** - don't give generic textbook examples
2. **Explain the BENEFIT** - why did you use this concept? What problem did it solve?
3. **Use proper terminology** - "parent class", "child class", "method signature", "runtime vs compile-time"
4. **Be ready to write code** - interviewers may ask you to write a quick example
5. **Connect to real-world** - use analogies (car, ATM, etc.) to explain complex concepts

### Practice Scenarios

**Scenario 1**: "Show me encapsulation in your code"
- Open `ScenarioContext.java`
- Point to private fields
- Point to public getters/setters
- Explain data protection

**Scenario 2**: "Show me inheritance in your code"
- Open `BasePage.java` and `LoginPage.java`
- Show `extends` keyword
- Show inherited methods being used
- Explain code reuse

**Scenario 3**: "Show me polymorphism in your code"
- Open `RequestBuilder.java`
- Show overloaded `postRequest()` methods
- Explain different signatures
- Show how you call them differently

**Scenario 4**: "Show me abstraction in your code"
- Open `DriverFactory.java` or `RequestBuilder.java`
- Show complex implementation inside
- Show simple method call from test
- Explain hidden complexity

---

## Summary Table

| OOP Concept | Where Used | Key Benefit | Interview Keyword |
|-------------|------------|-------------|-------------------|
| **Encapsulation** | ScenarioContext, BasePage | Data Protection | Private fields + Public getters/setters |
| **Inheritance** | BasePage → LoginPage/HomePage | Code Reusability | extends, IS-A relationship |
| **Polymorphism** | RequestBuilder overloaded methods | Flexibility | Method Overloading/Overriding |
| **Abstraction** | DriverFactory, RequestBuilder | Hide Complexity | Utility classes, Simple interfaces |
| **Static** | LoggerUtil, FakerUtil | No object needed | Class-level access |
| **ThreadLocal** | DriverFactory | Thread Safety | Parallel execution |
| **Dependency Injection** | Step Definitions | Loose Coupling | PicoContainer, Constructor injection |

---

## Final Interview Pro Tips

1. **Start with the concept definition** (1 sentence)
2. **Give YOUR project example** (show code or explain)
3. **Explain the benefit** (why you used it)
4. **Be ready for follow-up questions**

Example Flow:
```
Interviewer: "Explain encapsulation"

You: "Encapsulation is bundling data and methods together while restricting direct access to data. [DEFINITION]

In my framework, I have a ScenarioContext class where I store test data like API responses and booking IDs. All fields are private, and I provide public getters and setters. [YOUR EXAMPLE]

This protects data from accidental modification and gives me control over how data is accessed and modified. [BENEFIT]

I can also add validation in setters if needed in the future without changing other code. [EXTRA BENEFIT]"
```

**Remember**: Confidence comes from understanding YOUR OWN code. Review these examples, understand WHY you used each concept, and you'll ace any OOP interview question!
