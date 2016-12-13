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
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.expando.taglib.servlet.taglib.CustomAttributeTag}
 */
@Deprecated
public class CustomAttributeTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_editable = false;
		_label = false;
		_name = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:custom-attribute:className", _className);
		request.setAttribute(
			"liferay-ui:custom-attribute:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:custom-attribute:editable", String.valueOf(_editable));
		request.setAttribute(
			"liferay-ui:custom-attribute:label", String.valueOf(_label));
		request.setAttribute("liferay-ui:custom-attribute:name", _name);
	}

	private static final String _PAGE =
		"/html/taglib/ui/custom_attribute/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _editable;
	private boolean _label;
	private String _name;

}