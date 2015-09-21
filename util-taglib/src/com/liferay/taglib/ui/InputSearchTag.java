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

	public void setMarkupView(String markupView) {
		_markupView = markupView;
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

	public void setTitle(String title) {
		_title = title;
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_autoFocus = false;
		_buttonLabel = null;
		_cssClass = null;
		_id = null;
		_markupView = null;
		_name = null;
		_placeholder = null;
		_showButton = true;
		_title = null;
		_useNamespace = true;
	}

	@Override
	protected String getPage() {
		if (Validator.isNotNull(_markupView)) {
			return "/html/taglib/ui/input_search/" + _markupView +"/page.jsp";
		}

		return "/html/taglib/ui/input_search/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String buttonLabel = _buttonLabel;

		if (Validator.isNull(buttonLabel)) {
			buttonLabel = LanguageUtil.get(request, "search");
		}

		String cssClass = _cssClass;

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

		String title = _title;

		if (title == null) {
			title = LanguageUtil.get(request, "search");
		}

		request.setAttribute(
			"liferay-ui:input-search:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute(
			"liferay-ui:input-search:buttonLabel", buttonLabel);
		request.setAttribute("liferay-ui:input-search:cssClass", cssClass);
		request.setAttribute("liferay-ui:input-search:id", id);
		request.setAttribute("liferay-ui:input-search:name", name);
		request.setAttribute(
			"liferay-ui:input-search:placeholder", placeholder);
		request.setAttribute("liferay-ui:input-search:showButton", _showButton);
		request.setAttribute("liferay-ui:input-search:title", title);
		request.setAttribute(
			"liferay-ui:input-search:useNamespace", _useNamespace);
	}

	private boolean _autoFocus;
	private String _buttonLabel;
	private String _cssClass;
	private String _id;
	private String _markupView;
	private String _name;
	private String _placeholder;
	private boolean _showButton = true;
	private String _title;
	private boolean _useNamespace = true;

}