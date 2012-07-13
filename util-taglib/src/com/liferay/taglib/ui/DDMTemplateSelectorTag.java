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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateSelectorTag extends IncludeTag {

	public void setClassNameId(String classNameId) {
		_classNameId = classNameId;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setRefreshURL(String refreshURL) {
		_refreshURL = refreshURL;
	}

	@Override
	protected void cleanUp() {
		_classNameId = null;
		_icon = null;
		_message = null;
		_refreshURL = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:ddm-template-selector:classNameId", _classNameId);
		request.setAttribute("liferay-ui:ddm-template-selector:icon", _icon);
		request.setAttribute(
			"liferay-ui:ddm-template-selector:message", _message);
		request.setAttribute(
			"liferay-ui:ddm-template-selector:refreshURL", _refreshURL);
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm-template-selector/page.jsp";

	private String _classNameId;
	private String _icon;
	private String _message;
	private String _refreshURL;

}