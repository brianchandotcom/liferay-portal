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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class HeaderTag extends IncludeTag {

	public void setBackLabel(String backLabel) {
		_backLabel = backLabel;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = escapeXml;
	}

	public void setLocalizeTitle(boolean localizeTitle) {
		_localizeTitle = localizeTitle;
	}

	public void setShowBackURL(boolean showBackURL) {
		_showBackURL = showBackURL;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backLabel = null;
		_backURL = null;
		_cssClass = null;
		_escapeXml = true;
		_localizeTitle = true;
		_showBackURL = true;
		_title = null;
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
		request.setAttribute("liferay-ui:header:backLabel", _backLabel);

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(request, "redirect"));

		if (Validator.isNull(_backURL) && Validator.isNotNull(redirect)) {
			request.setAttribute("liferay-ui:header:backURL", redirect);
		}
		else {
			request.setAttribute("liferay-ui:header:backURL", _backURL);
		}

		request.setAttribute("liferay-ui:header:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:header:escapeXml", String.valueOf(_escapeXml));
		request.setAttribute(
			"liferay-ui:header:localizeTitle", String.valueOf(_localizeTitle));
		request.setAttribute(
			"liferay-ui:header:showBackURL", String.valueOf(_showBackURL));
		request.setAttribute("liferay-ui:header:title", _title);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/header/page.jsp";

	private String _backLabel;
	private String _backURL;
	private String _cssClass;
	private boolean _escapeXml = true;
	private boolean _localizeTitle = true;
	private boolean _showBackURL = true;
	private String _title;

}