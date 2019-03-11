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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public abstract class BaseContainerTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public boolean getFluid() {
		return _fluid;
	}

	public java.lang.String getId() {
		return _id;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;
	}

	public void setFluid(boolean fluid) {
		_fluid = fluid;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_fluid = true;
		_id = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "fluid", _fluid);
		setNamespacedAttribute(request, "id", _id);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:container:";

	private static final String _END_PAGE =
		"/html/taglib/aui/container/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/container/start.jsp";

	private java.lang.String _cssClass = null;
	private boolean _fluid = true;
	private java.lang.String _id = null;

}