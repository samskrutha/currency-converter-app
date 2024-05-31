pipeline {
    agent any

    environment {
        DOCKER_IMAGE_BACKEND = 'samskrutha/my-backend-app'
        DOCKER_IMAGE_FRONTEND = 'samskrutha/my-frontend-app'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/samskrutha/currency-converter-app.git', credentialsId: 'github'
            }
        }

        stage('Build and Test Backend') {
            steps {
                dir('backend') {
                    script {
                        docker.build("${DOCKER_IMAGE_BACKEND}:latest")
                    }
                }
            }
        }

        stage('Build and Test Frontend') {
            steps {
                dir('frontend') {
                    script {
                        docker.build("${DOCKER_IMAGE_FRONTEND}:latest")
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-credentials') {
                        docker.image("${DOCKER_IMAGE_BACKEND}:latest").push()
                        docker.image("${DOCKER_IMAGE_FRONTEND}:latest").push()
                    }
                }
            }
        }

        stage('Update Helm Chart Versions') {
            steps {
                dir('helm/backend') {
                    script {
                        sh 'helm package .'
                        sh 'helm repo index .'
                        sh 'git add .'
                        sh 'git commit -m "Update backend chart version"'
                        sh 'git push origin main'
                    }
                }
                dir('helm/frontend') {
                    script {
                        sh 'helm package .'
                        sh 'helm repo index .'
                        sh 'git add .'
                        sh 'git commit -m "Update frontend chart version"'
                        sh 'git push origin main'
                    }
                }
            }
        }

        stage('Deploy with ArgoCD') {
            steps {
                script {
                    withKubeConfig([credentialsId: "${KUBECONFIG_CREDENTIAL_ID}"]) {
                        sh 'argocd app sync backend-app'
                        sh 'argocd app sync frontend-app'
                    }
                }
            }
        }
    }

    post {
        success {
            slackSend (channel: '#jenkins', color: 'good', message: "Build and Deployment Successful: ${env.JOB_NAME} - ${env.BUILD_NUMBER}")
        }
        failure {
            slackSend (channel: '#jenkins', color: 'danger', message: "Build and Deployment Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}")
        }
    }
}
