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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateSelectorTag extends IncludeTag {

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setDisplayStyles(List<String> displayStyles) {
		_displayStyles = displayStyles;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setRefreshURL(String refreshURL) {
		_refreshURL = refreshURL;
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	@Override
	protected void cleanUp() {
		_classNameId = 0;
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_displayStyles = null;
		_icon = null;
		_label = "display-template";
		_refreshURL = null;
		_showEmptyOption = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:ddm-template-select:classNameId",
			String.valueOf(_classNameId));
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyleGroupId",
			String.valueOf(_displayStyleGroupId));
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyles", _displayStyles);
		request.setAttribute("liferay-ui:ddm-template-select:icon", _icon);
		request.setAttribute("liferay-ui:ddm-template-select:label", _label);
		request.setAttribute(
			"liferay-ui:ddm-template-select:refreshURL", _refreshURL);
		request.setAttribute(
			"liferay-ui:ddm-template-select:showEmptyOption",
			String.valueOf(_showEmptyOption));
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm_template_selector/page.jsp";

	private long _classNameId;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<String> _displayStyles;
	private String _icon;
	private String _label = "display-template";
	private String _refreshURL;
	private boolean _showEmptyOption;

}