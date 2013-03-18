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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputTimeZoneTag extends IncludeTag {

	public InputTimeZoneTag() {
		TimeZone timeZone = TimeZoneUtil.getDefault();

		_value = timeZone.getID();
	}

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDaylight(boolean daylight) {
		_daylight = daylight;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNullable(boolean nullable) {
		_nullable = nullable;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	protected void cleanUp() {
		_autoFocus = false;
		_cssClass = null;
		_daylight = false;
		_disabled = false;
		_displayStyle = TimeZone.LONG;
		_name = null;
		_nullable = false;

		TimeZone timeZone = TimeZoneUtil.getDefault();

		_value = timeZone.getID();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:input-time-zone:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute("liferay-ui:input-time-zone:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-time-zone:daylight", String.valueOf(_daylight));
		request.setAttribute(
			"liferay-ui:input-time-zone:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"liferay-ui:input-time-zone:displayStyle",
			String.valueOf(_displayStyle));
		request.setAttribute("liferay-ui:input-time-zone:name", _name);
		request.setAttribute(
			"liferay-ui:input-time-zone:nullable", String.valueOf(_nullable));
		request.setAttribute("liferay-ui:input-time-zone:value", _value);
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_time_zone/page.jsp";

	private boolean _autoFocus;
	private String _cssClass;
	private boolean _daylight;
	private boolean _disabled;
	private int _displayStyle = TimeZone.LONG;
	private String _name;
	private boolean _nullable;
	private String _value;

}