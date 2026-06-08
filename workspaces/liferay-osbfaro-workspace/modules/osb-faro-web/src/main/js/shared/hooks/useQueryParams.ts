/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation} from 'react-router-dom';
import {getSafeDecodedURIComponent} from '~/shared/util/util';

function decodeQueryParam(param: string) {
	return getSafeDecodedURIComponent(param.replace(/\+/g, ' '));
}

function queryStringToObject(initialQueryString: string): any {
	if (!initialQueryString) {
		return {};
	}

	const queryString = initialQueryString.replace('?', '');

	const params = queryString.split('&');
	const query: {[key: string]: string} = {};

	params.forEach((param) => {
		const [key, value] = param.split('=');
		query[key] = decodeQueryParam(value);
	});

	return query;
}

// TODO: Remove this once we upgrade to react-router-dom v6

export function useQueryParams() {
	const {search} = useLocation();

	return queryStringToObject(search);
}
