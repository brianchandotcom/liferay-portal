FROM liferay/jdk11-jdk8:latest

COPY liferay_learn_etc_cron_entrypoint.sh *.jar /usr/local/bin

ENTRYPOINT ["/usr/bin/tini", "--", "/usr/local/bin/liferay_learn_etc_cron_entrypoint.sh"]

RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - && \
	apt-get install --yes git jq nodejs p7zip-full rsync && \
	npm install --global generator-liferay-fragments generator-liferay-theme yarn yo && \
	mkdir /public_html && \
	chown liferay:liferay /public_html

USER liferay