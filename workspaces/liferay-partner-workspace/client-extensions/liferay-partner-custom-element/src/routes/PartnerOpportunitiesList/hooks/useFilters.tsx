/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters() {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	const [filtersTerm, setFilterTerm] = useState('');

	const onFilter = useCallback(
		(newFilters: Partial<typeof INITIAL_FILTER>) =>
			setFilters((previousFilters) => ({
				...previousFilters,
				...newFilters,
			})),
		[]
	);

	useEffect(
		() =>
			setFilterTerm(
				filters.searchTerm
					? getSearchFilterTerm(filters.searchTerm)
					: ''
			),
		[filters.searchTerm, setFilters]
	);

	return {filters, filtersTerm, onFilter};
}
