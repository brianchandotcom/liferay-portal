/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export default function usePagination() {
	const [pageSize, setPageSize] = useState<number>(20);

	const [page, setPage] = useState<number>(1);

	const deltas = [
		{
			label: 20,
		},
		{
			label: 50,
		},
		{
			label: 100,
		},
		{
			label: 200,
		},
	];

	return {
		activeDelta: pageSize,
		activePage: page,
		deltas,
		onDeltaChange: setPageSize,
		onPageChange: setPage,
	};
}
