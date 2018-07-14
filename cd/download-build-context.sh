#!/usr/bin/env bash

echo "==== 1"
ls record-jar-converter-rest-api/target
ls record-jar-converter-service/target
ls record-jar-converter-web/target
echo "==== 2"
ls -lR cache/
echo "==== 3"

mkdir record-jar-converter-rest-api/target
mkdir record-jar-converter-service/target
mkdir record-jar-converter-web/target

mkdir -p cache/record-jar-converter-rest-api/target
mkdir -p cache/record-jar-converter-service/target
mkdir -p cache/record-jar-converter-web/target

cp -r cache/record-jar-converter-rest-api/target record-jar-converter-rest-api
cp -r cache/record-jar-converter-service/target record-jar-converter-service
cp -r cache/record-jar-converter-web/target record-jar-converter-web


echo "==== 4"
ls record-jar-converter-rest-api/target
ls record-jar-converter-service/target
ls record-jar-converter-web/target
echo "==== 5"
ls -lR cache/
echo "==== 6"