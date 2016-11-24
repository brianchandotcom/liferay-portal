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

import com.liferay.portal.kernel.servlet.taglib.ui.QuickAccessEntry;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class QuickAccessTag extends IncludeTag {

	public void setContentId(String contentId) {
		_contentId = contentId;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		List<QuickAccessEntry> quickAccessEntries =
			(List<QuickAccessEntry>)request.getAttribute(
				WebKeys.PORTLET_QUICK_ACCESS_ENTRIES);

		request.setAttribute("liferay-ui:quick-access:contentId", _contentId);
		request.setAttribute(
			"liferay-ui:quick-access:quickAccessEntries", quickAccessEntries);
	}

	private static final String _PAGE = "/html/taglib/ui/quick_access/page.jsp";

	private String _contentId;

}