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
public abstract class BaseJobLiferayEventHandler extends BaseEventHandler {

	protected BaseJobLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected JSONObject getJobJSONObject() {
		JSONObject jobJSONObject = new JSONObject();

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject objectEntryDTOJobJSONObject =
			messageJSONObject.getJSONObject("objectEntryDTOJob");

		jobJSONObject.put(
			"dateCreated",
			StringUtil.toString(
				new Date(objectEntryDTOJobJSONObject.getLong("dateCreated")))
		).put(
			"dateModified",
			StringUtil.toString(
				new Date(objectEntryDTOJobJSONObject.getLong("dateModified")))
		).put(
			"externalReferenceCode",
			objectEntryDTOJobJSONObject.getString("externalReferenceCode")
		).put(
			"id", objectEntryDTOJobJSONObject.getLong("id")
		);

		JSONObject propertiesJSONObject =
			objectEntryDTOJobJSONObject.getJSONObject("properties");

		for (String key : propertiesJSONObject.keySet()) {
			jobJSONObject.put(key, propertiesJSONObject.get(key));
		}

		return jobJSONObject;
	}

}