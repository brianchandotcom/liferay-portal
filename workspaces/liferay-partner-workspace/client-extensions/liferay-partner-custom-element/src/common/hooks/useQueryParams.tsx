/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Liferay} from '../services/liferay';

export default function useQueryParams() {
	const [urlParams] = useState(new URLSearchParams(window.location.search));
	const urlParamsEntries = urlParams.entries();

	useEffect(() => {
		window.history.replaceState(
			null,
			'',
			`${Liferay.ThemeDisplay.getLayoutRelativeURL()}?${urlParams.toString()}`
		);
	}, [urlParams, urlParamsEntries]);

	return urlParams;
}
