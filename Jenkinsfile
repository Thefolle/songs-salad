pipeline {
  agent any
  stages {
    stage('Build & Test') {
      steps {
        dir(path: 'songs-salad-main') {
          bat(script: './mvnw clean', returnStdout: true)
          bat(script: './mvnw package', returnStdout: true)
        }
      }
    }
  }
}
