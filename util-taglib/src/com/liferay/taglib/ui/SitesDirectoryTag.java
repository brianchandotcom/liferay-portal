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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SitesDirectoryTag extends IncludeTag {

	public void setBulletStyle(String bulletStyle) {
		_bulletStyle = bulletStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setHeaderType(String headerType) {
		_headerType = headerType;
	}

	public void setIncludedGroups(String includedGroups) {
		_includedGroups = includedGroups;
	}

	public void setNestedChildren(boolean nestedChildren) {
		_nestedChildren = nestedChildren;
	}

	public void setRootGroupLevel(int rootGroupLevel) {
		_rootGroupLevel = rootGroupLevel;
	}

	public void setRootGroupType(String rootGroupType) {
		_rootGroupType = rootGroupType;
	}

	@Override
	protected void cleanUp() {
		_bulletStyle = "1";
		_displayStyle = "descriptive";
		_headerType = "none";
		_includedGroups = "auto";
		_nestedChildren = true;
		_rootGroupLevel = 1;
		_rootGroupType = "absolute";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:sites-directory:bulletStyle", _bulletStyle);
		request.setAttribute(
			"liferay-ui:sites-directory:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:sites-directory:headerType", _headerType);
		request.setAttribute(
			"liferay-ui:sites-directory:includedGroups", _includedGroups);
		request.setAttribute(
			"liferay-ui:sites-directory:nestedChildren",
			String.valueOf(_nestedChildren));
		request.setAttribute(
			"liferay-ui:sites-directory:rootGroupLevel",
			String.valueOf(_rootGroupLevel));
		request.setAttribute(
			"liferay-ui:sites-directory:rootGroupType", _rootGroupType);
	}

	private static final String _PAGE =
		"/html/taglib/ui/sites_directory/page.jsp";

	private String _bulletStyle = "1";
	private String _displayStyle = "descriptive";
	private String _headerType = "none";
	private String _includedGroups = "auto";
	private boolean _nestedChildren = true;
	private int _rootGroupLevel = 1;
	private String _rootGroupType = "absolute";

}