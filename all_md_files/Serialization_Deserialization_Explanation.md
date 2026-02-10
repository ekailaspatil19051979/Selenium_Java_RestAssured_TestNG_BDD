# Serialization and Deserialization in Rest Assured – Detailed Guide

## 1. Introduction

In API Automation using Rest Assured, two very important concepts are:

- **Serialization**
- **Deserialization**

These are fundamental when we work with request bodies and API responses.

---

## 2. What is Serialization?

### Simple Definition

**Serialization is the process of converting a Java Object into JSON format.**

### Why Serialization is Needed?

- APIs accept data in JSON format.
- Automation code is written in Java.
- So Java objects must be converted into JSON before sending request.

### Flow

Java Object  →  JSON  →  API Request

---

### Example Scenario

Suppose we want to create a new user using POST API.

API expects this JSON:

```json
{
  "name": "Rahul",
  "job": "Tester"
}
```

But in Java we normally create objects.

---

## 3. Serialization Example

### Step 1: Create POJO Class

```java
public class User {

    private String name;
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
```

---

### Step 2: Use Object in Test

```java
User user = new User();
user.setName("Rahul");
user.setJob("Tester");
```

---

### Step 3: Send Request using Rest Assured

```java
given()
    .header("Content-Type", "application/json")
    .body(user)       // Java Object
.when()
    .post("/users")
.then()
    .statusCode(201);
```

---

### What Happens Internally?

Rest Assured automatically converts:

Java Object → JSON

This automatic conversion = **Serialization**

---

## 4. What is Deserialization?

### Simple Definition

**Deserialization is the process of converting JSON response into Java Object.**

---

### Why Deserialization is Needed?

- API gives response in JSON format.
- But in Java we need to work with objects.
- So JSON must be converted into Java object.

### Flow

API Response (JSON) → Java Object

---

## 5. Deserialization Example

### Sample API Response

```json
{
  "name": "Rahul",
  "job": "Tester",
  "id": "100",
  "createdAt": "2024-01-01"
}
```

---

### Step 1: Create Response POJO

```java
public class UserResponse {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
```

---

### Step 2: Convert JSON to Java Object

```java
UserResponse response =

given()
.when()
    .get("/users/2")
.then()
    .extract()
    .as(UserResponse.class);
```

---

### Now We Can Use Java Object

```java
System.out.println(response.getName());
System.out.println(response.getJob());
```

---

## 6. Key Differences

| Serialization | Deserialization |
|---------------|----------------|
| Java Object → JSON | JSON → Java Object |
| Used while sending request | Used while reading response |
| Happens before API call | Happens after API response |
| Converts POJO to JSON | Converts JSON to POJO |

---

## 7. Real Time Use in Framework

### Common Use Cases

- Sending POST request body
- Updating data with PUT request
- Reading complex JSON responses
- Chaining APIs
- Data validation

---

## 8. Libraries Used

Behind the scenes Rest Assured uses:

- Jackson Library
- Gson Library

These perform conversion automatically.

---

## 9. Interview Summary

### One Line Definitions

- **Serialization:** Converting Java object to JSON
- **Deserialization:** Converting JSON to Java object

---

## 10. Final Conclusion

Serialization and Deserialization are core concepts in Rest Assured automation.

Without these:

- We cannot send Java objects in API
- We cannot read API responses as objects

Understanding these is mandatory for API automation engineers.

---

### End of Document
