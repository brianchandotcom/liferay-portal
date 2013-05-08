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
public class BaseColTag extends com.liferay.taglib.util.IncludeTag {

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

	public int getOffset() {
		return _offset;
	}

	public int getOffsetWidth() {
		return _offsetWidth;
	}

	public int getSpan() {
		return _span;
	}

	public int getWidth() {
		return _width;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setOffset(int offset) {
		_offset = offset;

		setScopedAttribute("offset", offset);
	}

	public void setOffsetWidth(int offsetWidth) {
		_offsetWidth = offsetWidth;

		setScopedAttribute("offsetWidth", offsetWidth);
	}

	public void setSpan(int span) {
		_span = span;

		setScopedAttribute("span", span);
	}

	public void setWidth(int width) {
		_width = width;

		setScopedAttribute("width", width);
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_id = null;
		_offset = 0;
		_offsetWidth = 0;
		_span = 12;
		_width = 0;
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
		setNamespacedAttribute(request, "offset", _offset);
		setNamespacedAttribute(request, "offsetWidth", _offsetWidth);
		setNamespacedAttribute(request, "span", _span);
		setNamespacedAttribute(request, "width", _width);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:col:";

	private static final String _END_PAGE =
		"/html/taglib/aui/col/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/col/start.jsp";

	private java.lang.String _cssClass = null;
	private java.lang.String _id = null;
	private int _offset = 0;
	private int _offsetWidth = 0;
	private int _span = 12;
	private int _width = 0;

}