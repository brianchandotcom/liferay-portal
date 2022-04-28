#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

if [[ ! -z "${EXPECTED_REF}" ]]; then
  IMAGE=${EXPECTED_REF}
fi

echo "[run_local] Build the customrestservice"

mvn clean package

docker build -t $IMAGE .