pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3'
        PATH = "${JAVA_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}"
        SONARQUBE_SERVER = 'SonarQubeServer'
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
    }

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout GitHub Repo') {
            steps {
                checkout scmGit(
                        branches: [[name: '*/master']],
                        userRemoteConfigs: [[
                                                    url: 'https://github.com/ADirin/non-functional-testing-jmeter.git'
                                            ]]
                )
            }
        }

        stage('Build & Unit Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Non-Functional Test') {
            steps {
                bat '''
                    if exist result.jtl del /f /q result.jtl
                    jmeter -n -t tests/performance/demo.jmx -l result.jtl
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                    bat """
                        sonar-scanner ^
                        -Dsonar.projectKey=DemoApp ^
                        -Dsonar.projectName=DemoApp ^
                        -Dsonar.sources=src/main/java ^
                        -Dsonar.tests=src/test/java ^
                        -Dsonar.java.binaries=target/classes ^
                        -Dsonar.java.test.binaries=target/test-classes ^
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.token=${env.SONAR_TOKEN}
                    """
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true
            script {
                if (fileExists('result.jtl')) {
                    perfReport sourceDataFiles: 'result.jtl'
                }
            }
        }
    }
}