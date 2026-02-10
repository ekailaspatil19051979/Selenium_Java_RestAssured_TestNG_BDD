
# Rest Assured Serialization & Deserialization – Project Explanation

This README explains how **Serialization and Deserialization** are used in this repository:  
**Selenium_Java_RestAssured_TestNG_BDD**

---

## 1. Where These Concepts Are Used

### Serialization (Java Object → JSON)

Serialization is used when sending request body in API calls.

In this project it happens mainly in:

**File:**  
`src/main/java/api/RequestBuilder.java`

Whenever code like below is executed:

```java
given()
   .body(javaObject)
```

Rest Assured automatically converts the Java POJO object into JSON before sending request.

This automatic conversion = **Serialization**

---

### Deserialization (JSON → Java Object)

Deserialization is used when reading API response and converting it into Java objects.

Used in:

- ResponseValidator.java  
- Step Definition classes  
- JSONReader.java (uses Jackson ObjectMapper)

Example patterns in project:

```java
response.jsonPath().getString("key")
```

and

```java
response.as(Booking.class)
```

These are examples of **Deserialization**

---

## 2. POJO Models in This Project

Project contains POJO classes under:

`src/main/java/models`

- Booking.java  
- BookingDates.java

These classes are used for mapping JSON request and response.

---

## 3. End-to-End Example Test Case Using Booking POJO

Below is a complete example test case showing both Serialization and Deserialization.

### Example: Create Booking API Automation

```java
package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingAPITest {

    @Test
    public void createBookingTest() {

        // ----- STEP 1: Create Java Object (POJO) -----
        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-01-01");
        dates.setCheckout("2024-01-10");

        Booking booking = new Booking();
        booking.setFirstname("Rahul");
        booking.setLastname("Sharma");
        booking.setTotalprice(1000);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);
        booking.setAdditionalneeds("Breakfast");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // ----- STEP 2: Serialization Happens Here -----
        Response response =
        given()
            .header("Content-Type", "application/json")
            .body(booking)     // Java object converted to JSON
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .extract()
            .response();

        // ----- STEP 3: Deserialization Happens Here -----
        Booking responseBooking = response.as(Booking.class);

        // ----- STEP 4: Validations -----
        Assert.assertEquals(responseBooking.getFirstname(), "Rahul");
        Assert.assertEquals(responseBooking.getLastname(), "Sharma");
        Assert.assertEquals(responseBooking.getTotalprice(), 1000);
    }
}
```

---

## 4. Flow Explanation

### Serialization Flow

Java Object → Rest Assured `.body()` → JSON → API Request

### Deserialization Flow

API Response (JSON) → `.as(Booking.class)` → Java Object → Assertions

---

## 5. Libraries Used

- Rest Assured  
- Jackson (internal)  
- TestNG  
- Java

---

## 6. Interview Summary Based on This Repo

If asked in interview:

**“How did you use serialization and deserialization in your project?”**

Sample Answer:

> In our framework we used POJO classes like Booking.java to represent request bodies.  
> While sending POST requests, we passed Java objects in Rest Assured `.body()` method which performed serialization automatically.  
> For responses, we used jsonPath() and `.as(POJO.class)` to deserialize JSON responses into Java objects and performed assertions.

---

### End of README
