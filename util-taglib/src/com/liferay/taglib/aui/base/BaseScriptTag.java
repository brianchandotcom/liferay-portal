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
 */
public abstract class BaseScriptTag extends com.liferay.taglib.util.PositionTagSupport {

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public java.lang.String getRequire() {
		return _require;
	}

	public boolean getSandbox() {
		return _sandbox;
	}

	public java.lang.String getUse() {
		return _use;
	}

	public void setRequire(java.lang.String require) {
		_require = require;
	}

	public void setSandbox(boolean sandbox) {
		_sandbox = sandbox;
	}

	public void setUse(java.lang.String use) {
		_use = use;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_require = null;
		_sandbox = false;
		_use = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/aui/script/page.jsp";

	private java.lang.String _require = null;
	private boolean _sandbox = false;
	private java.lang.String _use = null;

}