#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'maven-default'
        jdk 'openjdk8-zulu'
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn install -s cd/settings.xml -P docker,docker-it,build-extras'
            }
        }
        stage ('Build and Deploy') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_IO_USERNAME', passwordVariable: 'DOCKER_IO_TOKEN'),
                    usernamePassword(credentialsId: 'ossrh', usernameVariable: 'OSSRH_USERNAME', passwordVariable: 'OSSRH_TOKEN'),
                    usernamePassword(credentialsId: 'gpg', usernameVariable: 'GPG_KEY_NAME', passwordVariable: 'GPG_PASSPHRASE')
                ]) {
                    sh 'mvn deploy -s cd/settings.xml -P sign,docker,docker-it,build-extras'
                }
            }
        }
        stage ('Qualitiy - Sonar') {
            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar'
        }
        post {
            always {
                junit 'build/reports/**/*.xml'
            }
        }
    }
}