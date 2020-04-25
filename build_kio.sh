#!/usr/bin/env bash
BUILD_DIR="$(cd "$(dirname "$0")" && pwd)"
cd $BUILD_DIR/kio-common
echo "build common jar"
mvn clean install -DskipTests
echo
cd $BUILD_DIR/kio-responder
echo "build responder jar"
mvn clean package -DskipTests
echo
cd $BUILD_DIR/kio-requester
echo "build requester jar"
mvn clean package -DskipTests
echo