#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn deploy -P sign,docker,docker-it,build-extras
else
    mvn install -P docker,docker-it,build-extras
fi