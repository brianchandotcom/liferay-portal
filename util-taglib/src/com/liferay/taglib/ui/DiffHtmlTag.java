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
 * @author Julio Camarero
 */
public class DiffHtmlTag extends IncludeTag {

	public String getDiffHtmlResults() {
		return _diffHtmlResults;
	}

	public String getInfoMessage() {
		return _infoMessage;
	}

	public void setDiffHtmlResults(String diffHtmlResults) {
		_diffHtmlResults = diffHtmlResults;
	}

	public void setInfoMessage(String infoMessage) {
		_infoMessage = infoMessage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_diffHtmlResults = null;
		_infoMessage = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:diff-html:diffHtmlResults", _diffHtmlResults);
		httpServletRequest.setAttribute(
			"liferay-ui:diff-html:infoMessage", _infoMessage);
	}

	private static final String _PAGE = "/html/taglib/ui/diff_html/page.jsp";

	private String _diffHtmlResults;
	private String _infoMessage;

}