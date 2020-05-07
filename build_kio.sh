#!/usr/bin/env bash
BUILD_DIR="$(cd "$(dirname "$0")" && pwd)"
cd $BUILD_DIR
mvn clean install -DskipTests -U

cd $BUILD_DIR/golang/kio-requester
go build

cd $BUILD_DIR/rust/kio-requester
cargo build