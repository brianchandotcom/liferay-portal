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

import java.text.Format;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class CalendarTag extends IncludeTag {

	public void setData(Set<Integer> data) {
		_data = data;
	}

	public void setDay(int day) {
		_day = day;
	}

	public void setHeaderFormat(Format headerFormat) {
		_headerFormat = headerFormat;
	}

	public void setHeaderPattern(String headerPattern) {
		_headerPattern = headerPattern;
	}

	public void setMonth(int month) {
		_month = month;
	}

	public void setShowAllPotentialWeeks(boolean showAllPotentialWeeks) {
		_showAllPotentialWeeks = showAllPotentialWeeks;
	}

	public void setYear(int year) {
		_year = year;
	}

	@Override
	protected void cleanUp() {
		_data = null;
		_day = 0;
		_headerFormat = null;
		_headerPattern = null;
		_month = 0;
		_showAllPotentialWeeks = false;
		_year = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:calendar:data", _data);
		request.setAttribute("liferay-ui:calendar:day", String.valueOf(_day));
		request.setAttribute("liferay-ui:calendar:headerFormat", _headerFormat);
		request.setAttribute(
			"liferay-ui:calendar:headerPattern", _headerPattern);
		request.setAttribute(
			"liferay-ui:calendar:month", String.valueOf(_month));
		request.setAttribute(
			"liferay-ui:calendar:showAllPotentialWeeks",
			String.valueOf(_showAllPotentialWeeks));
		request.setAttribute("liferay-ui:calendar:year", String.valueOf(_year));
	}

	private static final String _PAGE = "/html/taglib/ui/calendar/page.jsp";

	private Set<Integer> _data;
	private int _day;
	private Format _headerFormat;
	private String _headerPattern;
	private int _month;
	private boolean _showAllPotentialWeeks;
	private int _year;

}