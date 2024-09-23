/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum ORDER_CUSTOM_FIELDS {
	ANALYTICS_GROUP_ID = 'analytics-group-id',
	CLOUD_PROVISIONING = 'cloud-provisioning',
	END_DATE = 'trial-end-date',
	PROJECT_NAME = 'project-name',
	START_DATE = 'trial-start-date',
	VIRTUAL_HOST = 'trial-virtualhost',
}

export enum ORDER_TYPES {
	ADDONS = 'ADDONS',
	DXPAPP = 'DXPAPP',
	CLOUDAPP = 'CLOUDAPP',
	SOLUTIONS7 = 'SOLUTIONS7',
	SOLUTIONS30 = 'SOLUTIONS30',
}

export enum ORDER_WORKFLOW_STATUS_CODE {
	CANCELLED = 8,
	COMPLETED = 0,
	IN_PROGRESS = 6,
	ON_HOLD = 20,
	PENDING = 1,
	PROCESSING = 10,
}
