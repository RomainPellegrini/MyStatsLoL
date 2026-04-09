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

        stage('Build Docker Images') {
            steps {
                // Build les images définies dans docker-compose.yml
                sh 'docker-compose -f docker-compose.yml build'
            }
        }

        stage('Start Services') {
            steps {
                // Démarre les services en arrière-plan
                sh 'docker-compose -f docker-compose.yml up -d'
            }
        }

        stage('Run Tests') {
            steps {
                // On peut exécuter les tests via Maven dans le conteneur backend
                sh 'docker-compose -f docker-compose.yml exec -T backend mvn clean test'
            }
        }
    }

    post {
        always {
            // Collecte les rapports JUnit depuis le conteneur backend
            sh 'docker cp $(docker-compose -f docker-compose.yml ps -q backend):/app/target/surefire-reports ./backend-reports || true'
            junit 'backend-reports/*.xml'

            // Arrête et supprime les conteneurs
            sh 'docker-compose -f docker-compose.yml down'
        }

        success {
            echo 'Déploiement terminé avec succès !'
        }

        failure {
            echo 'Il y a eu des erreurs pendant le pipeline.'
        }
    }
}
