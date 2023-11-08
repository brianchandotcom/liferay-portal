FROM apache/activemq-artemis:2.31.2-alpine

ENV ANONYMOUS_LOGIN "false"
ENV ARTEMIS_CLUSTER_PASSWORD "artemis-cluster"
ENV ARTEMIS_CLUSTER_SLAVE "false"
ENV ARTEMIS_CLUSTER_USER "artemis-cluster"
ENV ARTEMIS_PASSWORD "artemis"
ENV ARTEMIS_USER "artemis"

COPY ./data/docker-run.sh /