#!/usr/bin/env bash
cd ./record-jar-converter-web/
mvn -Dmaven.javadoc.skip=true -DskipTests verify -P docker-it,!ws-it