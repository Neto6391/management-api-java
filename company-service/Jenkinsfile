pipeline {
    agent any

    tools {
        maven 'maven-3.9.8'
        jdk '17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                dir('company-service') {
                    sh 'ls -al'
                    sh 'mvn clean install'
                }
            }
        }
        stage('Test') {
            steps {
                dir('company-service') {
                    sh 'mvn test -Dspring.profiles.active=test'
                }
            }
        }
    }
}