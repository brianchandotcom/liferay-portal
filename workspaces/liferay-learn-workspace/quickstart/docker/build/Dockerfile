FROM liferay/jdk11:latest

COPY resources/ /

ENTRYPOINT ["tini", "-v", "--", "/usr/local/bin/liferay_build_entrypoint.sh"]

USER root

WORKDIR /mnt/liferay-learn-workspace