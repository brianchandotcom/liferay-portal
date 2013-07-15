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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class FormNavigatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setCategoryNames(String[] categoryNames) {
		_categoryNames = categoryNames;
	}

	public void setCategorySections(String[][] categorySections) {
		_categorySections = categorySections;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHtmlBottom(String htmlBottom) {
		_htmlBottom = htmlBottom;
	}

	public void setHtmlTop(String htmlTop) {
		_htmlTop = htmlTop;
	}

	public void setJspPath(String jspPath) {
		_jspPath = jspPath;
	}

	public void setShowButtons(boolean showButtons) {
		_showButtons = showButtons;
	}

	@Override
	protected void cleanUp() {
		_backURL = null;
		_categoryNames = null;
		_categorySections = null;
		_displayStyle = "form";
		_formName = "fm";
		_htmlBottom = null;
		_htmlTop = null;
		_jspPath = null;
		_showButtons = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:form-navigator:backURL", _backURL);
		request.setAttribute(
			"liferay-ui:form-navigator:categoryNames", _categoryNames);
		request.setAttribute(
			"liferay-ui:form-navigator:categorySections", _categorySections);
		request.setAttribute("liferay-ui:form-navigator:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:form-navigator:formName", _formName);
		request.setAttribute(
			"liferay-ui:form-navigator:htmlBottom", _htmlBottom);
		request.setAttribute("liferay-ui:form-navigator:htmlTop", _htmlTop);
		request.setAttribute("liferay-ui:form-navigator:jspPath", _jspPath);
		request.setAttribute(
			"liferay-ui:form-navigator:showButtons",
			String.valueOf(_showButtons));
	}

	private static final String _PAGE =
		"/html/taglib/ui/form_navigator/page.jsp";

	private String _backURL;
	private String[] _categoryNames;
	private String[][] _categorySections;
	private String _displayStyle = "form";
	private String _formName = "fm";
	private String _htmlBottom;
	private String _htmlTop;
	private String _jspPath;
	private boolean _showButtons = true;

}