/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Jair Medeiros
 */
public class ContactDeleteKoroneikiMessageConsumer implements MessageConsumer {

	public void consume(String message) {
		if (_log.isInfoEnabled()) {
			JSONObject jsonObject = new JSONObject(message);

			JSONObject contactJSONObject = jsonObject.getJSONObject("contact");

			String contactUUID = contactJSONObject.getString("uuid");

			_log.info("Removed partner members with user UUID " + contactUUID);
		}
	}

	private static final Log _log = LogFactory.getLog(
		ContactDeleteKoroneikiMessageConsumer.class);

}