/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters() {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	const [filtersTerm, setFilterTerm] = useState('');

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

	useEffect(() => {
		let initialFilter = '';

		if (filters.searchTerm) {
			initialFilter = getSearchFilterTerm(filters.searchTerm);
		}

		setFilterTerm(initialFilter);
	}, [filters.searchTerm, setFilters]);

	return {filters, filtersTerm, onFilter};
}
