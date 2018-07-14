#!/usr/bin/env bash
mvn install -DskipITs -P docker,build-extras,!ws-it
