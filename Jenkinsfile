pipeline {
    agent any

    environment {
        IMAGE_NAME = 'thiernosouleymanediallo/sunusante-backend'
        IMAGE_TAG  = "${BUILD_NUMBER}"
    }

    stages {
        stage('Compiler') {
            steps {
                sh 'mvn -B compile'
            }
        }
        stage('Tests unitaires') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Construire l image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest ."
            }
        }
        stage('Publier sur Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_TOKEN')]) {
                    sh 'echo "$DOCKER_TOKEN" | docker login -u "$DOCKER_USER" --password-stdin'
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
		    sh "docker push ${IMAGE_NAME}:latest"
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
        success {
            echo "Image ${IMAGE_NAME}:${IMAGE_TAG} publiee sur Docker Hub"
        }
        failure {
            echo 'Echec du pipeline CI backend'
        }
    }
}