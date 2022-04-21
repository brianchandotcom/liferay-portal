#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the customrestservice"
mvn clean package
docker build -t $IMAGE .