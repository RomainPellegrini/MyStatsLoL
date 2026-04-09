pipeline {
    agent any

    environment {
        DOCKERHUB_USERNAME = 'romainpellegrini'
        APP_IMAGE = "${DOCKERHUB_USERNAME}/mystatslol-app"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    triggers {
        githubPush()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Login Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'DOCKERHUB_CREDENTIALS',
                        usernameVariable: 'DOCKERHUB_USER',
                        passwordVariable: 'DOCKERHUB_PASSWORD'
                    )
                ]) {
                    sh 'echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USER" --password-stdin'
                }
            }
        }

        stage('Build Image') {
            steps {
                withCredentials([
                    string(credentialsId: 'POSTGRES_USER',     variable: 'POSTGRES_USER'),
                    string(credentialsId: 'POSTGRES_PASSWORD', variable: 'POSTGRES_PASSWORD'),
                    string(credentialsId: 'POSTGRES_DB',       variable: 'POSTGRES_DB'),
                    string(credentialsId: 'RIOT_API_KEY',      variable: 'RIOT_API_KEY')
                ]) {
                    sh '''
                        docker compose build \
                            --build-arg POSTGRES_USER="$POSTGRES_USER" \
                            --build-arg POSTGRES_PASSWORD="$POSTGRES_PASSWORD" \
                            --build-arg POSTGRES_DB="$POSTGRES_DB" \
                            --build-arg RIOT_API_KEY="$RIOT_API_KEY"
                    '''
                }
            }
        }

        stage('Tag Image') {
            steps {
                sh '''
                    docker tag mystatslol-app:latest ${APP_IMAGE}:${IMAGE_TAG}
                    docker tag mystatslol-app:latest ${APP_IMAGE}:latest
                '''
            }
        }

        stage('Push Image') {
            steps {
                sh '''
                    docker push ${APP_IMAGE}:${IMAGE_TAG}
                    docker push ${APP_IMAGE}:latest
                '''
            }
        }

    }

    post {
        always {
            sh 'docker logout || true'
            sh 'docker compose down --rmi local --volumes --remove-orphans || true'
        }
        success {
            echo "✅ Images pushées avec succès sur Docker Hub (tag: ${IMAGE_TAG})"
        }
        failure {
            echo "❌ Le pipeline a échoué — vérifie les logs ci-dessus"
        }
    }
}
