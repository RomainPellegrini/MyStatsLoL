pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RomainPellegrini/MyStatsLoL.git'
            }
        }
        stage('Run Tests') {
            steps {
                dir('backend') {
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
