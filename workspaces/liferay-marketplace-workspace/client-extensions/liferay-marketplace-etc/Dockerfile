FROM liferay/node-runner:latest

COPY app.js package.json /opt/liferay/

ENV LIFERAY_NODE_RUNNER_START="npm start"

RUN npm install