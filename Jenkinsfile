
pipeline {
    agent any

    environment {
        JAVA8_HOME = '/opt/jdk8u422-b05'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh '''
                    export JAVA_HOME=$JAVA8_HOME
                    export PATH=$JAVA_HOME/bin:$PATH
                    mvn -v
                    mvn clean compile
                '''
            }
        }

        stage('Test') {
            steps {
                sh '''
                    export JAVA_HOME=$JAVA8_HOME
                    export PATH=$JAVA_HOME/bin:$PATH
                    mvn test
                '''
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Package') {
            steps {
                sh '''
                    export JAVA_HOME=$JAVA8_HOME
                    export PATH=$JAVA_HOME/bin:$PATH
                    mvn clean package
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    ansible-playbook \
                    -i ansible/environments/test/hosts \
                    ansible/playbooks/deploy.yml \
                    --extra-vars "ansible_become_pass=Devops@12345"
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh 'curl -f http://130.131.1.141:8080 || true'
            }
        }
    }
}

