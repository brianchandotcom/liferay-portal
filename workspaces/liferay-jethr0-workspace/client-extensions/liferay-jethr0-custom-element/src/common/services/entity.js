/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import api from './liferay/api.js';

const entities = async (name, filter, options = {}) => {
	const params = new URLSearchParams();

	params.append('filter', filter);

	const response = await api('/o/c/' + name, params, options);

	if (!response.ok) {
		throw new Error(response.statusText);
	}

	const data = await response.json();

	return data;
};

export default entities;
