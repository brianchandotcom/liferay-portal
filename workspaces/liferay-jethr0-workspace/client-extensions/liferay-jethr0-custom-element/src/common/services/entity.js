/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import api from './liferay/api.js';

const entities = async (name, filter, sort, options = {}) => {
	const response = await api('/o/c/' + name, filter, sort, options);

	if (!response.ok) {
		throw new Error(response.statusText);
	}

	const data = await response.json();

	return data;
};

export default entities;
