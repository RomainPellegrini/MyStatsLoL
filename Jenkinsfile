pipeline {
     agent { label 'Jenkins Worker' }
     tools {
        maven 'Maven 4.0.0'
        jdk 'JDK'
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
