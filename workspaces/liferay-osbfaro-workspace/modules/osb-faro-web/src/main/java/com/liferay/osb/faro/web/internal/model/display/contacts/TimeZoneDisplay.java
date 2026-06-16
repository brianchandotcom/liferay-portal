/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.web.internal.util.TimeZoneUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;

/**
 * @author Geyson Silva
 */
public class TimeZoneDisplay {

	public TimeZoneDisplay(ZoneId zoneId, String country) {
		_country = country;

		LocalDateTime nowLocalDateTime = LocalDateTime.now();

		ZonedDateTime zonedDateTime = nowLocalDateTime.atZone(zoneId);

		ZoneOffset zoneOffset = zonedDateTime.getOffset();

		String zoneOffsetId = zoneOffset.getId();

		_displayTimeZone = String.format(
			"%s%s %s (%s)", TimeZoneUtil.UTC_TIME_ZONE_ID,
			StringUtil.removeChar(zoneOffsetId, CharPool.UPPER_CASE_Z),
			zoneId.getDisplayName(TextStyle.FULL, LocaleUtil.getDefault()),
			StringUtil.replace(
				zoneId.getId(), CharPool.UNDERLINE, CharPool.SPACE));

		_timeZoneId = zoneId.getId();
	}

	private final String _country;
	private final String _displayTimeZone;
	private final String _timeZoneId;

}