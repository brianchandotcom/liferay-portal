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
 * @author Sergio González
 */
public class SitesDirectoryTag extends IncludeTag {

	public static final String SITES_CHILDREN = "children";

	public static final String SITES_PARENT_LEVEL = "parent-level";

	public static final String SITES_SIBLINGS = "siblings";

	public static final String SITES_TOP_LEVEL = "top-level";

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getSites() {
		return _sites;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setSites(String sites) {
		_sites = sites;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = "descriptive";
		_sites = SITES_TOP_LEVEL;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:sites-directory:displayStyle", _displayStyle);
		httpServletRequest.setAttribute(
			"liferay-ui:sites-directory:sites", String.valueOf(_sites));
	}

	private static final String _PAGE =
		"/html/taglib/ui/sites_directory/page.jsp";

	private String _displayStyle = "descriptive";
	private String _sites = SITES_TOP_LEVEL;

}