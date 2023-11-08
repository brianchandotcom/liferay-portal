/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export type AppFlowListItemProps = {
	checked: boolean;
	label: string;
	name: string;
	selected: boolean;
};

export const initialFLowListItems: AppFlowListItemProps[] = [
	{
		checked: false,
		label: 'Create',
		name: 'create',
		selected: true,
	},
	{
		checked: false,
		label: 'Profile',
		name: 'profile',
		selected: false,
	},
	{
		checked: false,
		label: 'Build',
		name: 'build',
		selected: false,
	},
	{
		checked: false,
		label: 'Storefront',
		name: 'storefront',
		selected: false,
	},
	{
		checked: false,
		label: 'Version',
		name: 'version',
		selected: false,
	},
	{
		checked: false,
		label: 'Pricing',
		name: 'pricing',
		selected: false,
	},
	{
		checked: false,
		label: 'Licensing',
		name: 'licensing',
		selected: false,
	},
	{
		checked: false,
		label: 'Support',
		name: 'support',
		selected: false,
	},
	{
		checked: false,
		label: 'Submit',
		name: 'submit',
		selected: false,
	},
];
