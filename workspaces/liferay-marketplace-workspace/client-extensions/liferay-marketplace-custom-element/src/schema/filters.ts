/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Params} from 'react-router-dom';

import SearchBuilder, {Operators} from '../core/SearchBuilder';
import {LiferayVersionList} from '../enums/Liferay';
import {ProductWorkflowStatusCode} from '../enums/Product';
import i18n from '../i18n';

type AutoCompleteProps = {
	label?: string;
	onSearch: (keyword: string) => any;
	resource?: ((params: Readonly<Params<string>>) => string) | string;
	transformData?: (item: any) => any;
};

export type RenderedFieldOptions = string[] | {label: string; value: string}[];

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
	appliedFilter: {
		[key: string]: string | {label: string; value: string};
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
		label: 'Date Created',
		name: 'createDate',
		type: 'date-range',
	},
	name: {
		label: 'Name',
		name: 'name/en_US',
		type: 'text',
	},
	status: {
		label: 'Status',
		name: 'status',
		type: 'select',
	},
	type: {
		label: 'Label',
		name: 'type',
		type: 'select',
	},
	version: {
		label: 'Version',
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
				label: 'App Type',
				name: 'specificationValues|appType',
				operator: 'lambda',
				options: [
					{
						label: 'Client Extension',
						value: 'client-extension',
					},
					{
						label: 'Cloud App',
						value: 'cloud',
					},
					{
						label: 'Composite App',
						value: 'composite-app',
					},
					{
						label: 'DXP',
						value: 'dxp',
					},
					{
						label: 'Low Code Configuration',
						value: 'low-code-configuration',
					},
					{
						label: 'Other',
						value: 'other',
					},
				],
				type: 'checkbox',
			}),
			overrides(baseFilters.dateRange, {
				label: 'Created Date',
			}),
			overrides(baseFilters.version, {
				label: 'Liferay Version',
				name: 'specificationValues|liferayVersion',
				operator: 'lambda',
				options: LiferayVersionList,
				type: 'checkbox',
			}),
			overrides(baseFilters.dateRange, {
				label: 'Modified Date',
				name: 'modifiedDate',
			}),
			overrides(baseFilters.status, {
				label: 'Status',
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
				label: 'App Type',
				name: 'orderTypeExternalReferenceCode',
				options: [
					{
						label: 'Client Extension',
						value: 'CLIENT_EXTENSION',
					},
					{
						label: 'Cloud App',
						value: 'CLOUDAPP',
					},
					{
						label: 'Composite App',
						value: 'COMPOSITE_APP',
					},
					{
						label: 'DXP',
						value: 'DXPAPP',
					},
					{
						label: 'Low Code Configuration',
						value: 'LOW_CODE_CONFIGURATION',
					},
					{
						label: 'Other',
						value: 'OTHER',
					},
				],
				type: 'checkbox',
			}),
			overrides(baseFilters.status, {
				label: 'Status',
				name: 'orderStatus',
				options: [
					{label: 'Completed', value: '0'},
					{label: 'Pending', value: '1'},
					{label: 'In Progress', value: '6'},
					{label: 'Cancelled', value: '8'},
					{label: 'Processing', value: '10'},
					{label: 'On Hold', value: '20'},
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
				label: 'Account Type',
				name: 'customFields/AccountType',
				options: [
					'Marketplace Developer',
					'Technology Partner',
					'Strategic Partner',
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
				label: 'Modified Date',
				name: 'modifiedDate',
			}),
			overrides(baseFilters.status, {
				label: 'Status',
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
