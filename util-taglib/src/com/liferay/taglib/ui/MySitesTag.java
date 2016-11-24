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
public class MySitesTag extends IncludeTag {

	public void setClassNames(String[] classNames) {
		_classNames = classNames;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setMax(int max) {
		_max = max;
	}

	@Override
	protected void cleanUp() {
		_classNames = null;
		_cssClass = null;
		_max = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:my_sites:classNames", _classNames);
		request.setAttribute(
			"liferay-ui:my_sites:cssClass", String.valueOf(_cssClass));
		request.setAttribute("liferay-ui:my_sites:max", String.valueOf(_max));
	}

	private static final String _PAGE = "/html/taglib/ui/my_sites/page.jsp";

	private String[] _classNames;
	private String _cssClass;
	private int _max;

}