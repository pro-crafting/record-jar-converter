#!/usr/bin/env bash
cp cd/.travis.settings.xml $HOME/.m2/settings.xml
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_2789e35462ef_key -iv $encrypted_2789e35462ef_iv -in cd/codesigning.asc.enc -out cd/codesigning.asc -d
    gpg --fast-import cd/codesigning.asc
fi
