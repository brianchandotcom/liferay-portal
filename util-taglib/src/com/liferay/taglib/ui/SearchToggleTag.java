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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchToggleTag extends IncludeTag {

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setButtonLabel(String buttonLabel) {
		_buttonLabel = buttonLabel;
	}

	public void setDisplayTerms(DisplayTerms displayTerms) {
		_displayTerms = displayTerms;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setWidth(String width) {
		_width = width;
	}

	@Override
	protected void cleanUp() {
		_autoFocus = false;
		_buttonLabel = null;
		_displayTerms = null;
		_id = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:search-toggle:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute(
			"liferay-ui:search-toggle:buttonLabel", _buttonLabel);
		request.setAttribute(
			"liferay-ui:search-toggle:displayTerms", _displayTerms);
		request.setAttribute("liferay-ui:search-toggle:id", _id);
		request.setAttribute("liferay-ui:search-toggle:width", _width);
	}

	private static final String _END_PAGE =
		"/html/taglib/ui/search_toggle/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/search_toggle/start.jsp";

	private boolean _autoFocus;
	private String _buttonLabel;
	private DisplayTerms _displayTerms;
	private String _id;
	private String _width;

}