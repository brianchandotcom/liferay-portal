#!/bin/bash

function copy_configs {
	CONFIGS_DIR="/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}"

	if [ -n "$(ls -A ${CONFIGS_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying ${CONFIGS_DIR} config files:"
		echo ""

		tree --noreport "${CONFIGS_DIR}"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_HOME}."

		cp -R "${CONFIGS_DIR}"/* ${LIFERAY_HOME}

		echo ""
	fi
}

function copy_remove_dir {
	SRC_DIR=$1
	DEST_DIR=$2

	if [ -n "$(ls -A ${SRC_DIR}/* 2> /dev/null)" ]; then
		echo "[LIFERAY] Copying ${SRC_DIR} ${DEST_DIR} files:"
		echo ""

		tree --noreport "${SRC_DIR}"

		echo ""
		echo "[LIFERAY] ... into ${LIFERAY_MOUNT_DIR}/${DEST_DIR}"

		cp -R "${SRC_DIR}"/* ${LIFERAY_MOUNT_DIR}/${DEST_DIR}

		rm -rf "${SRC_DIR}"

		echo ""
	fi
}

function main {
	copy_remove_dir "/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/scripts" scripts

	copy_remove_dir "/home/liferay/configs/${LIFERAY_WORKSPACE_ENVIRONMENT}/patching" patching

	copy_configs
}

main