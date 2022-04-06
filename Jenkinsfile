pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'set'
            }
        }
    }
  post {
    success {
      echo env.ENV_EXAMPLE
    }
  }
}
