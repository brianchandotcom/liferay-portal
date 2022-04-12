#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the dynamicjs PoC"

docker build -t $IMAGE .
