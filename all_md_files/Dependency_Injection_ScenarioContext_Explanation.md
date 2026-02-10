
# Dependency Injection & ScenarioContext – Detailed Explanation

This document explains specifically how Dependency Injection (DI) and ScenarioContext are implemented in this automation framework in simple, interview-friendly terms.

---

## 1. What Problem We Needed to Solve

In a Cucumber BDD framework, step definitions are split into multiple classes such as:

- AdvancedStepDefs  
- NegativeStepDefs  
- SeleniumStepDefs  

Each step class executes different parts of the scenario.  
But often one step needs data produced by another step.

Example:

- Step 1 creates a booking via API  
- Step 2 needs that booking ID  
- Step 3 validates the same booking  

Without a sharing mechanism, this would be impossible.

---

## 2. Solution Implemented – ScenarioContext

To solve this, the framework uses a central class:

### `context/ScenarioContext.java`

This class acts as a shared data container during one scenario execution.

It stores values such as:

- API responses  
- Tokens  
- Dynamic IDs  
- Temporary test data  

---

## 3. How Dependency Injection Is Used

Instead of creating ScenarioContext manually in each step class, we use constructor-based dependency injection.

Example from the framework:

```java
public class AdvancedStepDefs {

    private final ScenarioContext context;

    public AdvancedStepDefs(ScenarioContext context) {
        this.context = context;
    }
}
```

And similarly:

```java
public class NegativeStepDefs {

    private final ScenarioContext context;

    public NegativeStepDefs(ScenarioContext context) {
        this.context = context;
    }
}
```

---

## 4. What Happens Internally

When a scenario starts, Cucumber automatically:

1. Creates ONE instance of ScenarioContext  
2. Creates all step definition classes  
3. Injects the SAME ScenarioContext object into each constructor  

So all step classes receive the same shared object.

---

## 5. Flow Example

### Step 1

```java
context.set("bookingId", id);
```

### Step 2

```java
int id = context.get("bookingId");
```

Because the same context object is injected, data flows easily between steps.

---

## 6. Why This Approach Is Good

### Advantages

- No static variables  
- No global state  
- Parallel execution safe  
- Loose coupling  
- Clean and maintainable code  
- Easy to debug  

---

## 7. No PicoContainer Required

Older frameworks used PicoContainer for this purpose.  
In this project, we rely on modern Cucumber native DI support.

Therefore:

- No external DI library  
- No PicoContainer  
- Pure constructor injection  

---

## 8. Interview-Ready Explanation

You can describe it like this:

“We use constructor-based dependency injection where Cucumber automatically provides a shared ScenarioContext object to all step definition classes. This allows us to pass data between steps in the same scenario without using static variables or global objects, making the framework parallel-safe and maintainable.”

---

## 9. Summary

| Concept | Implementation |
|-------|---------------|
| DI Type | Constructor Injection |
| Shared Object | ScenarioContext |
| Purpose | Data sharing between steps |
| Library | Native Cucumber DI |
| Benefit | Clean, scalable, thread-safe |

---

### End of Document
