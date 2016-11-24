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
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class JournalContentSearchTag extends IncludeTag {

	public void setShowListed(boolean showListed) {
		_showListed = showListed;
	}

	public void setTargetPortletId(String targetPortletId) {
		_targetPortletId = targetPortletId;
	}

	@Override
	protected void cleanUp() {
		_showListed = true;
		_targetPortletId = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:journal-content-search:showListed",
			String.valueOf(_showListed));
		request.setAttribute(
			"liferay-ui:journal-content-search:targetPortletId",
			_targetPortletId);
	}

	private static final String _PAGE =
		"/html/taglib/ui/journal_content_search/page.jsp";

	private boolean _showListed = true;
	private String _targetPortletId;

}