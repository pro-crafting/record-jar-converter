#!/usr/bin/env bash
cd ./record-jar-converter-web/
mvn thorntail:start failsafe:integration-test failsafe:verify -P ws-it