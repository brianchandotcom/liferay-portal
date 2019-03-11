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
public abstract class BaseAlertTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getAnimated() {
		return _animated;
	}

	public boolean getCloseable() {
		return _closeable;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public boolean getDestroyOnHide() {
		return _destroyOnHide;
	}

	public java.lang.Object getDuration() {
		return _duration;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getType() {
		return _type;
	}

	public void setAnimated(boolean animated) {
		_animated = animated;
	}

	public void setCloseable(boolean closeable) {
		_closeable = closeable;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;
	}

	public void setDestroyOnHide(boolean destroyOnHide) {
		_destroyOnHide = destroyOnHide;
	}

	public void setDuration(java.lang.Object duration) {
		_duration = duration;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setType(java.lang.String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_animated = false;
		_closeable = true;
		_cssClass = null;
		_destroyOnHide = false;
		_duration = 0.15;
		_id = null;
		_type = "info";
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
		setNamespacedAttribute(request, "animated", _animated);
		setNamespacedAttribute(request, "closeable", _closeable);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "destroyOnHide", _destroyOnHide);
		setNamespacedAttribute(request, "duration", _duration);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "type", _type);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:alert:";

	private static final String _END_PAGE =
		"/html/taglib/aui/alert/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/alert/start.jsp";

	private boolean _animated = false;
	private boolean _closeable = true;
	private java.lang.String _cssClass = null;
	private boolean _destroyOnHide = false;
	private java.lang.Object _duration = 0.15;
	private java.lang.String _id = null;
	private java.lang.String _type = "info";

}