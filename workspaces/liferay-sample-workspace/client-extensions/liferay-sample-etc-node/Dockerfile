FROM liferay/node-runner:latest

COPY package.json /opt/liferay

RUN npm install

COPY *.js /opt/liferay/
COPY util/ /opt/liferay/util/