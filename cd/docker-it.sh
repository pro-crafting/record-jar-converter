#!/usr/bin/env bash
cd ./record-jar-converter-web/
mvn verify -P docker-it,!ws-it