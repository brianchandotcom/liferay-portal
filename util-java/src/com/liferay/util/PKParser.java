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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class PKParser {

	public PKParser(String pk) {
		if (pk.startsWith(StringPool.OPEN_CURLY_BRACE)) {
			pk = pk.substring(1);
		}

		if (pk.endsWith(StringPool.CLOSE_CURLY_BRACE)) {
			pk = pk.substring(0, pk.length() - 1);
		}

		String[] array = StringUtil.split(pk);

		for (int i = 0; i < array.length; i++) {
			String[] kvp = StringUtil.split(array[i], CharPool.EQUAL);

			String key = kvp[0].trim();
			String value = kvp[1].trim();

			_fields.put(key, value);
		}
	}

	public boolean getBoolean(String key) {
		return GetterUtil.getBoolean(getString(key));
	}

	public double getDouble(String key) {
		return GetterUtil.getDouble(getString(key));
	}

	public int getInteger(String key) {
		return GetterUtil.getInteger(getString(key));
	}

	public long getLong(String key) {
		return GetterUtil.getLong(getString(key));
	}

	public short getShort(String key) {
		return GetterUtil.getShort(getString(key));
	}

	public String getString(String key) {
		String value = _fields.get(key);

		if (value == null) {
			return StringPool.BLANK;
		}
		else {
			return value;
		}
	}

	private final Map<String, String> _fields = new HashMap<>();

}