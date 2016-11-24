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

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.taglib.util.IncludeTag;

import java.util.LinkedHashMap;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class UserSearchTag extends IncludeTag {

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setUserParams(LinkedHashMap<String, Object> userParams) {
		_userParams = userParams;
	}

	@Override
	protected void cleanUp() {
		_portletURL = null;
		_rowChecker = null;
		_userParams = null;
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
		request.setAttribute("liferay-ui:user-search:portletURL", _portletURL);
		request.setAttribute("liferay-ui:user-search:rowChecker", _rowChecker);
		request.setAttribute("liferay-ui:user-search:userParams", _userParams);
	}

	private static final String _END_PAGE =
		"/html/taglib/ui/user_search/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/user_search/start.jsp";

	private PortletURL _portletURL;
	private RowChecker _rowChecker;
	private LinkedHashMap<String, Object> _userParams;

}