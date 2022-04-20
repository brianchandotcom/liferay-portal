#!/bin/bash

eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"

kubectl patch deployment custom-rest-service --patch '{"spec": {"template": {"spec": {"hostAliases": [{"ip":"'"$(minikube ip)"'", "hostnames": ["dxp.localdev.me"]}]}}}}'
kubectl patch cronjob cronjob --patch '{"spec": {"jobTemplate": {"spec": {"template": {"spec": {"hostAliases": [{"ip":"'"$(minikube ip)"'", "hostnames": ["dxp.localdev.me"]}]}}}}}}'