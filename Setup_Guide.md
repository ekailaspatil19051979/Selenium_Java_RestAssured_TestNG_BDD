# 🛳️ CI/CD & DevOps Setup Guide

This guide covers the end-to-end process of setting up Git, Jenkins, Azure DevOps, and Docker for the Automation Framework.

---

## 1. 🐙 Git Repository Setup & Code Push

### Step 1: Initialize Git
Open your project folder in the terminal/command prompt.
```bash
git init
```

### Step 2: Create .gitignore
Create a file named `.gitignore` in the root directory to avoid committing unnecessary files.
**Add the following content:**
```text
target/
test-output/
.idea/
*.iml
.settings/
.classpath
.project
logs/
.DS_Store
```

### Step 3: Stage and Commit Code
```bash
git add .
git commit -m "Initial framework setup with Selenium, RestAssured, and Cucumber"
```

### Step 4: Push to Remote Repository (GitHub/GitLab/Bitbucket)
1.  Create a new repository on GitHub (e.g., `automation-framework`).
2.  Copy the remote URL (e.g., `https://github.com/username/automation-framework.git`).
3.  Link your local repo to remote:
    ```bash
    git remote add origin https://github.com/username/automation-framework.git
    git branch -M main
    git push -u origin main
    ```

---

## 2. 🏗️ Jenkins Job Creation & Execution

### Prerequisites
*   Jenkins installed and running.
*   **Plugins Installed**: Maven Integration, Git, Pipeline, Allure Jenkins Plugin.
*   **Tools Configured**: JDK 17, Maven 3.

### Option A: Freestyle Project (Simple)
1.  **New Item** -> **Freestyle project**.
2.  **Source Code Management**: Select **Git** -> Enter Repo URL.
3.  **Build**: Select **Invoke top-level Maven targets**.
    *   **Goals**: `clean test -Dcucumber.filter.tags="@smoke"`
4.  **Post-build Actions**: Select **Allure Report**.
    *   **Path**: `target/allure-results`
5.  **Save & Build Now**.

### Option B: Pipeline Project (Using Jenkinsfile)
1.  **New Item** -> **Pipeline**.
2.  **Pipeline Definition**: Select **Pipeline script from SCM**.
3.  **SCM**: Git -> Enter Repo URL.
4.  **Script Path**: `Jenkinsfile` (Ensure this file exists in your repo root).
5.  **Save & Build Now**.

**Result**: Detailed stage view (Checkout -> Build -> Test -> Report) and Allure Report link on the sidebar.

---

## 3. ☁️ Run Tests in Azure DevOps (Pipelines)

### Step 1: Push Code to Azure Repos
You can push code to Azure Repos directly or use GitHub as a service connection.

### Step 2: Create Pipeline
1.  Go to **Pipelines** -> **New Pipeline**.
2.  **Where is your code?**: Select **Azure Repos Git** or **GitHub**.
3.  **Select Repository**: Choose your `automation-framework` repo.
4.  **Configure Pipeline**: Select **Existing Azure Pipelines YAML file**.
5.  **Path**: Select `azure-pipeline.yml` from the dropdown.

### Step 3: Run Pipeline
1.  Review the YAML content.
2.  Click **Run**.
3.  The pipeline will provision an agent (ubuntu-latest), checkout code, run Maven tests, and publish results.

### Step 4: View Results
1.  Go to the **Test Plans** -> **Runs** tab.
2.  Click on the latest run to see pass/fail charts and failure logs.

---

## 4. 🐳 Run Tests in Docker

### Method 1: Build & Run Custom Image
**Step 1: Build the Image**
```bash
docker build -t automation-framework .
```

**Step 2: Run Tests Container**
```bash
docker run --rm automation-framework
```
*Note: This runs tests in headless mode (by default) or fails if UI is needed without X11 forwarding.*

### Method 2: Docker Compose (Selenium Grid) - Recommended
This method spins up a full grid (Hub + Chrome Node + Firefox Node) and the test runner.

**Step 1: Start Services**
```bash
docker-compose up --build
```

**Step 2: Monitor Execution**
*   **Grid Dashboard**: Open `http://localhost:4444` to see Chrome/Firefox nodes.
*   **Test Logs**: Watch the terminal output of `test-runner` service.

**Step 3: Stop Services**
```bash
docker-compose down
```

---

## 5. 🛠️ Troubleshooting CI/CD

| Issue | Solution |
| :--- | :--- |
| **Jenkins: mvn not found** | Go to Global Tool Configuration -> Maven -> Install Automatically. |
| **Azure: Permission Denied** | Ensure the YAML file has correct permissions (`chmod +x` is usually handled). |
| **Docker: Connection Refused** | Ensure `docker-compose.yml` uses the service name `selenium-hub` as the host, not `localhost`. |
| **Git: Auth Failed** | Use Personal Access Token (PAT) instead of password for Git operations. |

---

## 6. 🚀 Advanced Framework Features Consumption

### 🔄 Database Validation (JDBC)
1.  **Configure URL**: Add your DB URL, username, and password to `config.properties`.
2.  **Usage**:
    ```java
    DBUtil.establishConnection(url, user, pass);
    List<Map<String, Object>> data = DBUtil.executeQuery("SELECT * FROM bookings WHERE id=1");
    DBUtil.closeConnection();
    ```

### ♿ Accessibility Checks (Axe-core)
1.  **Usage**: Call the static method anywhere in your UI tests after navigating to a page.
    ```java
    AccessibilityUtil.checkAccessibility(driver);
    ```
2.  **Results**: Violations (if any) are logged with impact levels (Critical, Serious, etc.).

### 👁️ Visual Regression (AShot)
1.  **Usage**: Perform a screenshot comparison.
    ```java
    VisualUtil.compareScreenshot(driver, "dashboard_view");
    ```
2.  **Baselines**: Baseline images are saved in `src/test/resources/baselines/`. Mismatches generate diff images in `target/visual-diffs/`.

### 🛡️ Security Scanning (OWASP ZAP)
1.  **Usage**: Enable ZAP proxy for Rest Assured or Selenium.
    ```java
    ZapUtil.setRestAssuredProxy(); // For API
    ChromeOptions options = ZapUtil.getZapChromeOptions(); // For UI
    ```
2.  **Requirements**: OWASP ZAP must be running on `localhost:8080`.

### 🧪 API POJO Handling (Lombok)
1.  **Usage**: Construct payloads using the `@Builder` pattern for cleaner code.
    ```java
    Booking payload = Booking.builder()
        .firstname("John")
        .lastname("Doe")
        .totalprice(150)
        .depositpaid(true)
        .bookingdates(BookingDates.builder().checkin("2024-01-01").checkout("2024-01-05").build())
        .build();
    ```

### ☁️ BrowserStack Execution
1.  **Usage**: Pass the BrowserStack grid URL via command line or properties.
    ```bash
    mvn test -Dgrid.url=https://YOUR_USER:YOUR_KEY@hub-cloud.browserstack.com/wd/hub
    ```

---
**Happy Testing!** 🕵️‍♂️
