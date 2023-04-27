import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import appsIcon from '../../assets/icons/apps-fill.svg';
import membersIcon from '../../assets/icons/person-fill.svg';

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
		itemName: 'apps',
		itemSelected: true,
		itemTitle: 'My Apps',
		items: appList,
	},
	{
		itemIcon: membersIcon,
		itemName: 'members',
		itemSelected: false,
		itemTitle: 'Members',
	},
];
