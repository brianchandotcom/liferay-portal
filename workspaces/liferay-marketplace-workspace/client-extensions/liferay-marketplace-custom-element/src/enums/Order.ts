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
	TRIAL_ERROR = 'trial-error',
	TRIAL_SETTINGS = 'trial-settings',
	VIRTUAL_HOST = 'trial-virtualhost',
}

export enum ORDER_TYPES {
	ADDONS = 'ADDONS',
	CLIENT_EXTENSION = 'CLIENT_EXTENSION',
	CLOUDAPP = 'CLOUDAPP',
	COMPOSITE_APP = 'COMPOSITE_APP',
	DXPAPP = 'DXPAPP',
	LOW_CODE_CONFIGURATION = 'LOW_CODE_CONFIGURATION',
	SOLUTIONS7 = 'SOLUTIONS7',
	SOLUTIONS30 = 'SOLUTIONS30',
}

export const ORDER_TYPES_LABELS = {
	[ORDER_TYPES.ADDONS]: 'Add-Ons',
	[ORDER_TYPES.CLIENT_EXTENSION]: 'Client Extension',
	[ORDER_TYPES.CLOUDAPP]: 'Cloud',
	[ORDER_TYPES.COMPOSITE_APP]: 'Composite App',
	[ORDER_TYPES.DXPAPP]: 'DXP',
	[ORDER_TYPES.LOW_CODE_CONFIGURATION]: 'Low-Code Configuration',
	[ORDER_TYPES.SOLUTIONS7]: 'Solutions 7',
	[ORDER_TYPES.SOLUTIONS30]: 'Solutions 30',
} as const;

export enum ORDER_WORKFLOW_STATUS_CODE {
	CANCELLED = 8,
	COMPLETED = 0,
	IN_PROGRESS = 6,
	ON_HOLD = 20,
	PENDING = 1,
	PROCESSING = 10,
}

export enum PAYMENT_STATUS {
	PAID = 0,
	PAYMENT_PENDING = 2,
}
