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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddOnEntry;
import com.liferay.taglib.servlet.JspWriterHttpServletResponse;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 */
public class AssetAddOnEntryDisplayTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		for (AssetAddOnEntry entry : _entries) {
			try {
				entry.include(request, getResponse());
			}
			catch (IOException e) {
				_log.error(e);
			}
		}

		return super.doEndTag();
	}

	public void setEntries(List<? extends AssetAddOnEntry> entries) {
		_entries = entries;
	}

	@Override
	protected void cleanUp() {
		_entries = null;
	}

	protected HttpServletResponse getResponse() {
		return new JspWriterHttpServletResponse(pageContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAddOnEntryDisplayTag.class);

	private List<? extends AssetAddOnEntry> _entries;

}