/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mahmoud Hussein Tayem
 */
public class JsonUtils {

	public static String toJSON(Object dataObject)
		throws JsonProcessingException {

		return _objectMapper.writeValueAsString(dataObject);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}