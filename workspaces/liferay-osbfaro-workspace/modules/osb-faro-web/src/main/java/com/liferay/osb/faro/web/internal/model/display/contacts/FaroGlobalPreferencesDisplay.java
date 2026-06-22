/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.GlobalPreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FaroGlobalPreferencesDisplay {

	public FaroGlobalPreferencesDisplay() {
		_preferences = new GlobalPreferences();
	}

	public FaroGlobalPreferencesDisplay(FaroPreferences faroPreferences)
		throws Exception {

		this(
			faroPreferences,
			JSONUtil.readValue(
				faroPreferences.getPreferences(), GlobalPreferences.class));
	}

	public FaroGlobalPreferencesDisplay(
		FaroPreferences faroPreferences, GlobalPreferences globalPreferences) {

		_preferences = globalPreferences;
	}

	private GlobalPreferences _preferences;

}