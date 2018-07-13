#!/usr/bin/env bash
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn -Dmaven.test.skip=true deploy -P sign,!docker-it,!ws-it


    docker load -i cache/images.tar
    docker push postremus/record-jar-converter-web
    docker push postremus/record-jar-converter-rest-api
#fi