#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the custom-rest-service"
mvn clean package
docker build -t "custom-rest-service:1.0.0-SNAPSHOT" .

kubectl delete deployments.apps custom-rest-service --wait=true --ignore-not-found=true
kubectl apply -f ../../k8s/custom-rest-service/

echo "[run_local] Custom rest service waiting for configuration injection from DXP..."
kubectl wait --for=condition=available --timeout=600s deployment/custom-rest-service

kubectl create ingress custom-rest-service-localhost --class=nginx --rule="custom-rest-service.localdev.me/*=custom-rest-service:80"