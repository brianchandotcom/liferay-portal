/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 */
public class Normalizer {

	public static String normalizeToAscii(String s) {
		if (!hasNonASCIICode(s)) {
			return s;
		}

		String normalizedText = _transliterator.transform(s);

		return StringUtil.replace(
			normalizedText, _UNICODE_TEXT, _NORMALIZED_TEXT);
	}

	private static boolean hasNonASCIICode(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) > 127) {
				return true;
			}
		}

		return false;
	}

	private static final String _NORMALIZED_TEXT = "l";

	private static final String _UNICODE_TEXT = "\u0142";

	private static Transliterator _transliterator =
		Transliterator.getInstance("NFD; [:Nonspacing Mark:] Remove; NFC");

}