#!/bin/bash

if [ `which minikube` ];then
    eval $(minikube docker-env) && echo "Using minikube docker-env"

    MINIKUBE_STATUS=$(minikube status -o json | jq -r '.APIServer')
    if [ "$MINIKUBE_STATUS" == "Stopped" ];then
        echo "Starting minikube ..."
        minikube start --cpus 8 --memory 16364
    fi
fi

# build local dxp image with k8s-native features
(cd kubernetes-native-dxp && ./gradlew buildDockerImage)

# build the remote-app PoC
(cd ../LCD-13781-remote-apps-sdlc/liferay-hello-world &&
    yarn install && yarn build &&
    docker build -t remoteappa . &&
    cat ../template.yaml > ../k8s/remote-app-a-configmap.yaml &&
    REMOTE_APP_HOST=http://localhost:8090 ../create_app_config.sh -n "Remote App A" -e liferay-hello-world -p "foo=bar" >> ../k8s/remote-app-a-configmap.yaml)

echo "Deleting deployment"
kubectl delete deployments.apps remote-app-a --wait=true --ignore-not-found=true
kubectl delete deployments.apps dxp-deployment --wait=true --ignore-not-found=true

echo "Deploy updated resources"
kubectl apply -f ../LCD-13781-remote-apps-sdlc/k8s/
kubectl wait --for=condition=available --timeout=600s deployment/remote-app-a
kubectl apply -f k8s/dxp/
kubectl wait --for=condition=available --timeout=600s deployment/dxp-deployment

# forward ports so we can see it!
pkill -f "kubectl port-forward"
export POD=$(kubectl get pods -l app=dxp --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
(kubectl port-forward $POD 8000:8000 &)
(kubectl port-forward $POD 8080:8080 &)
(kubectl port-forward $POD 11311:11311 &)
export REMOTE_APP_POD=$(kubectl get pods -l app=remote-app-a --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
(kubectl port-forward $REMOTE_APP_POD 8090:80 &)