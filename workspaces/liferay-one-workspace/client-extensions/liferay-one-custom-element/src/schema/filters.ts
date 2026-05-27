/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Params} from 'react-router-dom';

import SearchBuilder, {Operators} from '../core/SearchBuilder';
import i18n from '../i18n';

type AutoCompleteProps = {
	label?: string;
	onSearch: (keyword: string) => any;
	resource?: string | ((params: Readonly<Params<string>>) => string);
	transformData?: (item: any) => any;
};

export type AppliedFilters = {
	label: string;
	value: string;
};

export type RenderedFieldOptions = string[] | AppliedFilters[];

export type RendererFields = {
	disabled?: boolean;
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

export const baseFilters: Filter = {
	categories: {
		label: i18n.translate('category'),
		name: 'categoryNames',
		operator: 'lambda',
		type: 'select',
	},
	dateCreated: {
		label: i18n.translate('date-created'),
		name: 'createDate',
		type: 'date-range',
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

export function overrides(
	object: RendererFields,
	newObject: Partial<RendererFields>
): RendererFields {
	return {
		...object,
		...newObject,
	};
}

export const filterSchema: FilterSchemas = {};

export type FilterSchemaOption = keyof typeof filterSchema;
