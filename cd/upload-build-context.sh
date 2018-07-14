#!/usr/bin/env bash

rm -rf cache
mkdir cache

cp -r record-jar-converter-rest-api/target cache/record-jar-converter-rest-api
cp -r record-jar-converter-service/target cache/record-jar-converter-service
cp -r record-jar-converter-web/target cache/record-jar-converter-web

docker save postremus/record-jar-converter-web postremus/record-jar-converter-rest-api -o cache/images.tar

echo "===== debug"

ls -lR cache/