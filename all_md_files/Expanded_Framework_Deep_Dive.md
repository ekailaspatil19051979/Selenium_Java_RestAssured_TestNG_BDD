# EXTENDED FRAMEWORK DEEP DIVE ANALYSIS

This document contains class-by-class explanation with real code snippets from the uploaded repository.


## File: Selenium_Java_RestAssured_TestNG_BDD-main/pom.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.automation</groupId>
  <artifactId>enterprise-framework</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>Enterprise Automation Framework</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <selenium.version>4.16.1</selenium.version>
    <rest.assured.version>5.4.0</rest.assured.version>
    <cucumber.version>7.15.0</cucumber.version>
    <testng.version>7.9.0</testng.version>
    <poi.version>5.2.5</poi.version>
    <log4j.version>2.22.1</log4j.version>
    <extent.version>5.1.1</extent.version>
    <allure.version>2.25.0</allure.version>
    <aspectj.version>1.9.21</aspectj.version>
  </properties>

  <dependencies>
    <!-- Selenium -->
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>${selenium.version}</version>
    </dependency>

    <!-- Rest Assured -->
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>${rest.assured.version}</version>
    </depend

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/testng.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Enterprise Automation Suite" parallel="tests" thread-count="4">
    <listeners>
        <listener class-name="io.qameta.allure.testng.AllureTestNg"/>
        <listener class-name="utils.AnnotationTransformer"/>
    </listeners>
    
    <test name="BDD Tests">
        <classes>
            <class name="runners.TestRunner"/>
        </classes>
    </test>
</suite>


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/.allure/allure-2.25.0/lib/config/jetty-logging.properties

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

org.eclipse.jetty.util.log.class=org.eclipse.jetty.util.log.LoggerLog
org.eclipse.jetty.LEVEL=WARN

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/.allure/allure-2.25.0/lib/config/logback.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%msg%n</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="stdout"/>
    </root>
    <logger name="org.mortbay.log" level="INFO"/>
</configuration>


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/api/CustomLogFilter.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import utils.LoggerUtil;

public class CustomLogFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
            FilterContext ctx) {

        LoggerUtil.info("--- REQUEST DETAILS ---");
        LoggerUtil.info("Method: " + requestSpec.getMethod());
        LoggerUtil.info("URI: " + requestSpec.getURI());
        if (requestSpec.getBody() != null) {
            LoggerUtil.info("Body: " + requestSpec.getBody().toString());
        }

        Response response = ctx.next(requestSpec, responseSpec);

        LoggerUtil.info("--- RESPONSE DETAILS ---");
        LoggerUtil.info("Status: " + response.getStatusCode());
        LoggerUtil.info("Response Body: " + response.asPrettyString());

        return response;
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/api/RequestBuilder.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.LoggerUtil;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestBuilder {

    static {
        RestAssured.filters(new CustomLogFilter());
    }

    public static Response getRequest(String endpoint) {
        LoggerUtil.info("Sending GET request to: " + endpoint);
        return given().when().get(endpoint);
    }

    public static Response postRequest(String endpoint, Object body) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().contentType("application/json").body(body).when().post(endpoint);
    }

    public static Response postRequest(String endpoint, Object body, Map<String, String> headers) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().headers(headers).contentType("application/json").body(body).when().post(endpoint);
    }

    public static Response putRequest(String endpoint, Object body, String token) {
        LoggerUtil.info("Sending PUT request to: " + endpoint);
        return given().cookie("token", token).contentType("application/json").body(body).when().put(endpoint);
    }

    public static Response deleteRequest(String endpoint, String token) {
        LoggerUtil.info("Sending DELETE request to: " + endpoint);
        return given().cookie("token", token).when().delete(endpoint);
    }

    public static Response patchRequest(S

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/api/ResponseValidator.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package api;

import io.restassured.response.Response;
import org.testng.Assert;
import utils.LoggerUtil;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertNotNull(response, "Response is NULL! Request might have failed.");
        LoggerUtil.info("Validating Status Code: " + expectedStatusCode);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    public static void validateStatusCodeEither(Response response, int s1, int s2) {
        Assert.assertNotNull(response, "Response is NULL! Request might have failed.");
        int actual = response.getStatusCode();
        LoggerUtil.info("Validating Status Code: " + s1 + " or " + s2 + " (Actual: " + actual + ")");
        Assert.assertTrue(actual == s1 || actual == s2, "Expected " + s1 + " or " + s2 + " but found " + actual);
    }

    public static void validateResponseTime(Response response, long expectedTime) {
        LoggerUtil.info("Validating Response Time < " + expectedTime);
        Assert.assertTrue(response.getTime() < expectedTime, "Response time exceeded");
    }

    public static void validateSchema(Response response, String schemaPath) {
        LoggerUtil.info("Validating JSON Schema: " + schemaPath);
        response.then().assertThat()
                .body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    public static void validateField

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/base/APIBase.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import config.ConfigReader;

public class APIBase {
    protected static RequestSpecification requestSpec;

    public static void setup() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/base/BasePage.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package base;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.ConfigReader;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        // Driver might not be initialized at object creation time
        this.driver = DriverFactory.getDriver();

        if (this.driver != null) {
            this.wait = new WebDriverWait(driver,
                    Duration.ofSeconds(ConfigReader.getExplicitWait()));

            PageFactory.initElements(driver, this);
        }
    }

    private WebDriverWait getWait() {
        if (wait == null) {
            this.driver = DriverFactory.getDriver();

            if (driver != null) {
                wait = new WebDriverWait(driver,
                        Duration.ofSeconds(ConfigReader.getExplicitWait()));
            }
        }
        return wait;
    }

    public void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.cl

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/base/BaseTest.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import config.ConfigReader;
import utils.LoggerUtil;

public class BaseTest {

    @BeforeSuite
    public void globalSetup() {
        LoggerUtil.info("Starting Test Execution Suite");
        APIBase.setup();
    }

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        LoggerUtil.info("Browser initialized: " + ConfigReader.getBrowser());
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        LoggerUtil.info("Browser closed");
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/base/DriverFactory.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void initDriver() {

        // If driver already exists for this thread, do not re-create
        if (threadLocalDriver.get() != null) {
            return;
        }

        String browser = ConfigReader.getBrowser();
        String gridUrl = System.getProperty("grid.url");

        try {
            if (gridUrl != null && !gridUrl.isEmpty()) {

                if (browser.equalsIgnoreCase("chrome")) {

                    ChromeOptions options = new ChromeOptions();

                    if (ConfigReader.isHeadless()) {
                        options.addArguments("--headless");
                    }

                    threadLocalDriver.set(
                            new RemoteWebDriver(new URL(gridUrl), options));

                } else if (browser.equalsIgnoreCase("firefox")) {

                    FirefoxOptions options = new FirefoxOptions();

                    if (ConfigReader.isHeadless()) {
                        options.addArguments("--headle

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/config/ConfigReader.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("baseurl");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/models/Booking.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/models/BookingDates.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDates {
    private String checkin;
    private String checkout;
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/pages/AdminPage.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.components.NavBar;

public class AdminPage extends BasePage {

    // Composition: AdminPage has a NavBar
    public NavBar navBar;

    @FindBy(id = "roomNumber")
    private WebElement roomNumberInput;

    @FindBy(id = "roomPrice")
    private WebElement roomPriceInput;

    @FindBy(id = "createRoom")
    private WebElement createRoomButton;

    @FindBy(xpath = "//div[@class='room-listing']")
    private WebElement lastRoomListing;

    public AdminPage() {
        super();
        this.navBar = new NavBar();
    }

    public void createRoom(String number, String price) {
        sendKeys(roomNumberInput, number);
        sendKeys(roomPriceInput, price);
        click(createRoomButton);
    }

    public boolean isRoomDisplayed(String roomNumber) {
        // Implementation would check the list of rooms for the specific number
        // This is a placeholder for the logic
        return true;
    }

    public void logout() {
        navBar.logout();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/pages/BookingPage.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class BookingPage extends BasePage {

    @FindBy(id = "bookingId")
    private WebElement bookingIdField;

    @FindBy(className = "room-listing")
    private List<WebElement> roomListings;

    @FindBy(className = "book-room")
    private WebElement openBookingFormButton;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(name = "roomid")
    private WebElement roomSelect;

    @FindBy(id = "depositpaid")
    private WebElement depositPaidSelect;

    @FindBy(id = "checkin")
    private WebElement checkinInput;

    @FindBy(id = "checkout")
    private WebElement checkoutInput;

    @FindBy(css = ".btn-outline-primary.book-room")
    private WebElement bookButton;

    public void createBooking(String fname, String lname, String checkin, String checkout) {
        click(openBookingFormButton);
        sendKeys(firstNameInput, fname);
        sendKeys(lastNameInput, lname);
        sendKeys(checkinInput, checkin);
        sendKeys(checkoutInput, checkout);
        click(bookButton);
    }

    public int getRoomCount() {
        return roomListings.size();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/pages/HomePage.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "img.hotel-logoUrl")
    private WebElement logo;

    @FindBy(xpath = "//button[text()='Book this room']")
    private WebElement bookRoomButton;

    @FindBy(name = "name")
    private WebElement contactName;

    @FindBy(name = "email")
    private WebElement contactEmail;

    @FindBy(name = "phone")
    private WebElement contactPhone;

    @FindBy(name = "subject")
    private WebElement contactSubject;

    @FindBy(name = "description")
    private WebElement contactDescription;

    @FindBy(id = "submitContact")
    private WebElement submitContactButton;

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public void submitContactForm(String name, String email, String phone, String subject, String message) {
        sendKeys(contactName, name);
        sendKeys(contactEmail, email);
        sendKeys(contactPhone, phone);
        sendKeys(contactSubject, subject);
        sendKeys(contactDescription, message);
        click(submitContactButton);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/pages/LoginPage.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "doLogin")
    private WebElement loginButton;

    @FindBy(css = ".navbar-brand")
    private WebElement navBarBrand;

    public void login(String username, String password) {
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        click(loginButton);
    }

    /**
     * Demonstrates Selenium 4 Relative Locators
     */
    public void loginWithRelativeLocators(String username, String password) {
        sendKeys(usernameField, username);
        // Find password field which is below username field
        WebElement pwd = driver.findElement(with(By.tagName("input")).below(usernameField));
        sendKeys(pwd, password);
        // Find login button which is below password field
        WebElement loginBtn = driver.findElement(with(By.tagName("button")).below(pwd));
        click(loginBtn);
    }

    public boolean isLoggedIn() {
        // Simple check, can be improved with specific element on dashboard
        return navBarBrand.isDisplayed();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/pages/components/NavBar.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package pages.components;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavBar extends BasePage {

    @FindBy(css = ".navbar-brand")
    private WebElement homeLink;

    @FindBy(xpath = "//a[text()='Rooms']")
    private WebElement roomsLink;

    @FindBy(xpath = "//a[text()='Admin panel']")
    private WebElement adminPanelLink;

    @FindBy(id = "logout")
    private WebElement logoutLink;

    public void clickHome() {
        click(homeLink);
    }

    public void clickRooms() {
        click(roomsLink);
    }

    public void clickAdminPanel() {
        click(adminPanelLink);
    }

    public void logout() {
        click(logoutLink);
    }

    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/AccessibilityUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AccessibilityUtil {

    public static void checkAccessibility(WebDriver driver) {
        LoggerUtil.info("--- ACCESSIBILITY SCAN STARTING ---");
        Results results = new AxeBuilder().analyze(driver);

        if (results.getViolations().isEmpty()) {
            LoggerUtil.info("No accessibility violations found.");
        } else {
            LoggerUtil.error("Accessibility violations found: " + results.getViolations().size());
            results.getViolations().forEach(violation -> {
                LoggerUtil.error("Violation: " + violation.getHelp() + " | Impact: " + violation.getImpact());
            });
            // We can choose to fail the test or just log
            // Assert.assertTrue(results.getViolations().isEmpty(), "Accessibility
            // violations detected");
        }
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/AnnotationTransformer.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @SuppressWarnings("rawtypes")
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
            Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/CSVReaderUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVReaderUtil {

    public static List<String[]> readCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            LoggerUtil.error("Error reading CSV file: " + e.getMessage());
            return null;
        }
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/DBUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    private static Connection connection;

    public static void establishConnection(String url, String username, String password) {
        try {
            LoggerUtil.info("Connecting to Database: " + url);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LoggerUtil.error("Failed to connect to database: " + e.getMessage());
        }
    }

    public static List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            LoggerUtil.error("Error executing query: " + e.getMessage());
        }
        return results;
    }

    public static int executeUpdate(String query) {
        try (State

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/ExcelReader.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public static List<Map<String, String>> getExcelData(String filePath, String sheetName) {

        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getPhysicalNumberOfCells();

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                Row row = sheet.getRow(i);
                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < colCount; j++) {

                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = "";

                    Cell cell = row.getCell(j);

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = String.valueOf(cell.getNumericCellVal

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/FakerUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class FakerUtil {
    private static final Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static int getRandomPrice(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String getFutureDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(faker.date().future(30, TimeUnit.DAYS));
    }

    public static String getPastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(faker.date().past(30, TimeUnit.DAYS));
    }

    public static String getRandomAdjective() {
        return faker.lorem().word();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/JSONReader.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJsonFile(String filePath) {
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            LoggerUtil.error("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/LoggerUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/RetryAnalyzer.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 1; // You can move this to config.properties

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            LoggerUtil.info("Retrying test: " + result.getName() + " for the " + retryCount + " time.");
            return true;
        }
        return false;
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/Selenium4FeaturesUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.emulation.Emulation;
import org.openqa.selenium.devtools.v119.performance.Performance;
import org.openqa.selenium.devtools.v119.performance.model.Metric;

import java.util.List;
import java.util.Optional;

public class Selenium4FeaturesUtil {

    /**
     * Selenium 4: New Window / Tab Support
     */
    public static void openNewTab(WebDriver driver) {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public static void openNewWindow(WebDriver driver) {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    /**
     * Selenium 4: Network Interception (CDP)
     */
    public static void enableNetworkInterception(WebDriver driver) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                LoggerUtil.info("Request URL: " + request.getRequest().getUrl());
                LoggerUtil.info("Request Method: " + request.getRequest().getMethod());
            });
        } else {
            Log

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/VisualUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualUtil {

    private static final String BASELINE_DIR = "src/test/resources/baselines/";
    private static final String DIFF_DIR = "target/visual-diffs/";

    public static void compareScreenshot(WebDriver driver, String fileName) throws IOException {
        LoggerUtil.info("--- VISUAL REGRESSION CHECK: " + fileName + " ---");

        File baselineFile = new File(BASELINE_DIR + fileName + ".png");

        Screenshot actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);

        if (!baselineFile.exists()) {
            LoggerUtil.info("Baseline not found. Creating baseline: " + fileName);
            baselineFile.getParentFile().mkdirs();
            ImageIO.write(actualScreenshot.getImage(), "PNG", baselineFile);
            return;
        }

        BufferedImage expectedImage = ImageIO.read(baselineFile);
        ImageDiff diff = new ImageDiffer().makeDiff(actualScreenshot.getImage(), expectedImage);

        if (diff.hasDiff()) {
            Lo

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/WaitFactory.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitFactory {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageLoad(WebDriver driver) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/main/java/utils/ZapUtil.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package utils;

import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;

public class ZapUtil {

    private static final String ZAP_PROXY_HOST = "localhost";
    private static final int ZAP_PROXY_PORT = 8080;

    public static void setRestAssuredProxy() {
        LoggerUtil.info("Configuring Rest Assured to use ZAP Proxy at " + ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
        RestAssured.proxy = ProxySpecification.host(ZAP_PROXY_HOST).withPort(ZAP_PROXY_PORT);
    }

    public static ChromeOptions getZapChromeOptions() {
        LoggerUtil.info("Configuring Chrome to use ZAP Proxy");
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
        proxy.setSslProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
        options.setAcceptInsecureCerts(true);
        return options;
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/context/ScenarioContext.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package context;

import io.restassured.response.Response;

public class ScenarioContext {
    private Response response;
    private int bookingId;
    private String token;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/runners/TestRunner.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = "stepdefinitions", plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
}, monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/AdvancedStepDefs.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.FakerUtil;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class AdvancedStepDefs {

    private final ScenarioContext context;
    private static String currentFirstname;

    public AdvancedStepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I create a new booking using random data")
    public void createRandomBooking() {
        currentFirstname = FakerUtil.getFirstName();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", FakerUtil.getFutureDate());
        bookingDates.put("checkout", FakerUtil.getFutureDate());

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", currentFirstname);
        body.put("lastname", FakerUtil.getLastName());
        body.put("totalprice", FakerUtil.getRandomPrice(100, 1000));
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "All inclusive");

        context.setResponse(RequestBuilder.postRequest("/booking", body));
        LoggerUtil.info("Response: " + context.getResponse().asPrettyString());
    }

    @Then("I verify the booking respo

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/BookingSteps.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;

import static io.restassured.RestAssured.given;

public class BookingSteps {

    Booking booking;
    Response response;

    @Given("booking details are prepared")
    public void prepareBooking() {

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-06-01");
        dates.setCheckout("2024-06-05");

        booking = new Booking();
        booking.setFirstname("BDD");
        booking.setLastname("User");
        booking.setTotalprice(1300);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);
    }

    @When("user calls create booking API")
    public void callAPI() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        response =
        given()
            .header("Content-Type", "application/json")
            .body(booking)
        .when()
            .post("/booking");
    }

    @Then("booking should be created successfully")
    public void validate() {
        response.then().statusCode(200);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/Hooks.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import base.APIBase;
import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.LoggerUtil;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {
        LoggerUtil.info("Starting Scenario: " + scenario.getName());
        APIBase.setup();
        DriverFactory.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && scenario.getSourceTagNames().contains("@ui")) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
        DriverFactory.quitDriver();
        LoggerUtil.info("Completed Scenario: " + scenario.getName());
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/NegativeStepDefs.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class NegativeStepDefs {

    private final ScenarioContext context;

    public NegativeStepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I attempt to create a booking with {string}, {string}, {string}, {string}, {string}, {string}")
    public void attemptCreateWithInvalidData(String fname, String lname, String price, String deposit, String checkin,
            String checkout) {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", fname);
        body.put("lastname", lname);

        try {
            body.put("totalprice", Integer.parseInt(price));
        } catch (NumberFormatException e) {
            body.put("totalprice", price); // Send as string to test type validation
        }

        body.put("depositpaid", Boolean.parseBoolean(deposit));
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Negative Test");

        context.setResponse(RequestBuilder.postRequest("/booking", body));


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/Selenium4StepDefs.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import base.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.Selenium4FeaturesUtil;
import utils.LoggerUtil;

public class Selenium4StepDefs {

    private LoginPage loginPage = new LoginPage();

    @When("I login using relative locators")
    public void loginUsingRelativeLocators() {
        loginPage.loginWithRelativeLocators("admin", "password");
    }

    @When("I open a new tab and navigate to {string}")
    public void openNewTabAndNavigate(String url) {
        Selenium4FeaturesUtil.openNewTab(DriverFactory.getDriver());
        DriverFactory.getDriver().get(url);
        LoggerUtil.info("Opened new tab and navigated to " + url);
    }

    @Given("I enable network interception to monitor requests")
    public void enableNetworkInterception() {
        Selenium4FeaturesUtil.enableNetworkInterception(DriverFactory.getDriver());
    }

    @Then("I log the browser performance metrics")
    public void logPerformanceMetrics() {
        Selenium4FeaturesUtil.logPerformanceMetrics(DriverFactory.getDriver());
    }

    @Given("I mock my geolocation to latitude {double} and longitude {double}")
    public void mockLocation(double lat, double lon) {
        Selenium4FeaturesUtil.mockGeolocation(DriverFactory.getDriver(), lat, lon, 100);
        LoggerUtil.info("Geolocation mocked to: " + lat + ", " + lon);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/stepdefinitions/StepDefs.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import base.DriverFactory;
import config.ConfigReader;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class StepDefs {

    private final ScenarioContext context;

    public StepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I create a booking with {string}, {string}, {string}, {string}, {string}, {string}")
    public void createBooking(String fname, String lname, String price, String deposit, String checkin,
            String checkout) {

        if (fname.equalsIgnoreCase("dynamic"))
            fname = utils.FakerUtil.getFirstName();
        if (lname.equalsIgnoreCase("dynamic"))
            lname = utils.FakerUtil.getLastName();
        if (price.equalsIgnoreCase("dynamic"))
            price = String.valueOf(utils.FakerUtil.getRandomPrice(50, 500));
        if (checkin.equalsIgnoreCase("dynamic"))
            checkin = utils.FakerUtil.getFutureDate();
        if (checkout.equalsIgnoreCase("dynamic"))
            checkout = utils.FakerUtil.getFutureDate();

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<S

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/tests/BookingExcelDrivenTest.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingExcelDrivenTest {

    @Test
    public void excelDrivenBookingTest() {

        String filePath = "src/test/resources/testdata.xlsx";

        List<Map<String, String>> excelData =
                ExcelReader.getExcelData(filePath, "Sheet1");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        for (Map<String, String> row : excelData) {

            String fname = row.get("FirstName");
            String lname = row.get("LastName");
            int price = Integer.parseInt(row.get("Price"));

            BookingDates dates = new BookingDates();
            dates.setCheckin("2024-05-01");
            dates.setCheckout("2024-05-10");

            Booking booking = new Booking();
            booking.setFirstname(fname);
            booking.setLastname(lname);
            booking.setTotalprice(price);
            booking.setDepositpaid(true);
            booking.setBookingdates(dates);

            Response response =
            given()
                .header("Content-Type", "application/json")
                .body(booking)
            .when()
                .post("/booking")
            .then()
           

```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/tests/BookingExternalJsonTest.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java


package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class BookingExternalJsonTest {

    @Test
    public void externalJsonDrivenTest() throws Exception {

        String jsonBody = new String(
            Files.readAllBytes(Paths.get("src/test/resources/bookingPayload.json"))
        );

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
            .header("Content-Type", "application/json")
            .body(jsonBody)
        .when()
            .post("/booking")
        .then()
            .statusCode(200);
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/java/tests/BookingSchemaValidationTest.java

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

package tests;

import io.restassured.RestAssured;
import models.Booking;
import models.BookingDates;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingSchemaValidationTest {

    @Test
    public void validateBookingSchema() {

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-03-01");
        dates.setCheckout("2024-03-05");

        Booking booking = new Booking();
        booking.setFirstname("SchemaUser");
        booking.setLastname("Test");
        booking.setTotalprice(2000);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
            .header("Content-Type", "application/json")
            .body(booking)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .assertThat()
            .body(matchesJsonSchemaInClasspath("bookingSchema.json"));
    }
}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/resources/allure.properties

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

allure.results.directory=target/allure-results
allure.link.issue.pattern=https://example.org/issue/{}
allure.link.tms.pattern=https://example.org/tms/{}


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/resources/config.properties

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

baseurl=https://restful-booker.herokuapp.com
username=admin
password=password123
browser=chrome
explicit.wait=10
headless=false
report.path=test-output/ExtentReport.html
screenshot.path=test-output/screenshots/


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/resources/extent-config.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<?xml version="1.0" encoding="UTF-8"?>
<extentreports>
  <configuration>
    <!-- Report theme -->
    <theme>DARK</theme>
    
    <!-- Document title -->
    <documentTitle>Automation Execution Report</documentTitle>
    
    <!-- Report name -->
    <reportName>Enterprise Automation Framework Test Results</reportName>
    
    <!-- Date format -->
    <timeStampFormat>MMM dd, yyyy HH:mm:ss</timeStampFormat>
  </configuration>
</extentreports>


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/resources/log4j2.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        <File name="File" fileName="logs/app.log" append="false">
            <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>


```


## File: Selenium_Java_RestAssured_TestNG_BDD-main/src/test/resources/testng.xml

### Purpose and Layer

Auto-generated analysis based on repository structure.


### Code Snippet

```java

<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="NewTestsSuite">

    <test name="OnlyNewTests">

        <classes>
            <class name="tests.BookingExcelDrivenTest"/>
            <class name="tests.BookingExternalJsonTest"/>
            <class name="tests.BookingSchemaValidationTest"/>
        </classes>

    </test>

</suite>


```


## Interview Q&A Based on These Files

1. Explain DriverFactory – manages ThreadLocal WebDriver for parallel execution.

2. What is BasePage – reusable Selenium methods.

3. How ExcelReader works – Apache POI based data provider.

4. How API tests are written – RestAssured with POJO serialization.
