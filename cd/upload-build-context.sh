#!/usr/bin/env bash

rm -rf cache/$TRAVIS_BUILD_NUMBER/
mkdir -p cache/$TRAVIS_BUILD_NUMBER/

mkdir -p cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-rest-api/target
mkdir -p cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-service/target
mkdir -p cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-web/target

cp -r record-jar-converter-rest-api/target/* cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-rest-api/target
cp -r record-jar-converter-service/target/* cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-service/target
cp -r record-jar-converter-web/target/* cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-web/target

docker save postremus/record-jar-converter-web postremus/record-jar-converter-rest-api -o cache/$TRAVIS_BUILD_NUMBER/images.tar
