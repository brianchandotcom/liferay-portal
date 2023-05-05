import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import solutionsIcon from '../../assets/icons/analytics-icon.svg';
import appsIcon from '../../assets/icons/apps-fill.svg';
import businessIcon from '../../assets/icons/business-center.svg';
import membersIcon from '../../assets/icons/person-fill.svg';
import projectsIcon from '../../assets/icons/projects-icon.svg';

export const appList: AppProps[] = [];

export type AccountBriefProps = {
	externalReferenceCode: string;
	id: number;
	name: string;
};

export type CatalogProps = {
	externalReferenceCode: string;
	id: number;
	name: string;
};

export const customerRoles = [
	'Account Administrator',
	'Account Buyer',
	'Account Member',
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
		itemIcon: solutionsIcon,
		itemName: 'solutions',
		itemSelected: false,
		itemTitle: 'Solutions',
	},
	{
		itemIcon: projectsIcon,
		itemName: 'projects',
		itemSelected: false,
		itemTitle: 'Projects',
	},
	{
		itemIcon: membersIcon,
		itemName: 'members',
		itemSelected: false,
		itemTitle: 'Members',
	},
	{
		itemIcon: businessIcon,
		itemName: 'account',
		itemSelected: false,
		itemTitle: 'Account',
	},
];

export type MemberProps = {
	accountBriefs: AccountBriefProps[];
	dateCreated: string;
	email: string;
	image: string;
	isCustomerAccount: boolean;
	isPublisherAccount: boolean;
	lastLoginDate: string;
	name: string;
	role: string;
	userId: number;
};

export type ProductResponseProps = {
	catalogId: number;
	externalReferenceCode: string;
	lastUpdatedBy: string;
	modifiedDate: string;
	name: {en_US: string};
	productId: number;
	thumbnail: string;
	workflowStatusInfo: {label: string};
};

export const publisherRoles = ['Account Administrator', 'App Editor'];

export type RoleBriefProps = {
	id: number;
	name: string;
};

export type UserAccountProps = {
	accountBriefs: AccountBrief[];
	dateCreated: string;
	emailAddress: string;
	id: number;
	image: string;
	lastLoginDate: string;
	name: string;
	roleBriefs: RoleBriefProps[];
};
