#!/bin/bash

kubectl logs --prefix -f --selector='!foo' --all-containers --ignore-errors --tail=-1