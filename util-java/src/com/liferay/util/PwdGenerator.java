/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Random;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 * @author Shuyang Zhou
 */
public class PwdGenerator {

	public static final String KEY1 = "0123456789";

	public static final String KEY2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String KEY3 = "abcdefghijklmnopqrstuvwxyz";

	public static String getPassword() {
		return getPassword(8, KEYS);
	}

	public static String getPassword(int length) {
		return getPassword(length, KEYS);
	}

	public static String getPassword(int length, String... keys) {
		if (length < keys.length) {
			length = keys.length;
		}

		StringBuilder sb = new StringBuilder(length);

		// It is safe to use the regular Random class because each generated
		// password only consumes one secure random long

		Random random = new Random(SecureRandomUtil.nextLong());

		int fullKeyLength = 0;

		for (String key : keys) {
			fullKeyLength += key.length();
		}

		// Ensure every key contributes to the output by an even distribution

		for (String key : keys) {
			int count = key.length() * length / fullKeyLength;

			if (count == 0) {
				count = 1;
			}

			for (int i = 0; i < count; i++) {
				sb.append(key.charAt(random.nextInt(key.length())));
			}
		}

		// Round up the tail

		for (int i = sb.length(); i < length; i++) {
			String key = keys[random.nextInt(keys.length)];

			sb.append(key.charAt(random.nextInt(key.length())));
		}

		// Shuffle

		RandomUtil.shuffle(random, sb);

		return sb.toString();
	}

	public static String getPassword(String key, int length) {
		int keysCount = 0;

		if (key.contains(KEY1)) {
			keysCount++;
		}

		if (key.contains(KEY2)) {
			keysCount++;
		}

		if (key.contains(KEY3)) {
			keysCount++;
		}

		if (keysCount > length) {
			if (_log.isWarnEnabled()) {
				_log.warn("Length is too short");
			}

			length = keysCount;
		}

		while (true) {
			String password = getPassword(length, key);

			if (key.contains(KEY1)) {
				if (Validator.isNull(StringUtil.extractDigits(password))) {
					continue;
				}
			}

			if (key.contains(KEY2)) {
				if (password.equals(StringUtil.toLowerCase(password))) {
					continue;
				}
			}

			if (key.contains(KEY3)) {
				if (password.equals(StringUtil.toUpperCase(password))) {
					continue;
				}
			}

			return password;
		}
	}

	public static String getPassword(
		String key, int length, boolean useAllKeys) {

		if (useAllKeys) {
			return getPassword(key, length);
		}

		return getPassword(length, key);
	}

	public static String getPinNumber() {
		return getPassword(4, KEY1);
	}

	private static final String[] KEYS = {KEY1, KEY2, KEY3};

	private static Log _log = LogFactoryUtil.getLog(PwdGenerator.class);

}