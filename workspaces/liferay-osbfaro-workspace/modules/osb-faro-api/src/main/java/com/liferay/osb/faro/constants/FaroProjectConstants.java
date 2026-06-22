/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroProjectConstants {

	public static final String STATE_ACTIVATING = "ACTIVATING";

	public static final String STATE_AUTO_REDEPLOY_FAILED =
		"AUTO_REDEPLOY_FAILED";

	public static final String STATE_DEACTIVATED = "DEACTIVATED";

	public static final String STATE_MAINTENANCE = "MAINTENANCE";

	public static final String STATE_NOT_READY = "NOT READY";

	public static final String STATE_READY = "READY";

	public static final String STATE_SCHEDULED = "SCHEDULED";

	public static final String STATE_UNAVAILABLE = "UNAVAILABLE";

	public static final String STATE_UNCONFIGURED = "UNCONFIGURED";

	public static Map<String, String> getStates() {
		return _states;
	}

	private static final Map<String, String> _states = HashMapBuilder.put(
		"activating", STATE_ACTIVATING
	).put(
		"autoRedeployFailed", STATE_AUTO_REDEPLOY_FAILED
	).put(
		"deactivated", STATE_DEACTIVATED
	).put(
		"maintenance", STATE_MAINTENANCE
	).put(
		"notReady", STATE_NOT_READY
	).put(
		"ready", STATE_READY
	).put(
		"scheduled", STATE_SCHEDULED
	).put(
		"unavailable", STATE_UNAVAILABLE
	).put(
		"unconfigured", STATE_UNCONFIGURED
	).build();

}