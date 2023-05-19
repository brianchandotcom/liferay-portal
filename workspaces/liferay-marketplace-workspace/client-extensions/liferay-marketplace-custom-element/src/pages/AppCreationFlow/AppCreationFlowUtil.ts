/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
