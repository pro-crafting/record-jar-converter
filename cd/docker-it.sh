#!/usr/bin/env bash
docker load -i cache/images.tar

cd ./record-jar-converter-web/
mvn verify -P docker,docker-it,!ws-it