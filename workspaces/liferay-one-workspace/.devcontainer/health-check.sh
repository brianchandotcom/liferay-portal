#!/bin/bash

function _log {
	echo "[$(date "+%Y.%m.%d %H:%M:%S")] $1" | tee -a health-check.log
}

if ! grep -qiE '[-.]ga[0-9]*([[:space:]]|$)' .liferay-version
then
	_log "Checking for license registration..."

	if ! grep -qiE "license validation passed" logs/liferay.*.log 2>/dev/null
	then
		_log "License not registered"

		exit 1
	fi
fi

_log "Waiting for the server to be reachable..."

if ! curl -s localhost:8080 -o /dev/null
then
	_log "Server not reachable"

	exit 1
fi

_log "Ready!"

exit 0