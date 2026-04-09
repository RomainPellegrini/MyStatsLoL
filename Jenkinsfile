pipeline {
    agent {
        docker {
            image 'maven:4.0.0-openjdk-21' // Maven + Java 21
        }
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RomainPellegrini/MyStatsLoL.git'
            }
        }

        stage('Run Tests') {
            steps {
                dir('backend') { // se place dans le dossier backend
                    sh 'mvn clean test'
                }
            }
        }

    }

    post {
        always {
            junit 'backend/target/surefire-reports/*.xml'
        }
    }
}
