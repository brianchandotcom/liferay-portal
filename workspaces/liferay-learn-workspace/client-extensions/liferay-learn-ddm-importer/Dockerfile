FROM liferay/jdk11-jdk8:latest

USER liferay

COPY liferay_learn_ddm_importer_entrypoint.sh *.jar /home/liferay/
COPY main/resources/ /resources/

ENTRYPOINT ["/usr/bin/tini", "--", "/home/liferay/liferay_learn_ddm_importer_entrypoint.sh"]