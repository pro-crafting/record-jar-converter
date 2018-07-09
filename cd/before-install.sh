#!/usr/bin/env bash
cp cd/.travis.settings.xml $HOME/.m2/settings.xml
openssl aes-256-cbc -K $encrypted_2789e35462ef_key -iv $encrypted_2789e35462ef_iv -in cd/secret-files.tar.enc -out cd/secret-files.tar -d
cd cd/
tar xvf cd/secret-files.tar
cd ..

#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    gpg --fast-import cd/codesigning.asc
#fi

#Add public key for data@tm.pro-crafting.com
cp cd/id_rsa_data.pub ~/.ssh/.id_rsa_data.pub
chmod 0644 ~/.ssh/.id_rsa_data.pub
scp data@tm.pro-crafting.com/home/data/$TRAVIS_BUILD_NUMBER/ ./