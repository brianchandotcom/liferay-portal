/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {REACT_APP_LIFERAY_HOST = window.location.origin} = process.env;

const liferayRequest = async ({
	body,
	graphqlQuery,
	headers = {},
	method = 'GET',
	options = {},
	urlPath = '/',
	urlSearchParams = new URLSearchParams(),
}) => {
	headers['x-csrf-token'] = Liferay.authToken;

	if (!body && graphqlQuery) {
		graphqlQuery = graphqlQuery.replace(/\s+/g, ' ');

		body = `{"query": "query ${graphqlQuery}"}`;
	}

	return fetch(REACT_APP_LIFERAY_HOST + urlPath + '?' + urlSearchParams, {
		body,
		headers,
		method,
		...options,
	});
};

export default liferayRequest;
