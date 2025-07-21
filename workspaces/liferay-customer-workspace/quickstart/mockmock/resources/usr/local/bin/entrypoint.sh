#!/bin/bash

exec java -jar MockMock.jar -h "${MOCKMOCK_HTTP_PORT}" -m "${MOCKMOCK_MAX_QUEUE_SIZE}" -p "${MOCKMOCK_SMPT_PORT}"