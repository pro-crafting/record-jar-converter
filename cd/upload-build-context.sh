#!/usr/bin/env bash
cp -r record-jar-converter-rest-api/target cache/record-jar-converter-rest-api
cp -r record-jar-converter-service/target cache/record-jar-converter-service
cp -r record-jar-converter-web/target cache/record-jar-converter-web

docker save record-jar-converter-web record-jar-converter-rest-api -o cache/images.tar