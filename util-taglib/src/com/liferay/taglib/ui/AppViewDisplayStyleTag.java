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

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Sergio González
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.taglib.aui.NavItemTag}
 */
@Deprecated
public class AppViewDisplayStyleTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyles(String[] displayStyles) {
		_displayStyles = displayStyles;
	}

	public void setDisplayStyleURL(PortletURL displayStyleURL) {
		_displayStyleURL = displayStyleURL;
	}

	public void setEventName(String eventName) {
		_eventName = eventName;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		_requestParams = requestParams;
	}

	@Override
	protected void cleanUp() {
		_displayStyle = null;
		_displayStyles = null;
		_displayStyleURL = null;
		_eventName = null;
		_requestParams = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:app-view-display-style:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:app-view-display-style:displayStyles", _displayStyles);
		request.setAttribute(
			"liferay-ui:app-view-display-style:displayStyleURL",
			_displayStyleURL);
		request.setAttribute(
			"liferay-ui:app-view-display-style:eventName", _eventName);
		request.setAttribute(
			"liferay-ui:app-view-display-style:requestParams", _requestParams);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/app_view_display_style/page.jsp";

	private String _displayStyle;
	private String[] _displayStyles;
	private PortletURL _displayStyleURL;
	private String _eventName;
	private Map<String, String> _requestParams;

}