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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Tibor Lipusz
 */
public class NavigationTag extends IncludeTag {

	public void setBulletStyle(String bulletStyle) {
		_bulletStyle = bulletStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setHeaderType(String headerType) {
		_headerType = headerType;
	}

	public void setIncludedLayouts(String includedLayouts) {
		_includedLayouts = includedLayouts;
	}

	public void setNestedChildren(boolean nestedChildren) {
		_nestedChildren = nestedChildren;
	}

	public void setPreview(boolean preview) {
		_preview = preview;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	@Override
	protected void cleanUp() {
		_bulletStyle = "1";
		_displayStyle = "1";
		_headerType = "none";
		_includedLayouts = "auto";
		_nestedChildren = true;
		_preview = false;
		_rootLayoutLevel = 1;
		_rootLayoutType = "absolute";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:navigation:bulletStyle", _bulletStyle);
		request.setAttribute(
			"liferay-ui:navigation:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:navigation:headerType", _headerType);
		request.setAttribute(
			"liferay-ui:navigation:includedLayouts", _includedLayouts);
		request.setAttribute(
			"liferay-ui:navigation:nestedChildren",
			String.valueOf(_nestedChildren));
		request.setAttribute(
			"liferay-ui:navigation:preview", String.valueOf(_preview));
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutLevel",
			String.valueOf(_rootLayoutLevel));
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutType", _rootLayoutType);
	}

	private static final String _PAGE = "/html/taglib/ui/navigation/page.jsp";

	private String _bulletStyle = "1";
	private String _displayStyle = "1";
	private String _headerType = "none";
	private String _includedLayouts = "auto";
	private boolean _nestedChildren = true;
	private boolean _preview = false;
	private int _rootLayoutLevel = 1;
	private String _rootLayoutType = "absolute";

}