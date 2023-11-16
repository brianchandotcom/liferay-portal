/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AppType} from '../enums/AppType';

export const offeringTypesDescription = {
	[AppType.CLOUD]: [
		{
			description:
				'The cloud app is client extension based and is compatible with a customer’s self-hosted environment.',
			label: 'Self-Hosted',
		},
		{
			description:
				'The cloud app is client extension based and is compatible with Liferay’s Self-Managed (formerly DXP Cloud) offering.',
			label: 'Self-Managed',
		},
		{
			description:
				'The cloud app is client extension based and is compatible with Liferay Experience Cloud (LXC).  It fully supports and deploys on extension environments in LXC.',
			label: 'Fully-Managed',
		},
	],
	[AppType.DXP]: [
		{
			description:
				'The DXP app is module-based and is compatible with 7.4 builds of Liferay DXP.',
			label: 'Self-Hosted',
		},
		{
			description:
				'The DXP app is module-based and is compatible with 7.4 builds of Liferay DXP self-hosted Liferay Cloud (formerly DXP Cloud).',
			label: 'Self-Managed',
		},
		{
			description:
				'DXP module-based apps are not supported on Liferay Experience Cloud (LXC).',
			label: 'Fully-Managed',
		},
	],
} as const;
