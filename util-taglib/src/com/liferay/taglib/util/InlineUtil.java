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

package com.liferay.taglib.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class InlineUtil {

	public static String buildDynamicAttributes(
		Map<String, Object> dynamicAttributes) {

		if ((dynamicAttributes == null) || dynamicAttributes.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(dynamicAttributes.size() * 4);

		for (Map.Entry<String, Object> entry : dynamicAttributes.entrySet()) {
			String key = entry.getKey();

			if (!key.equals("class")) {
				String value = String.valueOf(entry.getValue());

				if (_attributesSet.contains(key)) {
					Boolean attributeValue = Boolean.valueOf(value);

					if (!attributeValue) {
						continue;
					}

					value = key;
				}

				sb.append(key);
				sb.append("=\"");
				sb.append(value);
				sb.append("\" ");
			}
		}

		return sb.toString();
	}

	private static final String[] _HTML_BOOLEAN_ATTRIBUTES = {
		"allowfullscreen", "allowpaymentrequest", "async", "autofocus",
		"autoplay", "checked", "controls", "default", "disabled",
		"formnovalidate", "hidden", "ismap", "itemscope", "loop", "multiple",
		"muted", "nomodule", "novalidate", "open", "playsinline", "readonly",
		"required", "reversed", "selected", "truespeed"
	};

	private static final Set<String> _attributesSet = new HashSet<>(
		Arrays.asList(_HTML_BOOLEAN_ATTRIBUTES));

}