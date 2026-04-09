pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RomainPellegrini/MyStatsLoL.git'
            }
        }

        stage('Build & Start Services') {
            steps {
                withCredentials([
                    string(credentialsId: 'POSTGRES_USER', variable: 'POSTGRES_USER'),
                    string(credentialsId: 'POSTGRES_PASSWORD', variable: 'POSTGRES_PASSWORD'),
                    string(credentialsId: 'POSTGRES_DB', variable: 'POSTGRES_DB'),
                    string(credentialsId: 'RIOT_API_KEY', variable: 'RIOT_API_KEY')
                ]) {
                    sh 'docker-compose -f docker-compose.yml build'
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }

        stage('Run Tests') {
            steps {
                withCredentials([
                    string(credentialsId: 'RIOT_API_KEY', variable: 'RIOT_API_KEY')
                ]) {
                    sh 'docker-compose -f docker-compose.yml exec -T backend mvn clean test'
                }
            }
        }
    }

    post {
        always {
            // Nettoyage des conteneurs
            sh 'docker-compose -f docker-compose.yml down || true'

            // Récupération des rapports JUnit
            sh '''
                mkdir -p backend-reports
                docker cp $(docker-compose -f docker-compose.yml ps -q backend):/app/target/surefire-reports/. backend-reports/ || true
            '''
            junit 'backend-reports/*.xml'
        }
    }
}
