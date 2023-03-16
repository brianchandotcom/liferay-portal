FROM liferay/jdk11-jdk8:latest

RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - \
	\
	apt-get install -y git nodejs p7zip-full \
	\
	npm install -g generator-liferay-fragments generator-liferay-theme yarn yo \
	\
	mkdir /public_html \
	chown liferay:liferay /public_html

USER liferay

COPY liferay_learn_cron_entrypoint.sh *.jar /home/liferay

ENTRYPOINT ["/usr/bin/tini", "--", "/home/liferay/liferay_learn_cron_entrypoint.sh"]