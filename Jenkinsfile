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
          junit(allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml')
        }

      }
    }

    stage('Notify') {
      steps {
        emailext(subject: '$DEFAULT_SUBJECT', to: 'prova@yopmail.com', body: '$DEFAULT_CONTENT')
      }
    }

  }
}