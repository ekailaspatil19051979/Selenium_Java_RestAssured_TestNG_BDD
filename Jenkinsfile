pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
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
