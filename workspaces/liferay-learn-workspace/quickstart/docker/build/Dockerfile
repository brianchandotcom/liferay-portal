FROM --platform=${TARGETPLATFORM} liferay/jdk11-jdk8:latest AS liferay-jdk11-jdk8

RUN curl -fsSL "https://dl.filippo.io/mkcert/latest?for=linux/$(dpkg --print-architecture)" -o /usr/local/bin/mkcert && \
	chmod +x /usr/local/bin/mkcert

FROM liferay-jdk11-jdk8

ARG GROUP_ID=$GROUP_ID
ARG LABEL_BUILD_DATE
ARG LABEL_NAME
ARG LABEL_VCS_REF
ARG LABEL_VCS_URL
ARG LABEL_VERSION
ARG TARGETPLATFORM
ARG  USER_ID=$USER_ID

COPY resources/ /

ENTRYPOINT ["tini", "-v", "--", "/usr/local/bin/liferay_build_entrypoint.sh"]

ENV JAVA_VERSION=zulu11
ENV LANG="C.UTF-8"

LABEL org.label-schema.build-date="${LABEL_BUILD_DATE}"
LABEL org.label-schema.name="${LABEL_NAME}"
LABEL org.label-schema.schema-version="1.0"
LABEL org.label-schema.vcs-ref="${LABEL_VCS_REF}"
LABEL org.label-schema.vcs-url="${LABEL_VCS_URL}"
LABEL org.label-schema.vendor="Liferay, Inc."
LABEL org.label-schema.version="${LABEL_VERSION}"

USER root

WORKDIR /mnt/liferay-learn-workspace