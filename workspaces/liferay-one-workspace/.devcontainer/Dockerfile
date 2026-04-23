FROM eclipse-temurin:21-jdk-jammy

ARG NODE_VERSION=20
ARG YARN_VERSION=1.22.22
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
		ca-certificates \
		curl \
		git \
		gnupg \
		jq \
		less \
		lsb-release \
		make \
		openssh-client \
		sudo \
		unzip \
		vim \
		zip \
	&& curl -fsSL https://deb.nodesource.com/setup_${NODE_VERSION}.x | bash - \
	&& apt-get install -y --no-install-recommends nodejs \
	&& npm install --global yarn@${YARN_VERSION} \
	&& install -m 0755 -d /etc/apt/keyrings \
	&& curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc \
	&& chmod a+r /etc/apt/keyrings/docker.asc \
	&& echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "${VERSION_CODENAME}") stable" > /etc/apt/sources.list.d/docker.list \
	&& apt-get update \
	&& apt-get install -y --no-install-recommends docker-ce-cli docker-compose-plugin \
	&& rm -rf /var/lib/apt/lists/*
RUN groupadd --gid ${USER_GID} ${USERNAME} \
	&& useradd --uid ${USER_UID} --gid ${USER_GID} --shell /bin/bash --create-home ${USERNAME} \
	&& echo "${USERNAME} ALL=(root) NOPASSWD:ALL" > /etc/sudoers.d/${USERNAME} \
	&& chmod 0440 /etc/sudoers.d/${USERNAME}

USER ${USERNAME}