/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum SkuLicenseUsageType {
	CLOUD = 'cloud-license-usage-type',
	DXP = 'dxp-license-usage-type',
}

export enum SkuLicenseUsageTypeValue {
	DEVELOPER = 'developer',
	STANDARD = 'standard',
	TRIAL = 'trial',
}

export const SkuLicenseUsageTypeValues = {
	[SkuLicenseUsageType.DXP]: [
		SkuLicenseUsageTypeValue.DEVELOPER,
		SkuLicenseUsageTypeValue.STANDARD,
		SkuLicenseUsageTypeValue.TRIAL,
	],
	[SkuLicenseUsageType.CLOUD]: [
		SkuLicenseUsageTypeValue.STANDARD,
		SkuLicenseUsageTypeValue.TRIAL,
	],
} as const;
