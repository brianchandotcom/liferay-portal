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

package com.liferay.taglib.aui.base;

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class BaseToolTagImpl extends javax.servlet.jsp.tagext.TagSupport {

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public java.lang.String getHandler() {
		return _handler;
	}

	public java.lang.String getIcon() {
		return _icon;
	}

	@Override
	public java.lang.String getId() {
		return _id;
	}

	public void setHandler(java.lang.String handler) {
		_handler = handler;
	}

	public void setIcon(java.lang.String icon) {
		_icon = icon;
	}

	@Override
	public void setId(java.lang.String id) {
		_id = id;
	}

	protected void cleanUp() {
		_handler = null;
		_icon = null;
		_id = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/aui/tool/page.jsp";

	private java.lang.String _handler = null;
	private java.lang.String _icon = null;
	private java.lang.String _id = null;

}