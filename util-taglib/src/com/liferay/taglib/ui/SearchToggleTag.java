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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchToggleTag extends IncludeTag {

	public String getButtonLabel() {
		return _buttonLabel;
	}

	public DisplayTerms getDisplayTerms() {
		return _displayTerms;
	}

	public String getId() {
		return _id;
	}

	public String getMarkupView() {
		return null;
	}

	public boolean isAutoFocus() {
		return _autoFocus;
	}

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

	public void setMarkupView(String markupView) {
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_autoFocus = false;
		_buttonLabel = null;
		_displayTerms = null;
		_id = null;
	}

	@Override
	protected String getEndPage() {
		return "/html/taglib/ui/search_toggle/end.jsp";
	}

	@Override
	protected String getStartPage() {
		return "/html/taglib/ui/search_toggle/start.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:search-toggle:autoFocus", String.valueOf(_autoFocus));
		httpServletRequest.setAttribute(
			"liferay-ui:search-toggle:buttonLabel", _buttonLabel);
		httpServletRequest.setAttribute(
			"liferay-ui:search-toggle:displayTerms", _displayTerms);
		httpServletRequest.setAttribute("liferay-ui:search-toggle:id", _id);
	}

	private boolean _autoFocus;
	private String _buttonLabel;
	private DisplayTerms _displayTerms;
	private String _id;

}