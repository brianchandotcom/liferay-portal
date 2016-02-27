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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EmptyResultMessageTag extends IncludeTag {

	public void setCompact(boolean compact) {
		_compact = compact;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setSearch(boolean search) {
		_search = search;
	}

	@Override
	protected void cleanUp() {
		_message = null;
		_search = false;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:empty-result-message:compact",
			String.valueOf(_compact));
		request.setAttribute(
			"liferay-ui:empty-result-message:message", _message);
		request.setAttribute(
			"liferay-ui:empty-result-message:search", String.valueOf(_search));
	}

	private static final String _END_PAGE =
		"/html/taglib/ui/empty_result_message/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/empty_result_message/start.jsp";

	private boolean _compact;
	private String _message;
	private boolean _search;

}