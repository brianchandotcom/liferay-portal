#!/bin/bash

pushd ..

./gradlew clean deploy

popd

docker compose up