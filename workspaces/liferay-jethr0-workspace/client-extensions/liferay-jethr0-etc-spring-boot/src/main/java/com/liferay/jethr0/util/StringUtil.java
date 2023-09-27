/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.time.Instant;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public class StringUtil {

	public static String combine(Object... objects) {
		StringBuilder sb = new StringBuilder();

		for (Object object : objects) {
			sb.append(String.valueOf(object));
		}

		return sb.toString();
	}

	public static boolean equals(URL url1, URL url2) {
		if (Objects.equals(fixURL(url1), fixURL(url2))) {
			return true;
		}

		return false;
	}

	public static URL fixURL(URL url) {
		String urlString = String.valueOf(url);

		while (urlString.endsWith("/")) {
			urlString = urlString.substring(0, urlString.length() - 1);
		}

		return toURL(urlString);
	}

	public static boolean isNullOrEmpty(String string) {
		if (string == null) {
			return true;
		}

		String trimmedString = string.trim();

		if (trimmedString.isEmpty()) {
			return true;
		}

		return false;
	}

	public static String join(String delimiter, Collection<String> strings) {
		StringBuilder sb = new StringBuilder();

		for (Object string : strings) {
			if (sb.length() > 0) {
				sb.append(delimiter);
			}

			sb.append(string);
		}

		return sb.toString();
	}

	public static Date toDate(String dateString) {
		if ((dateString == null) || dateString.isEmpty()) {
			return null;
		}

		Instant instant = Instant.parse(dateString);

		return new Date(instant.toEpochMilli());
	}

	public static String toLowerCase(String s) {
		return toLowerCase(s, LocaleUtil.getDefault());
	}

	public static String toLowerCase(String s, Locale locale) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = null;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c > 127) {

				// Found non-ascii char, fallback to the slow unicode detection

				if (locale == null) {
					locale = LocaleUtil.getDefault();
				}

				return s.toLowerCase(locale);
			}

			if ((c >= 'A') && (c <= 'Z')) {
				if (sb == null) {
					sb = new StringBuilder(s);
				}

				sb.setCharAt(i, (char)(c + 32));
			}
		}

		if (sb == null) {
			return s;
		}

		return sb.toString();
	}

	public static String toString(Date date) {
		if (date == null) {
			return null;
		}

		return _simpleDateFormat.format(date);
	}

	public static URL toURL(String urlString) {
		try {
			return new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static final SimpleDateFormat _simpleDateFormat =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

}