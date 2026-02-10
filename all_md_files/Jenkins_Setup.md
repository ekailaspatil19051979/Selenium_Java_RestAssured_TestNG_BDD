# 🏗️ Jenkins CI/CD Setup Guide

This guide details how to set up a Jenkins Pipeline for your automation framework from scratch.

---

## Prerequisites
1.  **Jenkins Installed**: Installed on a server or locally (e.g., [localhost:8080](http://localhost:8080)).
2.  **Plugins Installed**:
    *   Pipeline
    *   Git
    *   Maven Integration
    *   Allure Jenkins Plugin (optional but recommended)
3.  **Global Tool Configuration**: Ensure **JDK 17+** and **Maven** are configured in `Manage Jenkins` -> `Global Tool Configuration`.

---

## Step 1: Create the Jenkinsfile
Ensure you have a `Jenkinsfile` in your project root. Here is a production-level Declarative Pipeline that works on **both Windows and Unix**:

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3'  // Must match the name in Global Tool Configuration
        jdk 'JDK 17'     // Must match the name in Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Use bat for Windows, sh for Unix/Linux
                    if (isUnix()) {
                        sh 'mvn clean compile'
                    } else {
                        bat 'mvn clean compile'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    try {
                        if (isUnix()) {
                            sh 'mvn test'
                        } else {
                            bat 'mvn test'
                        }
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "Tests failed but continuing pipeline: ${e.message}"
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    try {
                        allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                    } catch (Exception e) {
                        echo "Allure report generation failed: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Archive HTML reports if they exist
                archiveArtifacts artifacts: 'target/**/*.html', allowEmptyArchive: true
                
                // Publish test results if they exist
                try {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                } catch (Exception e) {
                    echo "No test results found: ${e.message}"
                }
            }
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        unstable {
            echo 'Pipeline is unstable (some tests failed)'
        }
    }
}
```

**Key Features:**
- ✅ **Cross-platform**: Uses `bat` for Windows, `sh` for Unix/Linux
- ✅ **Error handling**: Catches exceptions and continues pipeline
- ✅ **Flexible**: Allows empty test results and archives

---

## Step 2: Create a New Pipeline Job
1.  Open Jenkins.
2.  Click **New Item**.
3.  Enter name: `Automation-Framework-Pipeline`.
4.  Select **Pipeline** and click **OK**.

---

## Step 3: Configure Source Control (SCM)
1.  Scroll down to the **Pipeline** section.
2.  Definition: Select **Pipeline script from SCM**.
3.  SCM: Select **Git**.
4.  Repository URL: Provide your repo URL (e.g., `https://github.com/ekailaspatil19051979/Selenium_Java_RestAssured_TestNG_BDD.git`).
5.  Credentials: Add your Git credentials if the repo is private.
6.  Branch Specifier: `*/main` or `*/master`.
7.  Script Path: `Jenkinsfile`.

---

## Step 4: Configure Global Tools
1.  Go to **Manage Jenkins** → **Global Tool Configuration**.
2.  **JDK Configuration**:
    - Click **Add JDK**
    - Name: `JDK 17` (must match Jenkinsfile)
    - JAVA_HOME: `C:\Program Files\Java\jdk-17` (Windows) or `/usr/lib/jvm/java-17-openjdk` (Linux)
3.  **Maven Configuration**:
    - Click **Add Maven**
    - Name: `Maven 3` (must match Jenkinsfile)
    - Install automatically OR specify MAVEN_HOME path

---

## Step 5: Configure Credentials (Secrets)
1.  Go to **Manage Jenkins** → **Manage Credentials**.
2.  Add **Global Credentials** of type "Secret text" for sensitive data (API keys, passwords).
3.  Give them IDs (e.g., `db_pass`).
4.  In the `Jenkinsfile`, you can use these:
    ```groovy
    withCredentials([string(credentialsId: 'db_pass', variable: 'DB_PASSWORD')]) {
        bat 'mvn test -Ddb.password=%DB_PASSWORD%'  // Windows
        // OR
        sh 'mvn test -Ddb.password=$DB_PASSWORD'    // Unix
    }
    ```

---

## Step 6: Run and Monitor
1.  Click **Build Now**.
2.  **Stage View**: Monitor the progress of each stage (Checkout → Build → Test).
3.  **Console Output**: Click the build number then **Console Output** to see live Maven execution.
4.  **Allure Report**: Once finished, click the **Allure Report** icon on the build page to see detailed interactive reports.

---

## Troubleshooting Common Issues

### Issue 1: "Cannot run program 'sh'" on Windows
**Error**: `CreateProcess error=2, The system cannot find the file specified`

**Solution**: Your Jenkinsfile is using `sh` command on Windows. Use the updated Jenkinsfile above that uses `bat` for Windows.

### Issue 2: "No test report files were found"
**Error**: `hudson.AbortException: No test report files were found`

**Solution**: 
- Tests didn't run due to earlier build failure
- Use `allowEmptyResults: true` in junit step (already in updated Jenkinsfile)
- Check that tests actually execute: `mvn test` should create `target/surefire-reports/*.xml`

### Issue 3: Tool not found (Maven/JDK)
**Error**: `Tool type "maven" does not have an install of "Maven 3" configured`

**Solution**:
1. Go to **Manage Jenkins** → **Global Tool Configuration**
2. Add Maven/JDK with the EXACT name used in Jenkinsfile
3. Names are case-sensitive: `Maven 3` ≠ `maven 3`

### Issue 4: Git authentication failed
**Error**: `Permission denied (publickey)` or `Authentication failed`

**Solution**:
- Use HTTPS URL instead of SSH: `https://github.com/user/repo.git`
- Add credentials in Jenkins: **Manage Jenkins** → **Manage Credentials**
- For private repos, use Personal Access Token as password

### Issue 5: Allure plugin not found
**Error**: `No such DSL method 'allure' found`

**Solution**:
1. Go to **Manage Jenkins** → **Manage Plugins**
2. Search for "Allure Jenkins Plugin"
3. Install and restart Jenkins

---

## Advanced Configuration

### Parameterized Builds
Add parameters to run different test suites:

```groovy
pipeline {
    agent any
    
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser')
        choice(name: 'TAGS', choices: ['@smoke', '@regression', '@all'], description: 'Select test tags')
    }
    
    stages {
        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh "mvn test -Dbrowser=${params.BROWSER} -Dcucumber.filter.tags='${params.TAGS}'"
                    } else {
                        bat "mvn test -Dbrowser=${params.BROWSER} -Dcucumber.filter.tags=\"${params.TAGS}\""
                    }
                }
            }
        }
    }
}
```

### Scheduled Builds (Cron)
Add to pipeline to run tests automatically:

```groovy
pipeline {
    agent any
    
    triggers {
        cron('H 2 * * *')  // Run at 2 AM daily
    }
    // ... rest of pipeline
}
```

### Email Notifications
```groovy
post {
    failure {
        emailext (
            subject: "Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
            body: "Check console output at ${env.BUILD_URL}",
            to: "team@example.com"
        )
    }
}
```

---

## Best Practices

1. ✅ **Always use `allowEmptyResults: true`** for junit to prevent build failures when no tests run
2. ✅ **Use `isUnix()` check** for cross-platform compatibility
3. ✅ **Add try-catch blocks** around stages that might fail
4. ✅ **Archive artifacts** with `allowEmptyArchive: true`
5. ✅ **Use meaningful stage names** for better visibility
6. ✅ **Add post-build actions** for notifications and cleanup
7. ✅ **Keep credentials in Jenkins** - never hardcode in Jenkinsfile

---

## Next Steps
- Set up webhook triggers for automatic builds on Git push
- Configure parallel execution for faster test runs
- Integrate with Slack/Teams for notifications
- Set up Docker agents for isolated test environments
