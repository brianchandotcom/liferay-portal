/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util;

import com.liferay.ibm.icu.text.Transliterator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @see    com.liferay.rss.util.Normalizer
 */
public class Normalizer {

	public static String normalizeToAscii(String s) {
		if (!_hasNonasciiCode(s)) {
			return s;
		}

		String normalizedText = TransliteratorHolder.transform(s);

		return StringUtil.replace(
			normalizedText, _UNICODE_TEXT, _NORMALIZED_TEXT);
	}

	private static boolean _hasNonasciiCode(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) > 127) {
				return true;
			}
		}

		return false;
	}

	private static final char[] _NORMALIZED_TEXT = {'l', '\'', '\"'};

	private static final char[] _UNICODE_TEXT = {'\u0142', '\u02B9', '\u02BA'};

	private static class TransliteratorHolder {

		public static String transform(String s) {
			return _transliterator.transform(s);
		}

		private static final Transliterator _transliterator =
			Transliterator.getInstance(
				"Greek-Latin; Cyrillic-Latin; NFD; [:Nonspacing Mark:] " +
					"Remove; NFC");

	}

}