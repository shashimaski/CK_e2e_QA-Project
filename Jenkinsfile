pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Tools') {
            steps {
                sh '''
                    apt-get update || true
                    apt-get install -y maven curl || true
                    mvn -version
                '''
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
                sh '''
                    apt-get install -y ansible sshpass || true
                    ansible-playbook -i ansible/environments/test/hosts \
                    ansible/playbooks/deploy.yml
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
