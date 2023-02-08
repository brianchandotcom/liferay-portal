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

import uriTemplate from 'uri-templates';

import Configuration from '../Configuration';
import languageMap from '../json/languageMap.json';

export default function fetchREST(uriTemplateString, data, languageId) {
	return fetch(
		Configuration.apiServer + uriTemplate(uriTemplateString).fill(data),
		{
			headers: {
				'Accept-Language': languageMap[languageId] || 'en-US',
				'X-CSRF-Token': Liferay.authToken,
			},
			method: 'GET',
		}
	).then((res) => res.json());
}
