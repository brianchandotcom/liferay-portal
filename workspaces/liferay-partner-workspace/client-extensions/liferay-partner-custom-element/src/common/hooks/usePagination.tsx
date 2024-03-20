/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export default function usePagination(initialActivePage = 1) {
	const [activeDelta, setActiveDelta] = useState<number>(20);
	const [activePage, setActivePage] = useState<number>(initialActivePage);

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
		activeDelta,
		activePage,
		deltas,
		onDeltaChange: setActiveDelta,
		onPageChange: setActivePage,
	};
}
