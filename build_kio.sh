#!/usr/bin/env bash
BUILD_DIR="$(cd "$(dirname "$0")" && pwd)"
mvn clean package -DskipTests -U

build_one_by_one() {
cd $BUILD_DIR/kio-common
echo "build common"
mvn -U clean install -DskipTests
echo
cd $BUILD_DIR/kio-responder
echo "build responder"
mvn clean package -DskipTests -U
echo
cd $BUILD_DIR/kio-requester
echo "build requester"
mvn clean package -DskipTests -U
echo "done"
}

# build_one_by_one