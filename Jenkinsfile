pipeline {
    agent {
        docker {
            image 'maven:4.0.0-openjdk-21'
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
                dir('backend') {
                    sh 'mvn test'
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
