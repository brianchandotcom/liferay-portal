/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum ORDER_WORKFLOW_STATUS_CODE {
	CANCELLED = 8,
	COMPLETED = 0,
	IN_PROGRESS = 6,
	ON_HOLD = 20,
	PENDING = 1,
	PROCESSING = 10,
}

export enum ORDER_TYPES {
	DXPAPP = 'DXPAPP',
	CLOUDAPP = 'CLOUDAPP',
	SOLUTIONS7 = 'SOLUTIONS7',
	SOLUTIONS30 = 'SOLUTIONS30',
}

export enum PRODUCT_TYPE_SPECIFICATION {
	DXPAPP = 'dxp',
	CLOUDAPP = 'cloud',
}
