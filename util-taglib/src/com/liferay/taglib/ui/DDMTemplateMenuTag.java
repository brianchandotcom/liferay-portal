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

import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateMenuTag extends IncludeTag {

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setDefaultOptions(List<String> defaultOptions) {
		_defaultOptions = defaultOptions;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setPreferenceName(String preferenceName) {
		_preferenceName = preferenceName;
	}

	public void setPreferenceValue(String preferenceValue) {
		_preferenceValue = preferenceValue;
	}

	public void setShowDefaultOption(boolean showDefaultOption) {
		_showDefaultOption = showDefaultOption;
	}

	@Override
	protected void cleanUp() {
		_classNameId = 0;
		_defaultOptions = null;
		_label = "display-template";
		_preferenceName = "displayStyle";
		_showDefaultOption = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:ddm-template-menu:classNameId",
			String.valueOf(_classNameId));
		request.setAttribute("liferay-ui:ddm-template-menu:defaultOptions",
			_defaultOptions);
		request.setAttribute("liferay-ui:ddm-template-menu:label", _label);
		request.setAttribute(
			"liferay-ui:ddm-template-menu:preferenceName", _preferenceName);
		request.setAttribute(
			"liferay-ui:ddm-template-menu:preferenceValue", _preferenceValue);
		request.setAttribute(
			"liferay-ui:ddm-template-menu:showDefaultOption",
			_showDefaultOption);
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm-template-menu/page.jsp";

	private long _classNameId;
	private List<String> _defaultOptions;
	private String _label = "display-template";
	private String _preferenceName = "displayStyle";
	private String _preferenceValue;
	private boolean _showDefaultOption;

}