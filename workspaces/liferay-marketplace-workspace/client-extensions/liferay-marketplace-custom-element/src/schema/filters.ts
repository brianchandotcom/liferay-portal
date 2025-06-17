/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Params} from 'react-router-dom';

import SearchBuilder, {Operators} from '../core/SearchBuilder';
import {AccountType} from '../enums/Account';
import {LiferayVersionList} from '../enums/Liferay';
import {OrderTypes} from '../enums/Order';
import {ProductType, ProductWorkflowStatusCode} from '../enums/Product';
import i18n from '../i18n';

type AutoCompleteProps = {
	label?: string;
	onSearch: (keyword: string) => any;
	resource?: ((params: Readonly<Params<string>>) => string) | string;
	transformData?: (item: any) => any;
};

type AppliedFilters = {
	label: string;
	value: string;
};

export type RenderedFieldOptions = string[] | AppliedFilters[];

export type RendererFields = {
	disabled?: boolean;
	isCustomFilter?: boolean;
	label: string;
	name: string;
	operator?: Operators;
	optionalOperator?: Operators;
	options?: RenderedFieldOptions;
	placeholder?: string;
	removeQuoteMark?: boolean;
	requestOperator?: string;
	type:
		| 'autocomplete'
		| 'checkbox'
		| 'date'
		| 'date-range'
		| 'multiselect'
		| 'number'
		| 'select'
		| 'text'
		| 'textarea';
} & Partial<AutoCompleteProps>;

export type Filters = {
	[key: string]: RendererFields[];
};

export type Filter = {
	[key: string]: RendererFields;
};

export type FilterVariables = {
	appliedFilter?: {
		[key: string]: string | AppliedFilters;
	};
	defaultFilter?: string | SearchBuilder;
	filterSchema: FilterSchema;
};

export type FilterSchema = {
	fields: RendererFields[];
	name?: string;
	onApply?: (filterVariables: FilterVariables) => string;
	placeholder?: string;
};

export type FilterSchemas = {
	[key: string]: FilterSchema;
};

export type FilterSchemaOption = keyof typeof filterSchema;

const baseFilters: Filter = {
	dateRange: {
		label: i18n.translate('date-created'),
		name: 'createDate',
		type: 'date-range',
	},
	name: {
		label: i18n.translate('name'),
		name: 'name/en_US',
		type: 'text',
	},
	status: {
		label: i18n.translate('status'),
		name: 'status',
		type: 'select',
	},
	type: {
		label: i18n.translate('type'),
		name: 'type',
		type: 'select',
	},
	version: {
		label: i18n.translate('version'),
		name: 'version',
		type: 'select',
	},
};

const overrides = (
	object: RendererFields,
	newObject: Partial<RendererFields>
) => ({
	...object,
	...newObject,
});

const filterSchema = {
	administratorDashboardAppsTable: {
		fields: [
			overrides(baseFilters.type, {
				label: i18n.translate('app-type'),
				name: 'specificationValues|appType',
				operator: 'lambda',
				options: [
					{
						label: i18n.translate('client-extension'),
						value: ProductType.CLIENT_EXTENSION,
					},
					{
						label: i18n.translate('cloud-app'),
						value: ProductType.CLOUD,
					},
					{
						label: i18n.translate('composite-app'),
						value: ProductType.COMPOSITE_APP,
					},
					{
						label: i18n.translate('dxp-app'),
						value: ProductType.DXP,
					},
					{
						label: i18n.translate('low-code-configuration'),
						value: ProductType.LOW_CODE_CONFIGURATION,
					},
					{
						label: i18n.translate('other'),
						value: ProductType.OTHER,
					},
				],
				type: 'checkbox',
			}),
			baseFilters.dateRange,
			overrides(baseFilters.version, {
				label: i18n.translate('liferay-version'),
				name: 'specificationValues|liferayVersion',
				operator: 'lambda',
				options: LiferayVersionList,
				type: 'multiselect',
			}),
			overrides(baseFilters.dateRange, {
				label: i18n.translate('modified-date'),
				name: 'modifiedDate',
			}),
			overrides(baseFilters.status, {
				name: 'statusCode',
				options: [
					{
						label: i18n.translate('approved'),
						value: `${ProductWorkflowStatusCode.APPROVED}`,
					},
					{
						label: i18n.translate('draft'),
						value: `${ProductWorkflowStatusCode.DRAFT}`,
					},
					{
						label: i18n.translate('pending'),
						value: `${ProductWorkflowStatusCode.PENDING}`,
					},
				],
				removeQuoteMark: true,
				type: 'multiselect',
			}),
		],
		name: 'administratorDashboardAppsTable',
	},
	administratorDashboardOrdersTable: {
		fields: [
			overrides(baseFilters.type, {
				label: i18n.translate('app-type'),
				name: 'orderTypeExternalReferenceCode',
				options: [
					{
						label: i18n.translate('client-extension'),
						value: OrderTypes.CLIENT_EXTENSION,
					},
					{
						label: i18n.translate('cloud-app'),
						value: OrderTypes.CLOUDAPP,
					},
					{
						label: i18n.translate('composite-app'),
						value: OrderTypes.COMPOSITE_APP,
					},
					{
						label: i18n.translate('dxp-app'),
						value: OrderTypes.DXPAPP,
					},
					{
						label: i18n.translate('low-code-configuration'),
						value: OrderTypes.LOW_CODE_CONFIGURATION,
					},
					{
						label: i18n.translate('other'),
						value: OrderTypes.OTHER,
					},
				],
				type: 'checkbox',
			}),
			overrides(baseFilters.status, {
				name: 'orderStatus',
				options: [
					{label: i18n.translate('completed'), value: '0'},
					{label: i18n.translate('pending'), value: '1'},
					{label: i18n.translate('in-progress'), value: '6'},
					{label: i18n.translate('canceled'), value: '8'},
					{label: i18n.translate('processing'), value: '10'},
					{label: i18n.translate('on-hold'), value: '20'},
				],
				removeQuoteMark: true,
				type: 'multiselect',
			}),
			baseFilters.dateRange,
		],
		name: 'administratorDashboardOrdersTable',
	},
	administratorDashboardPublishersTable: {
		fields: [
			overrides(baseFilters.type, {
				label: i18n.translate('account-type'),
				name: 'customFields/AccountType',
				options: [
					AccountType.MARKETPLACE_DEVELOPER,
					AccountType.STRATEGIC_PARTNER,
					AccountType.TECHNOLOGY_PARTNER,
				],
				type: 'multiselect',
			}),
			overrides(baseFilters.dateRange, {
				name: 'dateCreated',
			}),
		],
		name: 'administratorDashboardSolutionsTable',
	},
	administratorDashboardSolutionsTable: {
		fields: [
			overrides(baseFilters.dateRange, {
				label: i18n.translate('modified-date'),
				name: 'modifiedDate',
			}),
			overrides(baseFilters.status, {
				name: 'statusCode',
				options: [
					{
						label: i18n.translate('approved'),
						value: `${ProductWorkflowStatusCode.APPROVED}`,
					},
					{
						label: i18n.translate('draft'),
						value: `${ProductWorkflowStatusCode.DRAFT}`,
					},
					{
						label: i18n.translate('pending'),
						value: `${ProductWorkflowStatusCode.PENDING}`,
					},
				],
				removeQuoteMark: true,
				type: 'multiselect',
			}),
			baseFilters.dateRange,
		],
		name: 'administratorDashboardSolutionsTable',
	},
};

export {filterSchema};
