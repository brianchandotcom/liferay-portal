#!/bin/bash

eval $(minikube docker-env) && echo "[stop_local] Using minikube docker-env"

kubectl delete --ignore-not-found=true custom-rest-service-localhost dxp-localhost remote-app-a-localhost 
kubectl delete --ignore-not-found=true deployment dxp-deployment custom-rest-service remote-app-a
kubectl delete --ignore-not-found=true configmap custom-rest-service-extension-configmap custom-rest-service.user.agent.factory.application.oauth2.liferay.com dxp-configmap dxp-logging-configmap remote-app-a-configmap
kubectl delete --ignore-not-found=true service custom-rest-service remote-app-a dxp-service

pkill -f "kubectl port-forward"