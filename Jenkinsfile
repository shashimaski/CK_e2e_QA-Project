pipeline {
    agent any

    stages {

        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean compile"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Package') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    ansible-playbook -i ansible/environments/test/hosts \
                    ansible/playbooks/deploy.yml --ask-become-pass
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh "curl -f http://130.131.1.141:8080 || true"
            }
        }
    }
}
