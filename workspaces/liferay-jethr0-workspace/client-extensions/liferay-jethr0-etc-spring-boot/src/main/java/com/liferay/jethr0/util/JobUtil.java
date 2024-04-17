/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import java.util.Date;

/**
 * @author Michael Hashimoto
 */
public class JobUtil {

	public static String getUpdateJobEntityName(String jobName) {
		if (jobName == null) {
			return null;
		}

		if (jobName.contains("$(current_date)")) {
			jobName = jobName.replaceAll(
				"\\$\\(current_date\\)", String.valueOf(new Date()));
		}

		return jobName;
	}

}