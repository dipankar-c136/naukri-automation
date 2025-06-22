pipeline {
    agent any

    triggers {
        cron('15 9 * * *') // Runs daily at 9:15 AM IST
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/dipankar-c136/naukri-automation.git' // Replace with your repository URL
            }
        }

        stage('Build') {
            steps {
                script {
                    // Run Maven build
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests using TestNG
                    sh 'mvn test'
                }
            }
        }
    }

    post {
        success {
            mail to: 'dc954725@gmail.com', // Replace with your email
                 subject: "Jenkins Job '${env.JOB_NAME}' Success",
                 body: "The job '${env.JOB_NAME}' completed successfully. Check the console output for details: ${env.RUN_DISPLAY_URL}"
        }
        failure {
            mail to: 'your-email@example.com', // Replace with your email
                 subject: "Jenkins Job '${env.JOB_NAME}' Failed",
                 body: "The job '${env.JOB_NAME}' failed. Check the console output for details: ${env.RUN_DISPLAY_URL}"
        }
    }
}