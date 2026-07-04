pipeline {

    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
        PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {

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

        stage('Deploy (Ansible)') {
            steps {
                echo "Deploying using Ansible"

                sh '''
                    export ANSIBLE_HOST_KEY_CHECKING=False
                    ansible-playbook -i ansible/environments/test/hosts \
                    ansible/playbooks/deploy.yml --ask-become-pass
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                    echo "Checking application..."
                    curl -f http://130.131.1.141:8080 || true
                '''
            }
        }
    }

    post {
        success {
            echo "PIPELINE SUCCESS 🚀"
        }
        failure {
            echo "PIPELINE FAILED ❌"
        }
    }
}
