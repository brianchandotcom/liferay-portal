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

import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';
import {DashboardListItems} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';

export const appList: AppProps[] = [];

export const initialAccountState: Account[] = [
	{
		description: '',
		externalReferenceCode: '',
		id: 0,
		name: '',
		type: '',
	},
];

export const initialAppState: AppProps = {
	attachments: [
		{
			externalReferenceCode: '',
			id: 0,
			src: '',
			title: {},
		},
	],
	catalogId: 0,
	externalReferenceCode: '',
	lastUpdatedBy: '',
	name: '',
	productId: 0,
	selected: false,
	status: '',
	thumbnail: '',
	type: '',
	updatedDate: '',
	version: '',
};

export const customerPermissionDescriptions: PermissionDescription[] = [
	{
		permissionName: 'Purchase apps and solutions',
		permissionTooltip: 'Purchase new apps and versions',
		permittedRoles: ['Account Buyer'],
	},
	{
		permissionName: 'Provision and download apps and solutions',
		permissionTooltip:
			'Manually provision cloud solutions in the DXP Cloud console.',
		permittedRoles: [''],
	},
	{
		permissionName: 'Download DXP app and versions',
		permissionTooltip: 'Download purchased DXP app LPKG files.',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
	},
	{
		permissionName: 'View purchased apps and solutions',
		permissionTooltip: 'View Cloud and DXP apps purchased by the customer.',
		permittedRoles: [
			'Account Administrator',
			'Account Buyer',
			'Account Member',
		],
	},
];

export const initialDashboardNavigationItems: DashboardListItems[] = [
	{
		itemIcon: appsIcon,
		itemName: 'myApps',
		itemSelected: true,
		itemTitle: 'My Apps',
		items: appList,
	},
	{
		itemIcon: solutionsIcon,
		itemName: 'solutions',
		itemSelected: false,
		itemTitle: 'Solutions',
	},
	{
		itemIcon: membersIcon,
		itemName: 'members',
		itemSelected: false,
		itemTitle: 'Members',
	},
];

export const tableHeaders = [
	{
		style: {width: '2%'},
		title: 'Name',
	},
	{
		title: 'Purchased By',
	},
	{
		title: 'Type',
	},
	{
		title: 'Order ID',
	},
	{
		title: 'Provisioning',
	},
	{
		title: 'Installation',
	},
];

export const memberTableHeaders = [
	{
		iconSymbol: 'order-arrow',
		title: 'Name',
	},
	{
		title: 'Email',
	},
	{
		title: 'Role',
	},
];
