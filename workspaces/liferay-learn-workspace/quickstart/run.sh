#!/bin/bash

if [ ! -f ".env" ]
then
	echo ".env file not found. Creating a new one..."
	touch .env
fi

source .env

if [[ -z "${LIFERAY_LEARN_DIR}" ]]
then

	default=../../../../liferay-learn

	echo "Please enter the value for 'LIFERAY_LEARN_DIR': [${default}]"

	read -p "> " LIFERAY_LEARN_DIR

	LIFERAY_LEARN_DIR="${LIFERAY_LEARN_DIR:-${default}}"

	LIFERAY_LEARN_DIR="$(readlink -f "${LIFERAY_LEARN_DIR}")"

	echo "LIFERAY_LEARN_DIR=${LIFERAY_LEARN_DIR}" >> .env

	echo "Environment variable 'LIFERAY_LEARN_DIR' added to .env file."

	source .env
fi

docker build -t liferay-learn-workspace/bootstrap:latest docker/bootstrap

cd ..

docker run --name learn-bootstrap -v ./:/mnt/liferay-learn-workspace liferay-learn-workspace/bootstrap:latest

docker rm learn-bootstrap

cd quickstart || exit

docker compose up --build