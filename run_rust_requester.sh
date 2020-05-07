#!/usr/bin/env bash
BUILD_DIR="$(cd "$(dirname "$0")" && pwd)"
cd $BUILD_DIR/rust/kio-requester
./kio-requester