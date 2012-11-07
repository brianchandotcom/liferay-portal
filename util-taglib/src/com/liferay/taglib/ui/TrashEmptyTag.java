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

	public void setInfoMessage(String infoMessage) {
		_infoMessage = infoMessage;
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

		_confirmMessage = _CONFIRM_MESSAGE;
		_emptyMessage = _EMPTY_MESSAGE;
		_infoMessage = _INFO_MESSAGE;
		_portletURL = null;
		_totalEntries = 0;
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
		request.setAttribute(
			"liferay-ui:trash-empty:infoMessage", _infoMessage);
		request.setAttribute("liferay-ui:trash-empty:portletURL", _portletURL);
		request.setAttribute(
			"liferay-ui:trash-empty:totalEntries", _totalEntries);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _CONFIRM_MESSAGE =
		"are-you-sure-you-want-to-empty-the-recycle-bin";

	private static final String _EMPTY_MESSAGE = "empty-the-recycle-bin";

	private static final String _INFO_MESSAGE =
		"entries-that-have-been-in-recycle-bin-for-more-than-x-days-will-be-" +
			"automatically-deleted";

	private static final String _PAGE = "/html/taglib/ui/trash_empty/page.jsp";

	private String _confirmMessage = _CONFIRM_MESSAGE;
	private String _emptyMessage = _EMPTY_MESSAGE;
	private String _infoMessage = _INFO_MESSAGE;
	private String _portletURL;
	private int _totalEntries;

}