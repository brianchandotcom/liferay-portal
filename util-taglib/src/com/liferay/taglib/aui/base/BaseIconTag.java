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
public class BaseIconTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getData() {
		return _data;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getImage() {
		return _image;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public java.lang.String getTarget() {
		return _target;
	}

	public java.lang.String getUrl() {
		return _url;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.util.Map<java.lang.String, java.lang.Object> data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setImage(java.lang.String image) {
		_image = image;

		setScopedAttribute("image", image);
	}

	public void setLabel(java.lang.String label) {
		_label = label;

		setScopedAttribute("label", label);
	}

	public void setTarget(java.lang.String target) {
		_target = target;

		setScopedAttribute("target", target);
	}

	public void setUrl(java.lang.String url) {
		_url = url;

		setScopedAttribute("url", url);
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_data = null;
		_id = null;
		_image = null;
		_label = null;
		_target = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "data", _data);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "image", _image);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "target", _target);
		setNamespacedAttribute(request, "url", _url);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:icon:";

	private static final String _PAGE =
		"/html/taglib/aui/icon/page.jsp";

	private java.lang.String _cssClass = null;
	private java.util.Map<java.lang.String, java.lang.Object> _data = null;
	private java.lang.String _id = null;
	private java.lang.String _image = null;
	private java.lang.String _label = null;
	private java.lang.String _target = null;
	private java.lang.String _url = null;

}