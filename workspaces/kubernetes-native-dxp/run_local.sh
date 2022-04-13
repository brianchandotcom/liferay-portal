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

echo "[run_local] Build the remote-app PoC"
(cd lxc-extensions/liferay-hello-world &&
    yarn install && yarn build &&
    docker build -t remoteappa . &&
    cat ../../k8s/remoteappa/remote-app-a-configmap.yaml.template > ../../k8s/remoteappa/remote-app-a-configmap.yaml &&
    REMOTE_APP_HOST=http://localhost:8090 ../create_app_config.sh -n "Remote App A" -e liferay-hello-world -p "foo=bar" >> ../../k8s/remoteappa/remote-app-a-configmap.yaml)

echo "[run_local] Build the custom-rest-service"
(cd lxc-extensions/custom-rest-service && 
    mvn clean package &&
    docker build -t "custom-rest-service:1.0.0-SNAPSHOT" .)

echo "[run_local] Deleting deployments"
kubectl delete deployments.apps remote-app-a --wait=true --ignore-not-found=true
kubectl delete deployments.apps dxp-deployment --wait=true --ignore-not-found=true
kubectl delete deployments.apps custom-rest-service --wait=true --ignore-not-found=true

echo "[run_local] Deploy updated resources"
kubectl apply -f k8s/remoteappa/
kubectl wait --for=condition=available --timeout=600s deployment/remote-app-a
kubectl apply -f k8s/dxp/
kubectl wait --for=condition=available --timeout=600s deployment/dxp-deployment
kubectl apply -f k8s/custom-rest-service/

# forward ports so we can see it!
pkill -f "kubectl port-forward"
export POD=$(kubectl get pods -l app=dxp --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
(kubectl port-forward $POD 8000:8000 &)
(kubectl port-forward $POD 8080:8080 &)
(kubectl port-forward $POD 11311:11311 &)
export REMOTE_APP_POD=$(kubectl get pods -l app=remote-app-a --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
(kubectl port-forward $REMOTE_APP_POD 8090:80 &)