#!/usr/bin/env bash
cd ./record-jar-converter-web/
mvn verify -P docker,docker-it,!ws-it