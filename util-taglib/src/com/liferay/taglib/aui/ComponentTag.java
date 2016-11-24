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

package com.liferay.taglib.aui;

import com.liferay.alloy.util.ReservedAttributeUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.aui.base.BaseComponentTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author     Eduardo Lundgren
 * @author     Bruno Basto
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class ComponentTag extends BaseComponentTag {

	protected boolean isEventAttribute(String key) {
		if (StringUtil.startsWith(key, "after") ||
			StringUtil.startsWith(key, "on")) {

			return true;
		}

		return false;
	}

	protected boolean isValidAttribute(String key) {
		String excludeAttributes = getExcludeAttributes();

		if (excludeAttributes == null) {
			return true;
		}

		Set<String> excludeAttributesSet = SetUtil.fromArray(
			StringUtil.split(excludeAttributes));

		if (key.equals("dynamicAttributes") ||
			excludeAttributesSet.contains(key)) {

			return false;
		}

		return true;
	}

	protected void proccessAttributes(
		Map<String, Object> options, Map<String, Object> jsonifiedOptions) {

		Map<String, String> afterEventOptions = new HashMap<>();
		Map<String, String> onEventOptions = new HashMap<>();

		for (Map.Entry<String, Object> entry : options.entrySet()) {
			String key = entry.getKey();

			if (!isValidAttribute(key)) {
				continue;
			}

			Object value = entry.getValue();

			String originalKey = ReservedAttributeUtil.getOriginalName(
				getName(), key);

			if (value instanceof Map) {
				Map<String, Object> childOptions = new HashMap<>();

				proccessAttributes((Map<String, Object>)value, childOptions);

				jsonifiedOptions.put(originalKey, childOptions);

				continue;
			}

			if (isEventAttribute(key)) {
				processEventAttribute(
					key, String.valueOf(value), afterEventOptions,
					onEventOptions);
			}
			else {
				jsonifiedOptions.put(originalKey, value);
			}
		}

		if (!afterEventOptions.isEmpty()) {
			jsonifiedOptions.put("after", afterEventOptions);
		}

		if (!onEventOptions.isEmpty()) {
			jsonifiedOptions.put("on", onEventOptions);
		}
	}

	protected void processEventAttribute(
		String key, String value, Map<String, String> afterEventOptions,
		Map<String, String> onEventsOptions) {

		if (key.startsWith("after")) {
			String eventName = StringUtils.uncapitalize(key.substring(5));

			afterEventOptions.put(eventName, value);
		}
		else {
			String eventName = StringUtils.uncapitalize(key.substring(2));

			onEventsOptions.put(eventName, value);
		}
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		Map<String, Object> options = getOptions();

		Map<String, Object> jsonifiedOptions = new HashMap<>();

		proccessAttributes(options, jsonifiedOptions);

		super.setAttributes(request);

		setNamespacedAttribute(request, "jsonifiedOptions", jsonifiedOptions);
		setNamespacedAttribute(request, "options", options);
	}

}