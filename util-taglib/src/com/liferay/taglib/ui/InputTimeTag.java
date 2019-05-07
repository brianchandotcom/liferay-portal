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

import com.liferay.taglib.util.IncludeTag;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputTimeTag extends IncludeTag {

	public String getAmPmParam() {
		return _amPmParam;
	}

	public int getAmPmValue() {
		return _amPmValue;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getDateParam() {
		return _dateParam;
	}

	public Date getDateValue() {
		return _dateValue;
	}

	public String getHourParam() {
		return _hourParam;
	}

	public int getHourValue() {
		return _hourValue;
	}

	public int getMinuteInterval() {
		return _minuteInterval;
	}

	public String getMinuteParam() {
		return _minuteParam;
	}

	public int getMinuteValue() {
		return _minuteValue;
	}

	public String getName() {
		return _name;
	}

	public String getTimeFormat() {
		return _timeFormat;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public void setAmPmParam(String amPmParam) {
		_amPmParam = amPmParam;
	}

	public void setAmPmValue(int amPmValue) {
		_amPmValue = amPmValue;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDateParam(String dateParam) {
		_dateParam = dateParam;
	}

	public void setDateValue(Date dateValue) {
		_dateValue = dateValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHourParam(String hourParam) {
		_hourParam = hourParam;
	}

	public void setHourValue(int hourValue) {
		_hourValue = hourValue;
	}

	public void setMinuteInterval(int minuteInterval) {
		_minuteInterval = minuteInterval;
	}

	public void setMinuteParam(String minuteParam) {
		_minuteParam = minuteParam;
	}

	public void setMinuteValue(int minuteValue) {
		_minuteValue = minuteValue;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTimeFormat(String timeFormat) {
		_timeFormat = timeFormat;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_amPmParam = null;
		_amPmValue = 0;
		_cssClass = null;
		_dateParam = null;
		_dateValue = null;
		_disabled = false;
		_hourParam = null;
		_hourValue = 0;
		_minuteInterval = 0;
		_minuteParam = null;
		_minuteValue = 0;
		_name = null;
		_timeFormat = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:amPmParam", _amPmParam);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:amPmValue", String.valueOf(_amPmValue));
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:cssClass", _cssClass);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:dateParam", _dateParam);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:dateValue", _dateValue);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:disabled", String.valueOf(_disabled));
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:hourParam", _hourParam);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:hourValue", String.valueOf(_hourValue));
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:minuteInterval",
			String.valueOf(_minuteInterval));
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:minuteParam", _minuteParam);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:minuteValue", String.valueOf(_minuteValue));
		httpServletRequest.setAttribute("liferay-ui:input-time:name", _name);
		httpServletRequest.setAttribute(
			"liferay-ui:input-time:timeFormat", String.valueOf(_timeFormat));
	}

	private static final String _PAGE = "/html/taglib/ui/input_time/page.jsp";

	private String _amPmParam;
	private int _amPmValue;
	private String _cssClass;
	private String _dateParam;
	private Date _dateValue;
	private boolean _disabled;
	private String _hourParam;
	private int _hourValue;
	private int _minuteInterval;
	private String _minuteParam;
	private int _minuteValue;
	private String _name;
	private String _timeFormat;

}