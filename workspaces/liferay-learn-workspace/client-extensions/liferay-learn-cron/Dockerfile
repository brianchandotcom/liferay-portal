FROM liferay/jdk11-jdk8:latest

RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
RUN apt-get install -y git jq nodejs p7zip-full
RUN npm install -g generator-liferay-fragments generator-liferay-theme yarn yo
RUN mkdir /public_html
RUN chown liferay:liferay /public_html

USER liferay

COPY liferay_learn_cron_entrypoint.sh *.jar /home/liferay/

ENTRYPOINT ["/usr/bin/tini", "--", "/home/liferay/liferay_learn_cron_entrypoint.sh"]