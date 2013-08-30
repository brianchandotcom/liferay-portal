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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class InputSearchTag extends IncludeTag {

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setButtonLabel(String buttonLabel) {
		_buttonLabel = buttonLabel;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPlaceholder(String placeholder) {
		_placeholder = placeholder;
	}

	public void setShowButton(boolean showButton) {
		_showButton = showButton;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_autoFocus = false;
		_buttonLabel = null;
		_cssClass = null;
		_id = null;
		_name = null;
		_placeholder = null;
		_showButton = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String buttonLabel = _buttonLabel;

		if (Validator.isNull(buttonLabel)) {
			buttonLabel = LanguageUtil.get(pageContext, "search");
		}

		String cssClass = _cssClass;

		if (Validator.isNull(cssClass)) {
			cssClass = "input-append";
		}

		String name = _name;

		if (Validator.isNull(name)) {
			name = DisplayTerms.KEYWORDS;
		}

		String id = _id;

		if (Validator.isNull(id)) {
			id = name;
		}

		String placeholder = _placeholder;

		if (Validator.isNull(placeholder)) {
			placeholder = buttonLabel;
		}

		request.setAttribute("liferay-ui:input-search:autoFocus", _autoFocus);
		request.setAttribute(
			"liferay-ui:input-search:buttonLabel", buttonLabel);
		request.setAttribute("liferay-ui:input-search:cssClass", cssClass);
		request.setAttribute("liferay-ui:input-search:id", id);
		request.setAttribute("liferay-ui:input-search:name", name);
		request.setAttribute(
			"liferay-ui:input-search:placeholder", placeholder);
		request.setAttribute("liferay-ui:input-search:showButton", _showButton);
	}

	private static final String _PAGE = "/html/taglib/ui/input_search/page.jsp";

	private boolean _autoFocus = false;
	private String _buttonLabel;
	private String _cssClass;
	private String _id;
	private String _name;
	private String _placeholder;
	private boolean _showButton = true;

}