pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3'
        PATH = "${JAVA_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}"
    }

    stages {
        stage('Checkout GitHub Repo') {
            steps {
                checkout scmGit(
                        branches: [[name: '*/master']],
                        userRemoteConfigs: [[
                                                    url: 'https://github.com/ADirin/non-functional-testing-jmeter.git'
                                                    // credentialsId: 'github-credentials-id' // Uncomment if your repo is private
                                            ]]
                )
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
    }

    post {
        always {
            archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true
            perfReport sourceDataFiles: 'result.jtl'
        }
    }
}