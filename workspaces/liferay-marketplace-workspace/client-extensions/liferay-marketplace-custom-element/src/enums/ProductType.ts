/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum ProductType {
	CLIENT_EXTENSION = 'client-extension',
	CLOUD = 'cloud',
	DXP = 'dxp',
	FRAGMENT = 'fragment',
}

export const ProductTypeLabels = {
	[ProductType.CLIENT_EXTENSION]: 'Client Extension',
	[ProductType.CLOUD]: 'Cloud',
	[ProductType.DXP]: 'DXP',
	[ProductType.FRAGMENT]: 'Fragment',
} as const;
