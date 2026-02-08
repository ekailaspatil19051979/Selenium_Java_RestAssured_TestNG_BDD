# 🐳 Docker Automation Setup Guide

This guide explains how to containerize your automation framework and run tests using Docker and Selenium Grid.

---

## Prerequisites
1.  **Docker Installed**: Install Docker Desktop (Windows/Mac) or Docker Engine (Linux).
2.  **Docker Compose**: Usually included with Docker Desktop.

---

## Step 1: Create the Dockerfile
Create a file named `Dockerfile` (no extension) in your root directory. This image will contain your code and dependencies.

```dockerfile
# Use an official Maven image to build and run the tests
FROM maven:3.8.5-openjdk-17-slim

# Set working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project source code
COPY src ./src

# Set the entry point to run tests
# We use "-Dselenium.grid.url" to point to the standalone grid or hub
ENTRYPOINT ["mvn", "test"]
```

---

## Step 2: Configure Selenium Grid (docker-compose.yml)
Create a `docker-compose.yml` file to spin up a "Hub" and "Node" (Chrome/Firefox).

```yaml
version: "3"
services:
  # Selenium Hub
  selenium-hub:
    image: selenium/hub:4.1.2
    container_name: selenium-hub
    ports:
      - "4444:4444"

  # Chrome Node
  chrome:
    image: selenium/node-chrome:4.1.2
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443
    shm_size: '2gb'

  # Your Test Runner
  tests:
    build: .
    depends_on:
      - selenium-hub
    environment:
      - GRID_URL=http://selenium-hub:4444/wd/hub
      - BROWSER=chrome
    volumes:
      - ./target/allure-results:/app/target/allure-results
```

---

## Step 3: Update your Code for Remote Execution
Ensure your `DriverFactory` can read the `GRID_URL`.

```java
if (System.getenv("GRID_URL") != null) {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setBrowserName("chrome");
    driver = new RemoteWebDriver(new URL(System.getenv("GRID_URL")), caps);
} else {
    // Local execution logic
}
```

---

## Step 4: Building and Running
1.  **Build the Image**:
    ```bash
    docker-compose build
    ```
2.  **Run All Services**:
    ```bash
    docker-compose up
    ```
    *This will start the Hub, the Chrome Node, and then start your `tests` container.*

---

## Step 5: Monitoring the Grid
1.  Navigate to [http://localhost:4444](http://localhost:4444) in your browser.
2.  You will see the Selenium Grid console showing the active nodes and the status of your running tests.

---

## Step 6: Cleanup
After tests are finished, shut down the containers to free up resources:
```bash
docker-compose down
```

---

## FAQ: Why use Docker?
*   **Consistency**: "It works on my machine" issues disappear.
*   **Isolation**: No need to install specific Chrome/Firefox versions on your machine.
*   **Scalability**: Easily spin up 10 Chrome nodes by changing `docker-compose`.
