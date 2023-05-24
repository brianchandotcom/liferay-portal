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

package com.liferay.partner.serdes;

import com.liferay.partner.dto.Activity;
import com.liferay.partner.parser.ActivityJSONParser;

import java.util.Map;

/**
 * @author Jair Medeiros
 */
public class ActivitySerDes {

	public static Activity toDTO(String json) {
		ActivityJSONParser activityJSONParser = new ActivityJSONParser();

		return activityJSONParser.parseToDTO(json);
	}

	public static Activity[] toDTOs(String json) {
		ActivityJSONParser activityJSONParser = new ActivityJSONParser();

		return activityJSONParser.parseToDTOs(json);
	}

	public static Map<String, Object> toMap(String json) {
		ActivityJSONParser activityJSONParser = new ActivityJSONParser();

		return activityJSONParser.parseToMap(json);
	}

}