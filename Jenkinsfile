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
                sh 'mvn install -P docker,docker-it,build-extras,jenkins-ci'
            }
        }
        stage ('Build and Deploy') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_IO_USERNAME', passwordVariable: 'DOCKER_IO_TOKEN'),
                    usernamePassword(credentialsId: 'ossrh', usernameVariable: 'OSSRH_USERNAME', passwordVariable: 'OSSRH_TOKEN'),
                    usernamePassword(credentialsId: 'gpg', usernameVariable: 'GPG_KEY_NAME', passwordVariable: 'GPG_PASSPHRASE'),
                    file(credentialsId: 'mavensigningkey', variable: 'MAVEN_SIGNING_KEY')
                ]) {
                    sh "gpg --batch --fast-import ${env.MAVEN_SIGNING_KEY}"
                    sh 'mvn deploy -s cd/settings.xml -P sign,docker,docker-it,build-extras,jenkins-ci'
                }
            }
        }
        stage ('Qualitiy - Sonar') {
            steps {
                withCredentials([
                    string(credentialsId: 'pro-crafting-sonarcloud', variable: 'SONARCLOUD_TOKEN')
                }) {
                    sh "mvn org.jacoco:jacoco-maven-plugin:prepare-agent org.apache.maven.plugins:maven-surefire-plugin:test org.sonarsource.scanner.maven:sonar-maven-plugin:sonar Dsonar.login=${env.SONARCLOUD_TOKEN}"
                }
            }
        }
    }
    post {
        always {
            junit 'build/reports/**/*.xml'
        }
    }
}