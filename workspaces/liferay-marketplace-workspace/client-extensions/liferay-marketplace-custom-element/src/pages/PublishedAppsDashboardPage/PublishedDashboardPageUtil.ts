import {DashboardListItems} from 'liferay-marketplace-custom-element/src/components/DashboardNavigation/DashboardNavigation';
import {AppProps} from 'liferay-marketplace-custom-element/src/components/DashboardTable/DashboardTable';

import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import businessIcon from '../../assets/icons/business_center_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';
import projectsIcon from '../../assets/icons/projects_icon.svg';
import {Liferay} from '../../liferay/liferay';
import {getProductSpecifications} from '../../utils/api';

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
		items: [] as AppProps[],
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

export function formatDate(date: string) {
	const locale = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

	const dateOptions: Intl.DateTimeFormatOptions = {
		day: 'numeric',
		month: 'short',
		year: 'numeric',
	};

	const formattedDate = new Intl.DateTimeFormat(locale, dateOptions).format(
		new Date(date)
	);

	return formattedDate;
}

export async function getAppListProductSpecifications(productIds: number[]) {
	return await Promise.all(
		productIds.map(async (productId) => {
			return await getProductSpecifications({
				appProductId: productId,
			});
		})
	);
}

export function getAppListProductIds(products: {items: Product[]}) {
	const productIds: number[] = [];

	products.items.map((product) => {
		productIds.push(product.productId);
	});

	return productIds;
}

export function getProductTypeFromSpecifications(
	specifications: ProductSpecification[]
) {
	let productType = 'no type';

	specifications.forEach((specification: ProductSpecification) => {
		if (specification.specificationKey === 'type') {
			productType = specification.value.en_US;

			if (productType === 'cloud') {
				productType = 'Cloud';
			}
			else if (productType === 'dxp') {
				productType = 'DXP';
			}
		}
	});

	return productType;
}

export function getRolesList(
	accountBriefs: AccountBrief[],
	selectedAccountId: number
) {
	const rolesList: string[] = [];

	const accountBrief = accountBriefs.find(
		(accountBrief) => accountBrief.id === selectedAccountId
	);

	accountBrief?.roleBriefs.forEach((role) => {
		rolesList.push(role.name);
	});

	return rolesList.join(', ');
}
