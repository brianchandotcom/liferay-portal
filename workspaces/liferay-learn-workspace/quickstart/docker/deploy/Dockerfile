FROM liferay/jdk11:latest

COPY resources/ /

ENTRYPOINT ["tini", "-v", "--", "/usr/local/bin/liferay_deploy_entrypoint.sh"]

RUN apt-get update && apt-get install --yes git mysql-client p7zip-full rsync

USER root

WORKDIR /mnt/liferay-learn-workspace