#!/bin/bash

kubectl logs --prefix -f --selector='!foo' --all-containers