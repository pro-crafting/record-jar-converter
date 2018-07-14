#!/usr/bin/env bash
cd ./record-jar-converter-web/
mvn wildfly-swarm:start failsafe:integration-test failsafe:verify -P ws-it