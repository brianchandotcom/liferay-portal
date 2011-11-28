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

package com.liferay.taglib.aui;

import com.liferay.alloy.util.ReservedAttributeUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.aui.base.BaseComponentTag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
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

		List<String> excludeAttributesList = Arrays.asList(
			StringUtil.split(excludeAttributes));

		if (key.equals("dynamicAttributes") ||
			excludeAttributesList.contains(key)) {

			return false;
		}

		return true;
	}

	protected void proccessAttributes(
		Map<String, Object> options, Map<String, Object> newOptions) {

		Map<String, String> afterEventOptions = new HashMap<String, String>();

		Map<String, String> onEventOptions = new HashMap<String, String>();

		for (String key : options.keySet()) {
			if (!isValidAttribute(key)) {
				continue;
			}

			Object optionValue = options.get(key);

			String originalKey = ReservedAttributeUtil.getOriginalName(
				getName(), key);

			if (optionValue instanceof Map) {
				Map<String, Object> childOptions =
					new HashMap<String, Object>();

				proccessAttributes((Map)optionValue, childOptions);

				newOptions.put(originalKey, childOptions);

				continue;
			}

			if (isEventAttribute(key)) {
				processEventAttribute(
					key, String.valueOf(optionValue), afterEventOptions,
					onEventOptions);
			} else {
				newOptions.put(originalKey, optionValue);
			}
		}

		if (afterEventOptions.size() > 0) {
			newOptions.put("after", afterEventOptions);
		}

		if (onEventOptions.size() > 0) {
			newOptions.put("on", onEventOptions);
		}
	}

	protected void processEventAttribute(
			String key, String value, Map<String, String> afterEventOptionsMap,
			Map<String, String> onEventsOptionsMap) {

		if (key.startsWith("after")) {
			String eventName = StringUtils.uncapitalize(
				StringUtil.remove(key, "after", StringPool.BLANK));

			afterEventOptionsMap.put(eventName, value);
		} else {
			String eventName = StringUtils.uncapitalize(
				StringUtil.remove(key, "on", StringPool.BLANK));

			eventName = StringUtils.uncapitalize(eventName);

			onEventsOptionsMap.put(eventName, value);
		}
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		Map<String, Object> options = getOptions();

		HashMap<String, Object> optionsJSON = new HashMap<String, Object>();

		proccessAttributes(options, optionsJSON);

		super.setAttributes(request);

		setNamespacedAttribute(request, "options", options);
		setNamespacedAttribute(request, "optionsJSON", optionsJSON);
	}

}