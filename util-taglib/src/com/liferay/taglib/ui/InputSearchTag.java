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
 * @author Roberto Díaz
 */
public class InputSearchTag extends IncludeTag {

	public void setButtonLabel(String buttonLabel) {
		_buttonLabel = buttonLabel;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisplayTerms(DisplayTerms displayTerms) {
		_displayTerms = displayTerms;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setShowButton(boolean showButton) {
		_showButton = showButton;
	}

	@Override
	protected void cleanUp() {
		_showButton = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:input-search:buttonLabel", _buttonLabel);
		request.setAttribute("liferay-ui:input-search:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-search:displayTerms", _displayTerms);
		request.setAttribute("liferay-ui:input-search:id", _id);
		request.setAttribute("liferay-ui:input-search:name", _name);
		request.setAttribute("liferay-ui:input-search:showButton", _showButton);
	}

	private static final String _PAGE = "/html/taglib/ui/input_search/page.jsp";

	private String _buttonLabel;
	private String _cssClass;
	private DisplayTerms _displayTerms;
	private String _id;
	private String _name;
	private boolean _showButton = true;

}