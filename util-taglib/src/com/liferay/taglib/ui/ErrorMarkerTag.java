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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ErrorMarkerTag extends IncludeTag {

	public String getKey() {
		return _key;
	}

	public String getValue() {
		return _value;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_key = null;
		_value = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(_key) && Validator.isNotNull(_value)) {
			httpServletRequest.setAttribute(
				"liferay-ui:error-marker:key", _key);
			httpServletRequest.setAttribute(
				"liferay-ui:error-marker:value", _value);
		}
		else {
			httpServletRequest.removeAttribute("liferay-ui:error-marker:key");
			httpServletRequest.removeAttribute("liferay-ui:error-marker:value");
		}
	}

	private String _key;
	private String _value;

}