pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RomainPellegrini/MyStatsLoL.git'
            }
        }
        stage('Checkout Backend') {
            steps {
                sh 'cd backend'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

    }
}
