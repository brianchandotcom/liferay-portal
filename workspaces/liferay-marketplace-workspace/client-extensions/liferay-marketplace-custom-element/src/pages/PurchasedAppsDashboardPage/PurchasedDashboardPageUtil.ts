/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export const customerAppPermissionDescriptions: PermissionDescription[] = [
	{
		permissionName: 'Purchase apps and solutions',
		permissionTooltip: 'Purchase new apps and versions',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
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
		permittedRoles: ['Account Administrator', 'Account Buyer'],
	},
	{
		permissionName: 'Create licenses for a DXP application',
		permissionTooltip: 'Create a license for a DXP application',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
	},
];

export const customerDashboardPermissionDescriptions: PermissionDescription[] = [
	{
		permissionName: 'Manage my own member profile',
		permissionTooltip: 'Manage my own profile information (via Okta)',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
	},
	{
		permissionName: 'Manage customer account',
		permissionTooltip: 'Manage all attributes of account',
		permittedRoles: ['Account Administrator'],
	},
	{
		permissionName: 'View account members',
		permissionTooltip: 'View all members and roles in account',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
	},
	{
		permissionName: 'Manage members and roles',
		permissionTooltip: 'Manage roles of the members - invite & remove',
		permittedRoles: ['Account Administrator'],
	},
	{
		permissionName: 'View all invoice information',
		permissionTooltip: 'View invoices of past purchases in the Marketplace',
		permittedRoles: ['Account Administrator', 'Account Buyer'],
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
