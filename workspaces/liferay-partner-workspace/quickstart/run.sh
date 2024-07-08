#!/bin/bash

docker compose up -d

sleep 10s

pushd ..

./gradlew clean deploy "-Ddeploy.docker.container.id=liferay"

popd

docker logs -f liferay