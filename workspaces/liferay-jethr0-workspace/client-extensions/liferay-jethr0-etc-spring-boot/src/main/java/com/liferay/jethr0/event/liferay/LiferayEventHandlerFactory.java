/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class LiferayEventHandlerFactory implements EventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		String entityType = _getEntityType(messageJSONObject);

		if (StringUtil.isNullOrEmpty(entityType)) {
			throw new IllegalArgumentException(
				"Missing \"objectEntryDTO[EntityName]\" from message JSON");
		}

		if (entityType.equals("Job")) {
			String objectActionTriggerKey = messageJSONObject.optString(
				"objectActionTriggerKey");

			if (objectActionTriggerKey.equals("onAfterDelete")) {
				return new DeleteJobLiferayEventHandler(messageJSONObject);
			}
			else if (objectActionTriggerKey.equals("onAfterUpdate")) {
				return new UpdateJobLiferayEventHandler(messageJSONObject);
			}

			return new AddJobLiferayEventHandler(messageJSONObject);
		}
		else if (entityType.equals("Routine")) {
			String objectActionTriggerKey = messageJSONObject.optString(
				"objectActionTriggerKey");

			if (objectActionTriggerKey.equals("onAfterDelete")) {
				return new DeleteRoutineLiferayEventHandler(messageJSONObject);
			}
			else if (objectActionTriggerKey.equals("onAfterUpdate")) {
				return new UpdateRoutineLiferayEventHandler(messageJSONObject);
			}

			return new AddRoutineLiferayEventHandler(messageJSONObject);
		}

		throw new IllegalArgumentException(
			"Unsupported entityType " + entityType);
	}

	private String _getEntityType(JSONObject messageJSONObject) {
		for (String key : messageJSONObject.keySet()) {
			Matcher matcher = _objectEntityDTOPattern.matcher(key);

			if (matcher.matches()) {
				return matcher.group("entityType");
			}
		}

		return null;
	}

	private static final Pattern _objectEntityDTOPattern = Pattern.compile(
		"objectEntryDTO(?<entityType>\\w+)");

}