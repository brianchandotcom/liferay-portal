#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the remote-app PoC"
yarn install && yarn build
docker build -t $IMAGE .
cat ../../k8s/remoteappa/extension-configmap.yaml.template > ../../k8s/remoteappa/extension-configmap.yaml &&
../create_app_config.sh \
	-n "Liferay Hello World" \
	-e liferay-hello-world \
	-p "foo=bar" \
	>> ../../k8s/remoteappa/extension-configmap.yaml
