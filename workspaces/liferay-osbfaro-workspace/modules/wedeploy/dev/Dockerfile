FROM liferay/dxp:7.4.13-u78

COPY --chown=liferay:liferay configs /home/liferay/configs
COPY --chown=liferay:liferay deploy /mnt/liferay/deploy
COPY --chown=liferay:liferay license /mnt/liferay/deploy
COPY --chown=liferay:liferay resources /home/liferay/resources
COPY --chown=liferay:liferay 100_liferay_image_setup.sh /usr/local/liferay/scripts/pre-configure/100_liferay_image_setup.sh