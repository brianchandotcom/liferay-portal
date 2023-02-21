import accountIcon from '../../assets/icons/account-icon.svg';
import appIconPayments from '../../assets/icons/app-icon-payments.svg';
import appIconSales from '../../assets/icons/app-icon-sales.svg';
import appIconTransport from '../../assets/icons/app-icon-transport.svg';
import appsIcon from '../../assets/icons/apps-fill.svg';
import membersIcon from '../../assets/icons/person-fill.svg';
import salesIcon from '../../assets/icons/sales-icon.svg';
import {DashboardListItems} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from './../../components/DashboardTable/DashboardTable';

export const appList: AppProps[] = [
	{
		image: appIconTransport,
		name: 'A&Co Transport',
		rating: '4.3',
		selected: false,
		status: 'Published',
		type: 'SaaS',
		updatedBy: 'by Hanna White',
		updatedDate: 'Feb 14, 2023',
		updatedResponsible: 'you',
		version: '1.40',
	},
	{
		image: appIconSales,
		name: 'A&Co Sales',
		rating: '4.7',
		selected: false,
		status: 'Pending',
		type: 'OSGI',
		updatedBy: 'by Hanna White',
		updatedDate: 'Feb 14, 2023',
		updatedResponsible: 'you',
		version: '2.28',
	},
	{
		image: appIconPayments,
		name: 'A&Co Payments',
		rating: '4.1',
		selected: false,
		status: 'Hidden',
		type: 'OSGI',
		updatedBy: 'by Hanna White',
		updatedDate: 'Feb 14, 2023',
		updatedResponsible: 'you',
		version: '1.0',
	},
];

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
