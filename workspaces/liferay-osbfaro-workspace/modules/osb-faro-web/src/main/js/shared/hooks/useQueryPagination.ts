/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, OrderedMap, Set} from 'immutable';
import {pick} from 'lodash';
import {useQueryParams} from '~/shared/hooks/useQueryParams';
import {FilterByType, Pagination} from '~/shared/types';
import {createOrderIOMap, paginationDefaults} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';

const {
	delta: defaultDelta,
	filterBy: defaultFilterBy,
	page: defaultPage,
	query: defaultQuery,
} = paginationDefaults;

type QueryPaginationParams = {
	filterFields?: string[];
	initialDelta?: number;
	initialFilterBy?: FilterByType;
	initialOrderIOMap?: OrderedMap<string, OrderParams>;
	initialPage?: number;
	initialQuery?: string;
};

export const useQueryPagination = function useQueryPagination({
	filterFields,
	initialDelta = defaultDelta,
	initialFilterBy = defaultFilterBy,
	initialOrderIOMap,
	initialPage = defaultPage,
	initialQuery = defaultQuery,
}: QueryPaginationParams): Pagination {
	const {
		delta = initialDelta,
		field,
		page = initialPage,
		query = initialQuery,
		sortOrder,
		...otherParams
	} = useQueryParams();

	const getFilterByFromFields = (): FilterByType => {
		const filterProps = pick(otherParams, filterFields as string[]);

		return Map(
			Object.keys(filterProps).reduce<{[key: string]: Set<string>}>(
				(acc, currentKey) => {
					const filterValues = filterProps[currentKey] as string;

					acc[currentKey] = filterValues
						? Set(filterValues.split(','))
						: Set();

					return acc;
				},
				{}
			)
		);
	};

	let orderIOMap = initialOrderIOMap ?? OrderedMap<string, OrderParams>();

	if (field && sortOrder) {
		orderIOMap = createOrderIOMap(field, sortOrder);
	}

	let filterBy = initialFilterBy;

	if (filterFields) {
		filterBy = getFilterByFromFields();
	}

	return {
		delta: parseInt(delta as string, 10),
		filterBy,
		orderIOMap,
		page: parseInt(page as string, 10),
		query: query as string,
	};
};
