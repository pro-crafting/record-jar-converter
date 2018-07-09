#!/usr/bin/env bash
cp cd/.travis.settings.xml $HOME/.m2/settings.xml
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_2789e35462ef_key -iv $encrypted_2789e35462ef_iv -in cd/codesigning.asc.enc -out cd/codesigning.asc -d
    gpg --fast-import cd/codesigning.asc
#fi

#Add public key for data@tm.pro-crafting.com
openssl aes-256-cbc -K $encrypted_2789e35462ef_key -iv $encrypted_2789e35462ef_iv -in cd/id_rsa_data.pub.enc -out cd/id_rsa_data.pub -d
copy cd/id_rsa_data.pub ~/.id_rsa_data.pub
chmod 0644 ~/.id_rsa_data.pub
scp data@tm.pro-crafting.com/home/data/$TRAVIS_BUILD_NUMBER/ ./