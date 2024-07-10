#!/bin/bash

pushd .. > /dev/null

./gradlew clean deploy

popd > /dev/null

docker compose up