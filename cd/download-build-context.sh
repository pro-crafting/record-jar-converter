#!/usr/bin/env bash

mkdir record-jar-converter-rest-api/target
mkdir record-jar-converter-service/target
mkdir record-jar-converter-web/target

cp -r cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-rest-api/target/* record-jar-converter-rest-api/target/
cp -r cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-service/target/* record-jar-converter-service/target/
cp -r cache/$TRAVIS_BUILD_NUMBER/record-jar-converter-web/target/* record-jar-converter-web/target/
