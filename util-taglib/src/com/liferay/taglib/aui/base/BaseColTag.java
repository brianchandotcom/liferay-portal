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
public abstract class BaseColTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getLg() {
		return _lg;
	}

	public java.lang.String getMd() {
		return _md;
	}

	public java.lang.String getSm() {
		return _sm;
	}

	public int getSpan() {
		return _span;
	}

	public int getWidth() {
		return _width;
	}

	public java.lang.String getXs() {
		return _xs;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setLg(java.lang.String lg) {
		_lg = lg;
	}

	public void setMd(java.lang.String md) {
		_md = md;
	}

	public void setSm(java.lang.String sm) {
		_sm = sm;
	}

	public void setSpan(int span) {
		_span = span;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public void setXs(java.lang.String xs) {
		_xs = xs;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_id = null;
		_lg = null;
		_md = null;
		_sm = null;
		_span = 12;
		_width = 0;
		_xs = null;
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
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "lg", _lg);
		setNamespacedAttribute(request, "md", _md);
		setNamespacedAttribute(request, "sm", _sm);
		setNamespacedAttribute(request, "span", _span);
		setNamespacedAttribute(request, "width", _width);
		setNamespacedAttribute(request, "xs", _xs);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:col:";

	private static final String _END_PAGE =
		"/html/taglib/aui/col/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/col/start.jsp";

	private java.lang.String _cssClass = null;
	private java.lang.String _id = null;
	private java.lang.String _lg = null;
	private java.lang.String _md = null;
	private java.lang.String _sm = null;
	private int _span = 12;
	private int _width = 0;
	private java.lang.String _xs = null;

}