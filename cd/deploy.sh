#!/usr/bin/env bash
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn -Dmaven.test.skip=true deploy -P sign,build-extras,docker,!docker-it,!ws-it
#fi