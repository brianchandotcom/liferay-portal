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

import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputDateTag extends IncludeTag {

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDayNullable(boolean dayNullable) {
		_dayNullable = dayNullable;
	}

	public void setDayParam(String dayParam) {
		_dayParam = dayParam;
	}

	public void setDayValue(int dayValue) {
		_dayValue = dayValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDisableNamespace(boolean disableNamespace) {
		_disableNamespace = disableNamespace;
	}

	public void setFirstDayOfWeek(int firstDayOfWeek) {
		_firstDayOfWeek = firstDayOfWeek;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setImageInputId(String imageInputId) {
		_imageInputId = imageInputId;
	}

	public void setMonthAndYearNullable(boolean monthAndYearNullable) {
		_monthAndYearNullable = monthAndYearNullable;
	}

	public void setMonthAndYearParam(String monthAndYearParam) {
		_monthAndYearParam = monthAndYearParam;
	}

	public void setMonthNullable(boolean monthNullable) {
		_monthNullable = monthNullable;
	}

	public void setMonthParam(String monthParam) {
		_monthParam = monthParam;
	}

	public void setMonthValue(int monthValue) {
		_monthValue = monthValue;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setYearNullable(boolean yearNullable) {
		_yearNullable = yearNullable;
	}

	public void setYearParam(String yearParam) {
		_yearParam = yearParam;
	}

	public void setYearRangeEnd(int yearRangeEnd) {
		_yearRangeEnd = yearRangeEnd;
	}

	public void setYearRangeStart(int yearRangeStart) {
		_yearRangeStart = yearRangeStart;
	}

	public void setYearValue(int yearValue) {
		_yearValue = yearValue;
	}

	@Override
	protected void cleanUp() {
		_autoFocus = false;
		_cssClass = null;
		_dayNullable = false;
		_dayParam = null;
		_dayValue = 0;
		_disabled = false;
		_disableNamespace = false;
		_firstDayOfWeek = Calendar.SUNDAY - 1;
		_formName = "fm";
		_imageInputId = null;
		_monthAndYearNullable = false;
		_monthAndYearParam = StringPool.BLANK;
		_monthNullable = false;
		_monthParam = null;
		_monthValue = -1;
		_yearNullable = false;
		_yearParam = null;
		_yearRangeEnd = 0;
		_yearRangeStart = 0;
		_yearValue = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:input-date:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute("liferay-ui:input-date:cssClass",_cssClass);
		request.setAttribute(
			"liferay-ui:input-date:dayNullable", String.valueOf(_dayNullable));
		request.setAttribute("liferay-ui:input-date:dayParam", _dayParam);
		request.setAttribute(
			"liferay-ui:input-date:dayParamId",
			FriendlyURLNormalizerUtil.normalize(_dayParam));
		request.setAttribute(
			"liferay-ui:input-date:dayValue", String.valueOf(_dayValue));
		request.setAttribute(
			"liferay-ui:input-date:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"liferay-ui:input-date:disableNamespace",
			String.valueOf(_disableNamespace));
		request.setAttribute(
			"liferay-ui:input-date:firstDayOfWeek",
			String.valueOf(_firstDayOfWeek));
		request.setAttribute("liferay-ui:input-date:formName", _formName);
		request.setAttribute(
			"liferay-ui:input-date:imageInputId", _imageInputId);
		request.setAttribute(
			"liferay-ui:input-date:monthAndYearNullable",
			String.valueOf(_monthAndYearNullable));
		request.setAttribute(
			"liferay-ui:input-date:monthAndYearParam", _monthAndYearParam);
		request.setAttribute(
			"liferay-ui:input-date:monthNullable",
			String.valueOf(_monthNullable));
		request.setAttribute("liferay-ui:input-date:monthParam", _monthParam);
		request.setAttribute(
			"liferay-ui:input-date:monthParamId",
			FriendlyURLNormalizerUtil.normalize(_monthParam));
		request.setAttribute(
			"liferay-ui:input-date:monthValue", String.valueOf(_monthValue));
		request.setAttribute("liferay-ui:input-date:name", _name);
		request.setAttribute(
			"liferay-ui:input-date:yearNullable",
			String.valueOf(_yearNullable));
		request.setAttribute("liferay-ui:input-date:yearParam", _yearParam);
		request.setAttribute(
			"liferay-ui:input-date:yearParamId",
			FriendlyURLNormalizerUtil.normalize(_yearParam));
		request.setAttribute(
			"liferay-ui:input-date:yearRangeEnd",
			String.valueOf(_yearRangeEnd));
		request.setAttribute(
			"liferay-ui:input-date:yearRangeStart",
			String.valueOf(_yearRangeStart));
		request.setAttribute(
			"liferay-ui:input-date:yearValue", String.valueOf(_yearValue));
	}

	private static final String _PAGE = "/html/taglib/ui/input_date/page.jsp";

	private boolean _autoFocus;
	private String _cssClass;
	private boolean _dayNullable;
	private String _dayParam;
	private int _dayValue;
	private boolean _disabled;
	private boolean _disableNamespace;
	private int _firstDayOfWeek = Calendar.SUNDAY - 1;
	private String _formName = "fm";
	private String _imageInputId;
	private boolean _monthAndYearNullable;
	private String _monthAndYearParam = StringPool.BLANK;
	private boolean _monthNullable;
	private String _monthParam;
	private int _monthValue = -1;
	private String _name;
	private boolean _yearNullable;
	private String _yearParam;
	private int _yearRangeEnd;
	private int _yearRangeStart;
	private int _yearValue;

}