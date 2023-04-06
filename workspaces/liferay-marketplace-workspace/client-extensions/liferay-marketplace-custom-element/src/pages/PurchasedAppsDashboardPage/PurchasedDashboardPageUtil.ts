import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import appsIcon from '../../assets/icons/apps-fill.svg';

export const appList: AppProps[] = [];

export const initialDashboardNavigationItems: DashboardListItems[] = [
	{
		itemIcon: appsIcon,
		itemName: 'apps',
		itemSelected: true,
		itemTitle: 'My Apps',
		items: appList,
	},
];

export const initialAccountState: Account[] = [
	{
		externalReferenceCode: '',
		id: 0,
		name: '',
	},
];