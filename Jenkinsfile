pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        dir(path: 'songs-salad-main') {
          bat './mvnw package -DskipTests'
        }

      }
    }

    stage('Integration Test') {
      steps {
        dir(path: 'songs-salad-main') {
          bat './mvnw verify -DskipUnitTests -Dmaven.main.skip'
          junit(allowEmptyResults: true, testResults: '**/surefire-reports/TEST-*.xml')
        }

      }
    }

  }
}