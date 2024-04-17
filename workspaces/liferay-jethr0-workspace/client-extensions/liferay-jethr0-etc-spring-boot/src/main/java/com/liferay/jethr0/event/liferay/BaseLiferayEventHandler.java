/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.event.EventHandlerContext;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseLiferayEventHandler extends BaseEventHandler {

	protected BaseLiferayEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}