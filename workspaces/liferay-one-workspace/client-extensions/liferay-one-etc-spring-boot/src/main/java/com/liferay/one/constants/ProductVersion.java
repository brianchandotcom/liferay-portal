/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kyle Bischof
 */
public class ProductVersion {

	public static final String COMMERCE_LICENSE_VERSION_1 = "1";

	public static final String DXP_VERSION_7_0 = "7.0";

	public static final String DXP_VERSION_7_1 = "7.1";

	public static final String DXP_VERSION_7_2 = "7.2";

	public static final String DXP_VERSION_7_3 = "7.3";

	public static final String DXP_VERSION_7_4 = "7.4";

	public static final String PORTAL_VERSION_5_1_3 = "5.1";

	public static final String PORTAL_VERSION_5_1_4 = "5.1 SP1";

	public static final String PORTAL_VERSION_5_1_5 = "5.1 SP2";

	public static final String PORTAL_VERSION_5_1_6 = "5.1 SP3";

	public static final String PORTAL_VERSION_5_1_7 = "5.1 SP4";

	public static final String PORTAL_VERSION_5_1_8 = "5.1 SP5";

	public static final String PORTAL_VERSION_5_2_4 = "5.2";

	public static final String PORTAL_VERSION_5_2_5 = "5.2 SP1";

	public static final String PORTAL_VERSION_5_2_6 = "5.2 SP2";

	public static final String PORTAL_VERSION_5_2_7 = "5.2 SP3";

	public static final String PORTAL_VERSION_5_2_8 = "5.2 SP4";

	public static final String PORTAL_VERSION_5_2_9 = "5.2 SP5";

	public static final String PORTAL_VERSION_6_0_10 = "6.0";

	public static final String PORTAL_VERSION_6_0_11 = "6.0 SP1";

	public static final String PORTAL_VERSION_6_0_12 = "6.0 SP2";

	public static final String PORTAL_VERSION_6_1_10 = "6.1 GA1";

	public static final String PORTAL_VERSION_6_1_20 = "6.1 GA2";

	public static final String PORTAL_VERSION_6_1_30 = "6.1 GA3";

	public static final String PORTAL_VERSION_6_2_10 = "6.2 EE";

	public static String extractQuarterlyRelease(String version) {
		if (Validator.isNotNull(version)) {
			Matcher matcher = getQuarterlyReleaseMatcher(version);

			if (matcher.find()) {
				return StringUtil.toUpperCase(matcher.group());
			}
		}

		return StringPool.BLANK;
	}

	public static Matcher getQuarterlyReleaseMatcher(String version) {
		return _quarterlyReleaseVersionPattern.matcher(version);
	}

	private static final Pattern _quarterlyReleaseVersionPattern =
		Pattern.compile("(\\d{4})\\.Q([1-4])", Pattern.CASE_INSENSITIVE);

}