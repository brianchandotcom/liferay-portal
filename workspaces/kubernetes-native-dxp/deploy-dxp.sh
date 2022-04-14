#!/bin/bash

if [ `which minikube` ];then
    eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    R=$?

    MINIKUBE_STATUS=$(minikube status -o json | jq -r '.APIServer')
    if [ "$MINIKUBE_STATUS" != "Running" ];then
        echo "[run_local] Starting minikube ..."
        minikube start --cpus 8 --memory 16364
    fi

    if [ $R -gt 0 ];then
        eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    fi
fi

# build local dxp image with k8s-native features
./gradlew resolve buildDockerImage

echo "[run_local] Deleting deployments"
kubectl delete deployments.apps dxp-deployment --wait=true --ignore-not-found=true

echo "[run_local] Deploy updated resources"
kubectl apply -f k8s/dxp/
kubectl wait --for=condition=available --timeout=600s deployment/dxp-deployment

# forward ports so we can see it!
pkill -f "kubectl port-forward"
export POD=$(kubectl get pods -l app=dxp --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
(kubectl port-forward $POD 8000:8000 &)
(kubectl port-forward $POD 11311:11311 &)
(kubectl port-forward svc/dxp-service 8080:80 &)
