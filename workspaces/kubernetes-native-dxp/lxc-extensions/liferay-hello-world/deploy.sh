#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the remote-app PoC"
yarn install && yarn build
docker build -t remoteappa .
cat ../../k8s/remoteappa/extension-configmap.yaml.template > ../../k8s/remoteappa/extension-configmap.yaml &&
REMOTE_APP_HOST=http://localhost:8090 ../create_app_config.sh -n "Remote App A" -e liferay-hello-world -p "foo=bar" >> ../../k8s/remoteappa/extension-configmap.yaml

kubectl delete deployments.apps remote-app-a --wait=true --ignore-not-found=true
kubectl apply -f ../../k8s/remoteappa/
kubectl wait --for=condition=available --timeout=600s deployment/remote-app-a

export POD=$(kubectl get pods -l app=remote-app-a --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
echo "POD ===> $POD"

pkill -f "kubectl port-forward.*8090"
kubectl port-forward svc/remote-app-a 8090:80 &