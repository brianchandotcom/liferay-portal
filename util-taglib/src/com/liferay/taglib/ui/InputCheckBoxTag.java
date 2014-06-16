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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class InputCheckBoxTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		updateFormCheckboxNames();

		return super.doEndTag();
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultValue(boolean defaultValue) {
		_defaultValue = Boolean.valueOf(defaultValue);
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setParam(String param) {
		_param = param;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_defaultValue = Boolean.FALSE;
		_disabled = false;
		_formName = "fm";
		_id = null;
		_onClick = null;
		_param = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:input-checkbox:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-checkbox:defaultValue", _defaultValue);
		request.setAttribute(
			"liferay-ui:input-checkbox:disabled", String.valueOf(_disabled));
		request.setAttribute("liferay-ui:input-checkbox:formName", _formName);
		request.setAttribute("liferay-ui:input-checkbox:id", _id);
		request.setAttribute("liferay-ui:input-checkbox:onClick", _onClick);
		request.setAttribute("liferay-ui:input-checkbox:param", _param);
	}

	protected void updateFormCheckboxNames() {
		List<String> checkboxNames = (List<String>)request.getAttribute(
			"aui:form:checkboxNames");

		if (checkboxNames != null) {
			checkboxNames.add(_param);
		}
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_checkbox/page.jsp";

	private String _cssClass;
	private Boolean _defaultValue = Boolean.FALSE;
	private boolean _disabled;
	private String _formName = "fm";
	private String _id;
	private String _onClick;
	private String _param;

}