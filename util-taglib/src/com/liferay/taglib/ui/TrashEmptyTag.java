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

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class TrashEmptyTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setConfirmMessage(String confirmMessage) {
		_confirmMessage = confirmMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		_emptyMessage = emptyMessage;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL.toString();
	}

	public void setPortletURL(String portletURL) {
		_portletURL = portletURL;
	}

	public void setTotalEntries(int totalEntries) {
		_totalEntries = totalEntries;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_portletURL = null;
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
			"liferay-ui:trash-empty:confirmMessage", _confirmMessage);
		request.setAttribute(
			"liferay-ui:trash-empty:emptyMessage", _emptyMessage);
		request.setAttribute("liferay-ui:trash-empty:portletURL", _portletURL);
		request.setAttribute(
			"liferay-ui:trash-empty:totalEntries", _totalEntries);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/trash_empty/page.jsp";

	private String _confirmMessage =
		"are-you-sure-you-want-to-empty-the-recycle-bin";
	private String _emptyMessage = "empty-the-recycle-bin";
	private String _portletURL;
	private int _totalEntries;

}