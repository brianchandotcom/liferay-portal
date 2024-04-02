/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function usePagination(urlParams?: URLSearchParams) {
	const [activeDelta, setActiveDelta] = useState<number>(
		urlParams?.get('activedelta')
			? Number(urlParams.get('activedelta'))
			: 20
	);

	const [activePage, setActivePage] = useState<number>(
		urlParams?.get('activepage') ? Number(urlParams.get('activepage')) : 1
	);

	useEffect(() => {
		if (urlParams) {
			urlParams.set('activedelta', `${activeDelta}`);
			urlParams.set('activepage', `${activePage}`);
		}
	}, [activeDelta, activePage, urlParams]);

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
