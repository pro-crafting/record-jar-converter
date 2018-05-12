#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn deploy -P sign,build-extras,docker
else
    mvn install -P build-extras,docker
fi
