FROM adoptopenjdk/openjdk11:jdk-11.0.14.1_1

ENV DEBUG_PORT=""

RUN apt-get update && \
	apt-get install -y git gnupg jq openjdk-8-jdk tini && \
	rm -rf /var/lib/apt/lists/*
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
	echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
	apt-get update && \
	apt-get install -y yarn
RUN yarn global add yo && \
	export PATH="$(yarn global bin):$PATH" && \
	yarn add generator-liferay-theme
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

COPY *.jar app.jar
COPY app.sh /

RUN chmod a+x /app.sh
RUN useradd -ms /bin/bash importer

USER importer

WORKDIR /home/importer

ENTRYPOINT ["/usr/bin/tini", "--", "/app.sh"]