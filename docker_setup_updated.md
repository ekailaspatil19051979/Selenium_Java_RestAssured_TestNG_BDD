# 🐳 Docker Automation Setup Guide – Detailed Version

This document explains in depth how to containerize and execute the Selenium + Java + TestNG automation framework using Docker and Selenium Grid. It also includes troubleshooting steps and real debugging scenarios.

---

## 1. Prerequisites

Before starting, ensure the following are available on your system:

1. Docker Desktop installed and running (Windows/Mac) or Docker Engine (Linux)
2. Docker Compose installed (comes with Docker Desktop by default)
3. Java + Maven project working locally
4. Project should contain:
   - pom.xml
   - src folder
   - testng.xml

To verify Docker installation, open terminal and run:

```
docker --version
docker-compose --version
```

If these commands fail, Docker is either not installed or not added to system PATH.

---

## 2. Understanding the Dockerfile (Line-by-Line Explanation)

Below is the Dockerfile used in this project:

```
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY testng.xml .
RUN mvn dependency:go-offline

FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app .
ENTRYPOINT ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
```

### Explanation

- `FROM maven:3.9.6-eclipse-temurin-17 AS build`
  - Uses official Maven + JDK 17 image as base
  - Creates a build stage for caching dependencies

- `WORKDIR /app`
  - Sets /app as working directory inside container

- `COPY pom.xml .`
  - Copies only pom.xml first
  - Allows Docker to cache Maven dependencies

- `COPY src ./src`
  - Copies source code into container

- `COPY testng.xml .`
  - Copies TestNG suite file

- `RUN mvn dependency:go-offline`
  - Downloads all Maven dependencies before running tests

- Second FROM statement creates final runtime image

- `COPY --from=build /app .`
  - Copies compiled project from build stage

- `ENTRYPOINT ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]`
  - This is the command executed when container starts
  - Runs TestNG suite inside Docker

---

## 3. Understanding docker-compose.yml (Line-by-Line)

```
version: "3"
services:
  selenium-hub:
    image: selenium/hub:4.16.1
    container_name: selenium-hub
    ports:
      - "4444:4444"

  chrome:
    image: selenium/node-chrome:4.16.1
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  firefox:
    image: selenium/node-firefox:4.16.1
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  test-runner:
    build: .
    depends_on:
      - chrome
      - firefox
    environment:
      - grid.url=http://selenium-hub:4444
      - browser=chrome
    volumes:
      - ./target:/app/target
```

### Explanation

- selenium-hub: central grid controller
- chrome / firefox: browser nodes for execution
- test-runner: container that runs Maven tests
- environment variables pass grid URL to framework
- volume mapping copies reports back to host machine

---

## 4. Code Changes Required for Remote Execution

Your DriverFactory must support remote execution:

```
String gridUrl = System.getenv("grid.url");
String browser = System.getenv("browser");

if (gridUrl != null) {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setBrowserName(browser);
    driver = new RemoteWebDriver(new URL(gridUrl), caps);
} else {
    // Local execution
}
```

---

## 5. Commands to Run the Setup

### Build Docker Images
```
docker-compose build
```

### Start Grid and Tests
```
docker-compose up
```

### Run in background
```
docker-compose up -d
```

### View logs
```
docker-compose logs -f
```

### Stop all containers
```
docker-compose down
```

---

## 6. Monitoring Selenium Grid

Open in browser:

http://localhost:4444

If you see:

"No running or queued sessions"

That simply means grid is up but tests have not started yet.

---

## 7. Debugging Steps Performed in This Setup

### Issue 1 – Docker command not recognized
Error:
```
docker: command not recognized
```
Fix:
- Start Docker Desktop
- Add Docker path to Windows PATH
- Restart terminal

---

### Issue 2 – Jenkins build failing with "sh" command
Cause:
Windows Jenkins agent used `sh` instead of `bat`

Fix:
Replace:
```
sh 'mvn test'
```
with:
```
bat 'mvn test'
```

---

### Issue 3 – Chrome installation failure in Dockerfile
Old apt-key method deprecated.

Fix:
Either remove Chrome installation completely (recommended) or use updated keyring method.

---

### Issue 4 – Tests not connecting to Grid
Cause:
Environment variable mismatch

Fix:
Ensure compose file and Java code use same variable names (grid.url / browser)

---

### Issue 5 – No running sessions on Grid
Reason:
Tests were not started yet

Verification command:
```
docker-compose logs test-runner
```

---

## 8. Validating Test Execution

Check test results after run:

```
target/surefire-reports
```

To generate Allure report:

```
mvn allure:serve
```

---

## 9. Final Flow Summary

1. Write tests locally
2. Build Docker image
3. Start Selenium Grid
4. Run tests inside container
5. Collect reports on host machine

---

## 10. Best Practices

- Do not install browsers inside test-runner image
- Use Selenium Grid nodes instead
- Keep environment variables configurable
- Use volume mapping for reports
- Use docker-compose for parallel execution

---

This updated guide reflects the complete setup, debugging journey, and final working solution.

