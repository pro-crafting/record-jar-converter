#!/usr/bin/env bash
cp cd/.travis.settings.xml $HOME/.m2/settings.xml
openssl aes-256-cbc -K $encrypted_2789e35462ef_key -iv $encrypted_2789e35462ef_iv -in cd/secret-files.tar.enc -out cd/secret-files.tar -d
mkdir cache
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    gpg --fast-import cd/codesigning.asc
#fi

mkdir record-jar-converter-rest-api/target
mkdir record-jar-converter-service/target
mkdir record-jar-converter-web/target

mkdir -p cache/record-jar-converter-rest-api/target
mkdir -p cache/record-jar-converter-service/target
mkdir -p cache/record-jar-converter-web/target

cp -r cache/record-jar-converter-rest-api/target record-jar-converter-rest-api/target
cp -r cache/record-jar-converter-service/target record-jar-converter-service/target
cp -r cache/record-jar-converter-web/target record-jar-converter-web/target