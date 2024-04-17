#!/bin/bash

if [ ! -f ".env" ]
then
	echo ".env file not found. Creating a new one..."
	touch .env
fi

source .env

if [[ -z "${LIFERAY_LEARN_DIR}" ]]
then

	default=/home/me/dev/projects/liferay-learn

	echo "Please enter the value for 'LIFERAY_LEARN_DIR': [$default]"
		read -p "> " LIFERAY_LEARN_DIR
			LIFERAY_LEARN_DIR=${LIFERAY_LEARN_DIR:-$default}

	echo "LIFERAY_LEARN_DIR=${LIFERAY_LEARN_DIR}" >> .env

	echo "Environment variable 'LIFERAY_LEARN_DIR' added to .env file."

	source .env
fi

docker compose up --build