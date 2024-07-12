#!/bin/bash

docker compose up -d

pushd .. > /dev/null

if [ -f "quickstart/deploy.list" ]
then
	for line in $(cat "quickstart/deploy.list")
	do
		if [ -z "${line}" ]
		then
			continue
		fi

		echo "Deploying ${line}"

		./gradlew ${line}:clean ${line}:deploy
	done
else
	echo "Deploying $(ls client-extensions)"

	./gradlew clean deploy
fi

popd > /dev/null

docker compose up --no-recreate 