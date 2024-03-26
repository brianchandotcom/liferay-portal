/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {REACT_APP_LIFERAY_HOST = window.location.origin} = process.env;

const liferayRequest = async (urlPath, headers = {}, options = {}) => {
	headers['x-csrf-token'] = Liferay.authToken;

	return fetch(REACT_APP_LIFERAY_HOST + '/' + urlPath, {
		headers: headers,
		...options,
	});
};

export default liferayRequest;
