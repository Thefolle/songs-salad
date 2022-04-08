pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        dir(path: 'songs-salad-main') {
          bat(script: './mvnw clean', returnStdout: true)
          bat(script: './mvnw compile', returnStdout: true)
        }

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