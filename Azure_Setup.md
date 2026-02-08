# ☁️ Azure DevOps Pipelines Setup Guide

This guide provides a step-by-step process to set up your automation framework in Azure DevOps from scratch.

---

## Prerequisites
1.  **Azure DevOps Account**: Sign up at [dev.azure.com](https://dev.azure.com).
2.  **Project Created**: Create a new project (e.g., `Enterprise-Automation`).
3.  **Code in Repos**: Your code must be pushed to **Azure Repos** or **GitHub**.

---

## Step 1: Push Code to Azure Repos (if using Azure Repos)
If your code is only local, push it to Azure Repos:
```bash
git remote add azure <your-azure-repo-url>
git push -u azure --all
```

---

## Step 2: Configure the Pipeline YAML
Ensure you have the `azure-pipeline.yml` file in your root directory. Here is a production-ready template:

```yaml
trigger:
- main

pool:
  vmImage: 'ubuntu-latest'

variables:
  maven_cache: $(Pipeline.Workspace)/.m2/repository

steps:
- task: Cache@2
  inputs:
    key: 'maven | "$(Agent.OS)" | pom.xml'
    restoreKeys: |
      maven | "$(Agent.OS)"
    path: $(maven_cache)
  displayName: Cache Maven packages

- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'clean test'
    options: '-Dcucumber.filter.tags="@smoke"'
    publishJUnitResults: true
    testResultsFiles: '**/surefire-reports/TEST-*.xml'
    javaHomeOption: 'JDKVersion'
    jdkVersionOption: '1.17'
    mavenVersionOption: 'Default'
    mavenAuthenticateFeed: false
    effectivePomSkip: false
    sonarQubeRunAnalysis: false

- task: PublishTestResults@2
  condition: succeededOrFailed()
  inputs:
    testResultsFiles: '**/surefire-reports/TEST-*.xml'
    testRunTitle: 'Automation Test Results'

- task: PublishBuildArtifacts@1
  condition: always()
  inputs:
    PathtoPublish: 'target/allure-results'
    ArtifactName: 'allure-results'
    publishLocation: 'Container'
```

---

## Step 3: Create the Pipeline in Azure DevOps
1.  **Navigate**: Go to **Pipelines** -> **Pipelines** in your project.
2.  **New Pipeline**: Click the **New Pipeline** button.
3.  **Source**: Select **Azure Repos Git** (or GitHub).
4.  **Repository**: Select your `automation-framework` repository.
5.  **Configure**: Select **Existing Azure Pipelines YAML file**.
6.  **Path**: Choose `/azure-pipeline.yml` from the dropdown.
7.  **Run**: Click **Run**.

---

## Step 4: Add Environment Variables (Secrets)
If your tests require credentials, do **not** hardcode them:
1.  In the Pipeline view, click **Edit**.
2.  Click the **Variables** button.
3.  Add new variables (e.g., `DB_PASSWORD`, `API_KEY`).
4.  Check **Keep this value secret**.
5.  In your Java code, access these via `System.getenv("DB_PASSWORD")`.

---

## Step 5: Viewing Results
1.  **Summary**: After execution, the "Tests" tab in Azure Pipelines shows pass/fail counts.
2.  **Artifacts**: Download the `allure-results` from the **Published** artifacts section to view detailed reports locally if needed.
3.  **Logs**: Click on the **Maven** task in the job execution view to see real-time console output.
