/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LifecycleStages} from '~/contacts/pages/account/utils/constants';

export interface ILifecycleFilterValues {
	countryFilter: string;
	industryFilter: string;
	lifecycleStageFilter?: LifecycleStages;
}

const FILTER_CONFIG: {
	field: keyof ILifecycleFilterValues;
	fieldName: string;
	op: string;
}[] = [
	{field: 'countryFilter', fieldName: 'country', op: 'eq'},
	{field: 'industryFilter', fieldName: 'industry', op: 'eq'},
];

export const buildQueryString = function buildQueryString(
	values: ILifecycleFilterValues
): string {
	return FILTER_CONFIG.map(({field, fieldName, op}) => {
		const val = values[field];

		return val !== '' ? `${fieldName} ${op} '${val}'` : null;
	})
		.filter(Boolean)
		.join(' and ');
};
