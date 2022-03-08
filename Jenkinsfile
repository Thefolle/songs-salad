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
      bat 'Build successful'
    }
  }
}
