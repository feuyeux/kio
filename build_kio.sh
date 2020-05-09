#!/usr/bin/env bash
BUILD_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "Build java"
cd $BUILD_DIR
mvn clean install -DskipTests -U

echo "Build golang"
cd $BUILD_DIR/golang/kio-requester
go build

echo "Build rust"
cd $BUILD_DIR/rust/kio-requester
cargo build --release