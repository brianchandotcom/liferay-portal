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

package com.liferay.partner.parser;

import com.liferay.headless.admin.list.type.client.serdes.v1_0.ListTypeDefinitionSerDes;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;
import com.liferay.partner.dto.Activity;

import java.util.Objects;

/**
 * @author Jair Medeiros
 */
public class ActivityJSONParser extends BaseJSONParser<Activity> {

	@Override
	public Activity createDTO() {
		return new Activity();
	}

	@Override
	public Activity[] createDTOArray(int size) {
		return new Activity[size];
	}

	@Override
	public void setField(
		Activity activity, String jsonParserFieldName,
		Object jsonParserFieldValue) {

		if (Objects.equals(jsonParserFieldName, "id")) {
			if (jsonParserFieldValue != null) {
				activity.setId(Long.valueOf((String)jsonParserFieldValue));
			}
		}
		else if (Objects.equals(jsonParserFieldName, "activityStatus")) {
			if (jsonParserFieldValue != null) {
				activity.setActivityStatus(
					ListTypeDefinitionSerDes.toDTO(jsonParserFieldName));
			}
		}
	}

}