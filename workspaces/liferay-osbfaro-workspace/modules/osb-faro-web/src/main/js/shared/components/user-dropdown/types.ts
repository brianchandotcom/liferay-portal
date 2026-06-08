/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type MenuItem = {
	active?: boolean;
	childMenuId?: string;
	divider?: boolean;
	externalLink?: boolean;
	icon?: string;
	iconAlignment?: string;
	label?: string;
	onClick?: () => void;
	url?: string;
};

export type MenuGroup = {
	items: MenuItem[];
	subheaderLabel?: string;
};

export type Menus = {[key: string]: MenuGroup[]};
