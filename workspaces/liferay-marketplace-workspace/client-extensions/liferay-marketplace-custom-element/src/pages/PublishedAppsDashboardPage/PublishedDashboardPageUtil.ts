import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import appsIcon from '../../assets/icons/apps-fill.svg';
import membersIcon from '../../assets/icons/person-fill.svg';

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

export type MemberProps = {
	accountBriefs: AccountBriefProps[];
	dateCreated: string;
	email: string;
	image: string;
	lastLoginDate: string;
	name: string;
	role: string;
	userId: number;
};

export type ProductResponseProps = {
	catalogId: number;
	externalReferenceCode: string;
	lastUpdatedBy: string;
	name: {en_US: string};
	productId: number;
	workflowStatusInfo: {label: string};
	thumbnail: string;
	modifiedDate: string;
};

export type ProductSpecificationProps = {
	items: [];
	id: number;
	productId: number;
	specificationKey: string;
	value: {};
};

export type RoleBriefProps = {
	id: number;
	name: string;
};

export type UserAccountProps = {
	accountBriefs: AccountBriefProps[];
	dateCreated: string;
	emailAddress: string;
	image: string;
	lastLoginDate: string;
	name: string;
	roleBriefs: RoleBriefProps[];
	id: number;
};
