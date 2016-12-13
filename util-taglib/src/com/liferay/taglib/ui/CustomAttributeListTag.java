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
 *             com.liferay.expando.taglib.servlet.taglib.CustomAttributeListTag}
 */
@Deprecated
public class CustomAttributeListTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public void setIgnoreAttributeNames(String ignoreAttributeNames) {
		_ignoreAttributeNames = ignoreAttributeNames;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_editable = false;
		_ignoreAttributeNames = null;
		_label = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:custom-attribute-list:className", _className);
		request.setAttribute(
			"liferay-ui:custom-attribute-list:classPK",
			String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:custom-attribute-list:editable",
			String.valueOf(_editable));
		request.setAttribute(
			"liferay-ui:custom-attribute-list:ignoreAttributeNames",
			_ignoreAttributeNames);
		request.setAttribute(
			"liferay-ui:custom-attribute-list:label", String.valueOf(_label));
	}

	private static final String _PAGE =
		"/html/taglib/ui/custom_attribute_list/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _editable;
	private String _ignoreAttributeNames;
	private boolean _label;

}