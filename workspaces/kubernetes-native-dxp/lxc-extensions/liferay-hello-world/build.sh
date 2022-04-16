#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the remote-app PoC"
yarn install && yarn build
docker build -t $IMAGE .
cat ../../k8s/remoteappa/extension-configmap.yaml.template > ../../k8s/remoteappa/extension-configmap.yaml &&
REMOTE_APP_HOST=http://remote-app-a.localdev.me:8080 ../create_app_config.sh -n "Remote App A" -e liferay-hello-world -p "foo=bar" >> ../../k8s/remoteappa/extension-configmap.yaml