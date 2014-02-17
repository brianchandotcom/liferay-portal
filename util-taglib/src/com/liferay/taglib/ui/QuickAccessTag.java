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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.QuickAccessEntry;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class QuickAccessTag extends IncludeTag {

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		List<QuickAccessEntry> quickAccessEntries =
			(List<QuickAccessEntry>)request.getAttribute(
				QuickAccessEntry.ATTRIBUTE_NAME);

		request.setAttribute(
			"liferay-ui:quick-access:quickAccessEntries", quickAccessEntries);
	}

	private static final String _PAGE = "/html/taglib/ui/quick_access/page.jsp";

	private static Log _log = LogFactoryUtil.getLog(QuickAccessTag.class);

}