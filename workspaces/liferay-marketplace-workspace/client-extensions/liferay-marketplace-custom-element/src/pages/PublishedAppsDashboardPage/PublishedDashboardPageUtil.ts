import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import appsIcon from '../../assets/icons/apps-fill.svg';
import membersIcon from '../../assets/icons/person-fill.svg';

export const appList: AppProps[] = [];

export type MemberProps = {
	dateCreated: string;
	email: string;
	image: string;
	lastLoginDate: string;
	name: string;
	role: string;
	userId: number;
};

export const initialDashboardNavigationItems: DashboardListItems[] = [
	{
		itemIcon: appsIcon,
		itemName: 'apps',
		itemSelected: true,
		itemTitle: 'Apps',
		items: appList,
	},
	{
		itemIcon: membersIcon,
		itemName: 'members',
		itemSelected: false,
		itemTitle: 'Members',
	},
];
