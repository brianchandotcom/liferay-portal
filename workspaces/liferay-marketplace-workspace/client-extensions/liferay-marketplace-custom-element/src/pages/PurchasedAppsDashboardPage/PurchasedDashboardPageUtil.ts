import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';

export const appList: AppProps[] = [];

export const initialAccountState: Account[] = [
	{
		externalReferenceCode: '',
		id: 0,
		name: '',
		description: '',
		type: '',
	},
];

export const initialAppState: AppProps = {
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
		title: 'Name',
		style: {width: '2%'},
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
