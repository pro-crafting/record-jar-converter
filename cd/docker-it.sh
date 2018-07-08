#!/usr/bin/env bash
mvn -Dmaven.javadoc.skip=true -Dmaven.test.skip=true verify -P docker-it,!ws-it