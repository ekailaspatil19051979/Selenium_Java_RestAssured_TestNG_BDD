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
Ensure you have a `Jenkinsfile` in your project root. Here is a production-level Declarative Pipeline:

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.8.x' // Must match the name in Global Tool Configuration
        jdk 'Java 17'        // Must match the name in Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                // Pass dynamic parameters like browser or tags
                sh 'mvn test -Dbrowser=chrome -Dcucumber.filter.tags="@smoke"'
            }
        }
    }

    post {
        always {
            // Generate Allure Report
            allure includeProperties: false, results: [[path: 'target/allure-results']]
            
            // Archive standard junit results
            junit '**/target/surefire-reports/*.xml'
        }
        
        failure {
            echo "Pipeline failed! Sending email/slack notification..."
        }
    }
}
```

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
4.  Repository URL: Provide your repo URL (e.g., `https://github.com/user/repo.git`).
5.  Credentials: Add your Git credentials if the repo is private.
6.  Branch Specifier: `*/main` or `*/master`.
7.  Script Path: `Jenkinsfile`.

---

## Step 4: Configure Credentials (Secrets)
1.  Go to **Manage Jenkins** -> **Manage Credentials**.
2.  Add **Global Credentials** of type "Secret text" for sensitive data (API keys, passwords).
3.  Give them IDs (e.g., `db_pass`).
4.  In the `Jenkinsfile`, you can use these:
    ```groovy
    withCredentials([string(credentialsId: 'db_pass', variable: 'DB_PASSWORD')]) {
        sh 'mvn test -Ddb.password=$DB_PASSWORD'
    }
    ```

---

## Step 5: Run and Monitor
1.  Click **Build Now**.
2.  **Stage View**: Monitor the progress of each stage (Checkout -> Compile -> Test).
3.  **Console Output**: Click the build number then **Console Output** to see live Maven execution.
4.  **Allure Report**: Once finished, click the **Allure Report** icon on the build page to see detailed interactive reports.
