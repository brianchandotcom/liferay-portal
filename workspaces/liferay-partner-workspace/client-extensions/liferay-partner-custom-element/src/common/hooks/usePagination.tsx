/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {Liferay} from '../services/liferay';

export default function usePagination() {
	const urlParams = new URLSearchParams(window.location.href.split('?')[1]);

	if (!urlParams.has('activepage')) {
		urlParams.set('activepage', '1');

		window.history.replaceState(
			null,
			'',
			`${Liferay.ThemeDisplay.getLayoutRelativeURL()}?${urlParams.toString()}`
		);
	}

	const [activeDelta, setActiveDelta] = useState<number>(20);
	const [activePage, setActivePage] = useState<number>(
		Number(urlParams.get('activepage'))
	);

	const handlePageChange = (newPage: number) => {
		urlParams.set('activepage', `${newPage}`);
		window.history.replaceState(
			null,
			'',
			`${Liferay.ThemeDisplay.getLayoutRelativeURL()}?${urlParams.toString()}`
		);
		setActivePage(newPage);
	};

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
		onPageChange: handlePageChange,
	};
}
