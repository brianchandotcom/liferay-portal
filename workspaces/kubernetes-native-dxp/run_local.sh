#!/bin/bash

if [ `which minikube` ];then
    eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    R=$?

    MINIKUBE_STATUS=$(minikube status -o json | jq -r '.APIServer')
    if [ "$MINIKUBE_STATUS" != "Running" ];then
        echo "[run_local] Starting minikube ..."
        minikube start --cpus 8 --memory 16364
        minikube addons enable ingress
    fi

    if [ $R -gt 0 ];then
        eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    fi
fi

./deploy-dxp.sh
./lxc-extensions/custom-rest-service/deploy.sh
./lxc-extensions/liferay-hello-world/deploy.sh

# forward port to nginx ingress controller
pkill -f "kubectl port-forward"
(kubectl port-forward --namespace=ingress-nginx service/ingress-nginx-controller 8080:80)