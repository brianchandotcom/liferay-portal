/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRoutineLiferayEventHandler extends BaseEventHandler {

	protected BaseRoutineLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected JSONObject getRoutineJSONObject() {
		JSONObject routineJSONObject = new JSONObject();

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject objectEntryDTORoutineJSONObject =
			messageJSONObject.getJSONObject("objectEntryDTORoutine");

		routineJSONObject.put(
			"dateCreated",
			StringUtil.toString(
				new Date(
					objectEntryDTORoutineJSONObject.getLong("dateCreated")))
		).put(
			"dateModified",
			StringUtil.toString(
				new Date(
					objectEntryDTORoutineJSONObject.getLong("dateModified")))
		).put(
			"externalReferenceCode",
			objectEntryDTORoutineJSONObject.getString("externalReferenceCode")
		).put(
			"id", objectEntryDTORoutineJSONObject.getLong("id")
		);

		JSONObject propertiesJSONObject =
			objectEntryDTORoutineJSONObject.getJSONObject("properties");

		for (String key : propertiesJSONObject.keySet()) {
			routineJSONObject.put(key, propertiesJSONObject.get(key));
		}

		return routineJSONObject;
	}

}