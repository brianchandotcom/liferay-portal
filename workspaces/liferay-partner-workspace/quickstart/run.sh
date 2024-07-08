#!/bin/bash

docker compose up -d

pushd ..

./gradlew clean deploy "-Ddeploy.docker.container.id=liferay"

popd

docker logs --follow liferay