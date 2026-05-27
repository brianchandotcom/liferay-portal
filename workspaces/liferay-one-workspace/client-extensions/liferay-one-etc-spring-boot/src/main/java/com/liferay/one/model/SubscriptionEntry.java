/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class SubscriptionEntry {

	public SubscriptionEntry(JSONObject jsonObject) {
		_className = jsonObject.getString("className");
		_classPK = jsonObject.getLong("classPK");
		_customUserId = jsonObject.getLong("customUserId");
		_subscriptionEntryId = jsonObject.getLong("id");
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getCustomUserId() {
		return _customUserId;
	}

	public long getSubscriptionEntryId() {
		return _subscriptionEntryId;
	}

	private final String _className;
	private final long _classPK;
	private final long _customUserId;
	private final long _subscriptionEntryId;

}