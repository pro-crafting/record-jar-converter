#!/usr/bin/env bash
docker load -i cache/images.tar

cd ./record-jar-converter-web/
mvn docker:start failsafe:integration-test docker:stop failsafe:verify -P docker,docker-it,!ws-it -o