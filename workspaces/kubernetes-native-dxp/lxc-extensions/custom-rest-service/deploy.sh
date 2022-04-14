#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the custom-rest-service"
mvn clean package
docker build -t "custom-rest-service:1.0.0-SNAPSHOT" .

kubectl delete deployments.apps custom-rest-service --wait=true --ignore-not-found=true
kubectl apply -f ../../k8s/custom-rest-service/
kubectl wait --for=condition=available --timeout=600s deployment/custom-rest-service

export POD=$(kubectl get pods -l app=custom-rest-service --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
echo "POD ===> $POD"

pkill -f "kubectl port-forward.*8081"
kubectl port-forward svc/custom-rest-service 8081:80 &