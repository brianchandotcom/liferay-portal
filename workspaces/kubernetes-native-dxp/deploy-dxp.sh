#!/bin/bash

# build local dxp image with k8s-native features
./gradlew resolve buildDockerImage

echo "[run_local] Deleting deployments"
kubectl delete deployments.apps dxp-deployment --wait=true --ignore-not-found=true

echo "[run_local] Deploy updated resources"
kubectl apply -k k8s/dxp/
kubectl wait --for=condition=available --timeout=600s deployment/dxp-deployment

kubectl create ingress dxp-localhost --class=nginx --rule="dxp.localdev.me/*=dxp-service:80"