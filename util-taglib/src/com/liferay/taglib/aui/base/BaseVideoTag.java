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
public class BaseVideoTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getBoundingBox() {
		return _boundingBox;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.String getFlashPlayerVersion() {
		return _flashPlayerVersion;
	}

	public java.lang.Integer getHeight() {
		return _height;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getOgvUrl() {
		return _ogvUrl;
	}

	public java.lang.String getPoster() {
		return _poster;
	}

	public java.lang.String getSwfUrl() {
		return _swfUrl;
	}

	public java.lang.String getUrl() {
		return _url;
	}

	public java.lang.Integer getWidth() {
		return _width;
	}

	public void setBoundingBox(java.lang.String boundingBox) {
		_boundingBox = boundingBox;

		setScopedAttribute("boundingBox", boundingBox);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setFlashPlayerVersion(java.lang.String flashPlayerVersion) {
		_flashPlayerVersion = flashPlayerVersion;

		setScopedAttribute("flashPlayerVersion", flashPlayerVersion);
	}

	public void setHeight(java.lang.Integer height) {
		_height = height;

		setScopedAttribute("height", height);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setOgvUrl(java.lang.String ogvUrl) {
		_ogvUrl = ogvUrl;

		setScopedAttribute("ogvUrl", ogvUrl);
	}

	public void setPoster(java.lang.String poster) {
		_poster = poster;

		setScopedAttribute("poster", poster);
	}

	public void setSwfUrl(java.lang.String swfUrl) {
		_swfUrl = swfUrl;

		setScopedAttribute("swfUrl", swfUrl);
	}

	public void setUrl(java.lang.String url) {
		_url = url;

		setScopedAttribute("url", url);
	}

	public void setWidth(java.lang.Integer width) {
		_width = width;

		setScopedAttribute("width", width);
	}

	@Override
	protected void cleanUp() {
		_boundingBox = null;
		_cssClass = null;
		_flashPlayerVersion = null;
		_height = null;
		_id = null;
		_ogvUrl = null;
		_poster = null;
		_swfUrl = null;
		_url = null;
		_width = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "boundingBox", _boundingBox);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "flashPlayerVersion", _flashPlayerVersion);
		setNamespacedAttribute(request, "height", _height);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "ogvUrl", _ogvUrl);
		setNamespacedAttribute(request, "poster", _poster);
		setNamespacedAttribute(request, "swfUrl", _swfUrl);
		setNamespacedAttribute(request, "url", _url);
		setNamespacedAttribute(request, "width", _width);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:video:";

	private static final String _PAGE =
		"/html/taglib/aui/video/page.jsp";

	private java.lang.String _boundingBox = null;
	private java.lang.String _cssClass = null;
	private java.lang.String _flashPlayerVersion = null;
	private java.lang.Integer _height = null;
	private java.lang.String _id = null;
	private java.lang.String _ogvUrl = null;
	private java.lang.String _poster = null;
	private java.lang.String _swfUrl = null;
	private java.lang.String _url = null;
	private java.lang.Integer _width = null;

}