#!/usr/bin/env bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -P !ws-it -Dsonar.login=$SONAR_TOKEN