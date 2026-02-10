I enhanced the existing Rest Assured framework by adding advanced automation capabilities.
I introduced Excel-driven testing using Apache POI, external JSON file-based testing for complex payloads, and JSON schema validation for contract testing.
Additionally, I integrated Cucumber BDD to enable behavior-driven API automation.
These changes made the framework more scalable, maintainable and enterprise-ready.

🧠 Simple Conclusion
Aspect	Original Repo	Updated Repo
Basic API Automation	Yes	Yes
Advanced Data Driven	No	Yes
BDD Support	No	Yes
Schema Validation	No	Yes
External Payloads	No	Yes


# Updated Framework – Changes Documentation

This document explains **all new changes added** to the original repository and how to explain them in interviews.

---

## 1. Overview of Enhancements

The following major enhancements were added to the existing framework:

- Excel Driven API Testing  
- External JSON File Driven Testing  
- JSON Schema Validation  
- Cucumber BDD API Automation  
- Additional Test Resources  

---

# 2. New Files Added – Complete List

## A. Test Classes Added

### Path:
`src/test/java/tests/`

| File Name | Purpose |
|---------|---------|
| BookingExcelDrivenTest.java | Demonstrates data driven API testing using Excel |
| BookingExternalJsonTest.java | Shows how to read request body from external JSON file |
| BookingSchemaValidationTest.java | Validates API response using JSON Schema |

---

## B. Cucumber BDD Implementation

### Path:
`src/test/resources/features/`

| File | Purpose |
|------|--------|
| Booking.feature | Gherkin scenario for API automation |

### Path:
`src/test/java/stepdefinitions/`

| File | Purpose |
|------|--------|
| BookingSteps.java | Step definitions connecting feature file with Rest Assured code |

---

## C. Test Resource Files Added

### Path:
`src/test/resources/`

| File | Use |
|----|----|
| testdata.xlsx | Input data for Excel driven testing |
| bookingPayload.json | External JSON payload used in API request |
| bookingSchema.json | JSON schema for response validation |

---

# 3. Detailed Explanation of Each Addition

## 3.1 Excel Driven Testing

**File:**  
`BookingExcelDrivenTest.java`

### What It Does

- Reads test data from Excel sheet  
- Creates Booking POJO dynamically  
- Performs API call for each row  
- Validates response  

### Why It Is Useful

- Supports large test data sets  
- Business users can update Excel without code changes  
- Real world enterprise approach  

---

## 3.2 External JSON Driven Test

**File:**  
`BookingExternalJsonTest.java`

### Purpose

- Instead of hardcoding request body  
- JSON is read from external file  
- Useful for complex payloads  

### Real Use Case

- Contract testing  
- Large dynamic payloads  
- Reusable API templates  

---

## 3.3 JSON Schema Validation

**Files:**

- `BookingSchemaValidationTest.java`
- `bookingSchema.json`

### What It Validates

- Structure of API response  
- Data types  
- Mandatory fields  

### Benefit

- Ensures API contract stability  
- Catches backend changes early  

---

## 3.4 Cucumber BDD Support

**Files:**

- Booking.feature  
- BookingSteps.java  

### Purpose

- Enable business-readable test scenarios  
- Convert Gherkin steps into API automation  
- Bridge between manual QA and automation  

---

# 4. How to Run the New Tests

### Using Maven

```
mvn test
```

### Using TestNG

Run individual classes from:

`src/test/java/tests/`

---

# 5. How to Explain in Interview

### Sample Explanation

You can say:

> In my API automation framework, I enhanced the base Rest Assured project by adding multiple data-driven approaches.  
> I implemented Excel driven testing using Apache POI, external JSON file-based testing for complex payloads, and JSON schema validation for contract testing.  
> Additionally, I integrated Cucumber BDD to allow business-friendly API automation scenarios.  
> These improvements made the framework more scalable, maintainable, and enterprise-ready.

---

## Keywords to Use in Interview

- Serialization / Deserialization  
- Data Driven Framework  
- JSON Schema Validation  
- Contract Testing  
- Cucumber BDD  
- Externalized Test Data  
- Rest Assured Best Practices  

---

# 6. Final Outcome

After these changes, the framework now supports:

- Multiple data sources  
- Advanced validations  
- BDD + TestNG hybrid  
- Enterprise level API automation  

---

### End of Document
