#!/bin/bash

caddy run --adapter caddyfile --config /etc/caddy/Caddyfile &

exec java -jar MockMock.jar -p ${MOCKMOCK_SMPT_PORT} -h ${MOCKMOCK_HTTP_PORT} -m ${MOCKMOCK_MAX_QUEUE_SIZE}