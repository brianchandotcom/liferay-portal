#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

export SERVICE_DOMAIN=remote-app-a.localdev.me
export REMOTE_APP_HOST=https://${SERVICE_DOMAIN}

echo "[run_local] Build the remote-app PoC"
yarn install && yarn build
docker build -t $IMAGE .
cat ../../k8s/remoteappa/extension-configmap.yaml.template > ../../k8s/remoteappa/extension-configmap.yaml &&
../create_app_config.sh \
	-n "Liferay Hello World" \
	-e liferay-hello-world \
	-p "foo=bar" \
	>> ../../k8s/remoteappa/extension-configmap.yaml
