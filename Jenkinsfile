pipeline {
    agent any

    environment {
        COMPOSE_PROJECT_NAME = 'mystatslol'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RomainPellegrini/MyStatsLoL.git'
            }
        }

        stage('Build & Start Services') {
            steps {
                sh 'docker-compose -f docker-compose.yml build'
                sh 'docker-compose -f docker-compose.yml up -d'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'docker-compose -f docker-compose.yml exec -T backend mvn clean test'
            }
        }
    }

    post {
        always {
            // Stoppe et nettoie les conteneurs Docker
            sh 'docker-compose -f docker-compose.yml down || true'

            // Copie des rapports JUnit (optionnel)
            sh '''
                mkdir -p backend-reports
                docker cp $(docker-compose -f docker-compose.yml ps -q backend):/app/target/surefire-reports/. backend-reports/ || true
            '''
            junit 'backend-reports/*.xml'
        }
    }
}
