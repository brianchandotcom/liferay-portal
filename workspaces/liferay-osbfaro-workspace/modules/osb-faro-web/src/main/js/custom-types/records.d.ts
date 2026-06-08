/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare namespace CustomRecords {
	interface Property {
		entityName: string;
		entityType: string;
		id: any;
		label: string;
		name: string;
		options: object[];
		propertyKey: string;
		type: string;
	}

	interface User {
		email: string;
		groupId: string;
		hasPermission: (permissions: Array<string> | string) => boolean;
		id: string;
		isAdmin: () => boolean;
		isMember: () => boolean;
		isOwner: () => boolean;
		name: string;
		status: number;
	}
}
