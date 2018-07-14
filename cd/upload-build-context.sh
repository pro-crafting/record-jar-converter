#!/usr/bin/env bash

rm -rf cache
mkdir cache

mkdir -p cache/record-jar-converter-rest-api/target
mkdir -p cache/record-jar-converter-service/target
mkdir -p cache/record-jar-converter-web/target

cp -r record-jar-converter-rest-api/target/* cache/record-jar-converter-rest-api/target
cp -r record-jar-converter-service/target/* cache/record-jar-converter-service/target
cp -r record-jar-converter-web/target/* cache/record-jar-converter-web/target

docker save postremus/record-jar-converter-web postremus/record-jar-converter-rest-api -o cache/images.tar

echo "===== debug"

ls -lR cache/