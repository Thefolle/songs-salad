pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'set'
      }
    }

    stage('Test') {
      steps {
        echo 'Run system tests'
      }
    }

  }
  post {
    success {
      echo env.ENV_EXAMPLE
    }

  }
}