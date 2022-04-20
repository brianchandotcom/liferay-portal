#!/bin/bash

if [ `which minikube` ]; then
    eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    R=$?

    MINIKUBE_STATUS=$(minikube status -o json | jq -r '.APIServer')
    if [ "$MINIKUBE_STATUS" != "Running" ]; then
        echo "[run_local] Starting minikube ..."
        if [[ "$OSTYPE" == "darwin"* ]]; then
            minikube start --cpus 8 --memory 16364 --vm=true --driver=hyperkit
        else
            minikube start --cpus 8 --memory 16364
        fi

        minikube addons enable ingress

        MINIKUBE_TLS_CERT=$(kubectl -n ingress-nginx get deployment ingress-nginx-controller -o json | jq '.spec.template.spec.containers[0].args | index("--default-ssl-certificate=kube-system/mkcert")')
        if ! [[ $yournumber =~ '^[0-9]+$' ]]; then
            kubectl -n kube-system create secret tls mkcert --key tls/localdev.me.key --cert tls/localdev.me.crt
            printf '%s\n' "kube-system/mkcert" Y | minikube addons configure ingress
            minikube addons disable ingress

            bold=$(tput bold)
            normal=$(tput sgr0)
            echo "\
******************************************************************************************
******************************************************************************************
* A custom self-signed wildcard certificate for the domain ${bold}*.localdev.me${normal} was added to
* the minikube ingress addon. This means you get TLS for free with all subdomains of
* ${bold}localdev.me${normal}.
*
* Make sure to install the CA (Certificate Authority) ${bold}tls/ca.crt${normal} into your browser.
*
* If you have services that need to communicate directly over TLS using these domain names
* the ${bold}tls/ca.crt${normal} can be copied and installed into them as well.
******************************************************************************************
******************************************************************************************
"
        fi

        minikube addons enable ingress
    fi

    if [ $R -gt 0 ]; then
        eval $(minikube docker-env) && echo "[run_local] Using minikube docker-env"
    fi
else
    echo "Please install minikube. See https://minikube.sigs.k8s.io/docs/start/"
    exit 1
fi

if [ `which skaffold` ]; then
    skaffold dev
else
    echo "Please install skaffold. See https://skaffold.dev/docs/install/"
    exit 1
fi
