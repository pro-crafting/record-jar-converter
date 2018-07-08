#!/usr/bin/env bash
mvn install -DskipITs -P docker,!ws-it
