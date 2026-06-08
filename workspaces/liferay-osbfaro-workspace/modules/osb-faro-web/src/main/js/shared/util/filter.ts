/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {orderBy} from 'lodash';
import {toThousands} from '~/shared/util/numbers';

type FilterTypes = 'devices' | 'location';

export type Filters = {[key in FilterTypes]: string};

export type RawFilters = {[key in FilterTypes]: string[]};

type RawFilterEntry = {metrics: string; valueKey: string};

type FilterInput = {
	category: FilterTypes;
	checked: boolean;
	inputType: 'radio';
	label: string;
	value: string;
};

export const filterLangMap = {
	devices: Liferay.Language.get('devices'),
	location: Liferay.Language.get('location'),
};

/**
 * Get Filters
 */
export const getFilters = function getFilters(
	filters: RawFilters = {devices: [], location: []}
): {
	[key in FilterTypes]: string;
} {
	return (['devices', 'location'] as FilterTypes[]).reduce(
		(acc, cur) =>
			filters[cur] && filters[cur].length
				? {...acc, [cur]: filters[cur][0]}
				: acc,
		{devices: 'Any', location: 'Any'}
	);
};

/**
 * Has Category Filters
 */
export const hasCategoryFilters = function hasCategoryFilters(
	filters: RawFilters,
	category: FilterTypes
) {
	const categoryFilters = filters[category];

	return categoryFilters && !!categoryFilters.length;
};

/**
 * Is Clear Filter Visible
 */
export const isClearFilterVisible = function isClearFilterVisible(
	filters: RawFilters
) {
	if (filters && Object.keys(filters).length > 1) {
		for (const category in filters) {
			if (!hasCategoryFilters(filters, category as FilterTypes)) {
				return false;
			}
		}

		return true;
	}

	return false;
};

/**
 * Map Entry to Filter Item
 */
export const mapEntryToFilterItem = function mapEntryToFilterItem({
	metrics,
	valueKey,
}: RawFilterEntry): Omit<FilterInput, 'category'> {
	return {
		checked: false,
		inputType: 'radio',
		label: valueKey,
		value: toThousands(metrics.length),
	};
};

/**
 * Has Search
 * If there are many filter values, we should include search.
 */
export const hasSearch = function hasSearch(items: any[]) {
	return items.length > 15;
};

type FilterItem = {
	hasSearch: boolean;
	items: FilterInput[];
	label: string;
	name: string;
	value: string;
};

/**
 * Get Filter Item
 */
export const getFilterItem = function getFilterItem(
	data: RawFilterEntry[],
	category: FilterTypes
): FilterItem {
	let items = data.map((item) => ({...mapEntryToFilterItem(item), category}));

	items = orderBy(items, [({label}) => label.toLowerCase()]);

	return {
		hasSearch: hasSearch(items),
		items,
		label: filterLangMap[category],
		name: filterLangMap[category],
		value: toThousands(items.length),
	};
};
