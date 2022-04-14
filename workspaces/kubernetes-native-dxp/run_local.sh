#!/bin/bash

./deploy-dxp.sh
./lxc-extensions/custom-rest-service/deploy.sh
./lxc-extensions/liferay-hello-world/deploy.sh