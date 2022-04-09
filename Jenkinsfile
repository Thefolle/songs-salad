pipeline {
  agent any
  stages {
    stage('Build & Test') {
      steps {
        dir(path: 'songs-salad-main') {
          bat './mvnw package -DskipTests'
        }

      }
    }

  }
}