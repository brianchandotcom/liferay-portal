/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const SUBSCRIPTION_TYPES = {
	Blank: ['Partnership'],
	Purchased: [
		'Analytics Cloud',
		'Commerce',
		'Commerce for Cloud',
		'Enterprise Search',
		'Liferay Experience Cloud',
		'LXC - SM',
		'Other',
	],
	PurchasedAndProvisioned: ['Portal', 'Liferay Self-Hosted'],
} as const;

export const PRODUCT_DISPLAY_EXCEPTION = {
	blankProducts: [
		'Business',
		'Business Plan',
		'CSP - Custom',
		'CSP - Custom User Tier',
		'CSP - Up ',
		'CSP - Up to 1K Users',
		'CSP - Up to 10K Users',
		'CSP - Up to 20K Users',
		'CSP - Up to 100 Users',
		'CSP - Up to 5K Users',
		'CSP - Up to 500 Users',
		'Developer Services',
		'Developer Subscription',
		'Developer Support',
		'Developer Tools',
		'Enterprise',
		'Enterprise Plan',
		'Extended Premium Support',
		'Extended Premium Support - DXP 7.0',
		'Extended Premium Support - Liferay DXP 7.0',
		'Extended Premium Support - Liferay DXP 7.2',
		'Extended Premium Support - Liferay DXP 7.1',
		'Maintenance Services',
		'Managed Services',
		'Managed Services - Developer Support',
		'Managed Services - Standard',
		'Pro',
		'Pro Plan',
	],
	nonBlankProducts: ['Contact', 'Mobile Device'],
	purchasedProduct: [],
} as const;

export const PRODUCT_DISPLAY_EXCEPTION_INSTANCE_SIZE = {
	purchasedProductInstanceSize: [
		'Extended Premium Support',
		'Extended Premium Support - DXP 7.0',
		'Extended Premium Support - Liferay DXP 7.0',
		'Extended Premium Support - Liferay DXP 7.1',
		'Extended Premium Support - Liferay DXP 7.2',
	],
} as const;
