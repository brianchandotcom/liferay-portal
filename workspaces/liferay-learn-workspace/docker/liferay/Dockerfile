FROM liferay/dxp:2024.q1.5

COPY --chown=liferay:liferay configs /home/liferay/configs
COPY --chown=liferay:liferay liferay_image_setup.sh /usr/local/liferay/scripts/pre-configure/liferay_image_setup.sh

ENV LIFERAY_WORKSPACE_ENVIRONMENT=local