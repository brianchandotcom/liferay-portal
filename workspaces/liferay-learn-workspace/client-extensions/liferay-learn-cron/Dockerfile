FROM liferay/job-runner:latest

RUN apt-get update && \
	apt-get install -y git gnupg jq && \
	apt-get install -y p7zip-full p7zip-rar && \
	rm -rf /var/lib/apt/lists/*
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
	echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
	apt-get update && \
	apt-get install -y yarn
RUN yarn global add yo && \
	export PATH="$(yarn global bin):$PATH" && \
	yarn add generator-liferay-theme

COPY job-runner /mnt/liferay
COPY *.jar /opt/liferay

RUN mkdir -p /public_html