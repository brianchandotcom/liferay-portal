/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FaroPreferencesDisplay {

	public FaroPreferencesDisplay() {
	}

	public FaroPreferencesDisplay(FaroPreferences faroPreferences)
		throws Exception {

		this(
			faroPreferences,
			JSONUtil.readValue(
				faroPreferences.getPreferences(), WorkspacePreferences.class));
	}

	public FaroPreferencesDisplay(
		FaroPreferences faroPreferences,
		WorkspacePreferences workspacePreferences) {

		_groupId = faroPreferences.getGroupId();
		_ownerId = faroPreferences.getOwnerId();
		_preferences = workspacePreferences;
	}

	public FaroPreferencesDisplay(long groupId, long ownerId) {
		_groupId = groupId;
		_ownerId = ownerId;

		_preferences = new WorkspacePreferences();
	}

	private long _groupId;
	private long _ownerId;
	private WorkspacePreferences _preferences;

}