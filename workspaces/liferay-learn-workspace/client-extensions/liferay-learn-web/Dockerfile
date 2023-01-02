FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1

ENV DEBUG_PORT=""

RUN apt-get update && \
	apt-get install -y tini && \
	rm -rf /var/lib/apt/lists/*
RUN \
  export ARCH=$(dpkg --print-architecture) && \
    curl -JLO "https://dl.filippo.io/mkcert/latest?for=linux/${ARCH}" && \
    chmod +x mkcert-v*-linux-${ARCH} && \
    cp mkcert-v*-linux-${ARCH} /usr/local/bin/mkcert && \
    mkdir /var/lib/caroot

ENV TRUST_STORES=java
ENV CAROOT=/var/lib/caroot

COPY rootCA.pem /var/lib/caroot

RUN mkcert -install
RUN mkdir /resources

COPY *.jar app.jar
COPY main/resources/ /resources/
COPY app.sh /

RUN chmod +x /app.sh

ENTRYPOINT ["/usr/bin/tini", "--", "/app.sh"]