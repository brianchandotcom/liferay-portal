/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from './liferay.js';

const {REACT_APP_LIFERAY_HOST = window.location.origin} = process.env;

const baseFetch = async (url, filter, sort, options = {}) => {
	const urlObject = new URL(REACT_APP_LIFERAY_HOST + url);

	const urlSearchParams = new URLSearchParams(urlObject.search);

	if ((filter !== null) && (filter !== undefined)) {
		urlSearchParams.append('filter', filter);
	}

	if ((sort !== null) && (sort !== undefined)) {
		urlSearchParams.append('sort', sort);
	}

	urlObject.search = urlSearchParams.toString();

	return fetch(urlObject, {
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		...options,
	});
};

export default baseFetch;
