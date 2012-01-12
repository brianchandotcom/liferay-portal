/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class InputLocalizedTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setXml(String xml) {
		_xml = xml;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_disabled = false;
		_formName = null;
		_ignoreRequestValue = false;
		_languageId = null;
		_name = null;
		_type = "input";
		_xml = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String formName = _formName;

		if (Validator.isNull(formName)) {
			formName = "fm";
		}

		request.setAttribute("liferay-ui:input-localized:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-localized:defaultLanguageId", _defaultLanguageId);
		request.setAttribute(
			"liferay-ui:input-localized:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"liferay-ui:input-localized:dynamicAttributes",
			getDynamicAttributes());
		request.setAttribute("liferay-ui:input-localized:formName", formName);
		request.setAttribute(
			"liferay-ui:input-localized:ignoreRequestValue",
			String.valueOf(_ignoreRequestValue));
		request.setAttribute(
			"liferay-ui:input-localized:languageId", _languageId);
		request.setAttribute("liferay-ui:input-localized:name", _name);
		request.setAttribute("liferay-ui:input-localized:type", _type);
		request.setAttribute("liferay-ui:input-localized:xml", _xml);
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_localized/page.jsp";

	private String _cssClass;
	private String _defaultLanguageId;
	private boolean _disabled;
	private String _formName;
	private boolean _ignoreRequestValue;
	private String _languageId;
	private String _name;
	private String _type = "input";
	private String _xml;

}