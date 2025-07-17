FROM alpine:3

COPY resources/ /

ENTRYPOINT ["tini", "-v", "--", "/usr/local/bin/entrypoint.sh"]

ENV MOCKMOCK_HTTP_PORT=8282
ENV MOCKMOCK_MAX_QUEUE_SIZE=1000
ENV MOCKMOCK_SMPT_PORT=2525
ENV MOCKMOCK_URL=https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true

EXPOSE 80 2525 8282

RUN apk add --no-cache bash curl openjdk11 tini && \
	\
	curl -fSL "${MOCKMOCK_URL}" -o MockMock.jar && \
	chmod +x /usr/local/bin/entrypoint.sh