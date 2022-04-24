#!/bin/bash

cd "$(dirname "$0")"

eval $(minikube docker-env)

echo "[run_local] Build the liferayhelloworld PoC"

yarn install && yarn build-local &&\
  docker build -t $IMAGE .

cat ../../k8s/liferayhelloworld/extension-configmap.yaml.template \
	> ../../k8s/liferayhelloworld/extension-configmap.yaml &&
sed -e 's/^/    /' com.liferay.remote.app.factory.configuration.v1.RemoteAppFactoryConfiguration-liferayhelloworld.config \
	>> ../../k8s/liferayhelloworld/extension-configmap.yaml


