FROM liferay/dxp:2024.q1.5
ENV LIFERAY_WORKSPACE_ENVIRONMENT=local
COPY --chown=liferay:liferay configs /home/liferay/configs
COPY --chown=liferay:liferay 100_liferay_image_setup.sh /usr/local/liferay/scripts/pre-configure/100_liferay_image_setup.sh