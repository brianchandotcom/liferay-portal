import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import accountIcon from '../../assets/icons/account-icon.svg';
import appsIcon from '../../assets/icons/apps-fill.svg';
import membersIcon from '../../assets/icons/person-fill.svg';
import salesIcon from '../../assets/icons/sales-icon.svg';

export const appList: AppProps[] = [];

export const initialDashboardNavigationItems: DashboardListItems[] = [
	{
		itemIcon: appsIcon,
		itemName: 'apps',
		itemSelected: true,
		itemTitle: 'Apps',
		items: appList,
	},
	{
		itemIcon: salesIcon,
		itemName: 'sales',
		itemSelected: false,
		itemTitle: 'Sales',
	},
	{
		itemIcon: membersIcon,
		itemName: 'members',
		itemSelected: false,
		itemTitle: 'Members',
	},
	{
		itemIcon: accountIcon,
		itemName: 'account',
		itemSelected: false,
		itemTitle: 'Account',
	},
];
