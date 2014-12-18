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
public abstract class BaseVideoTag extends com.liferay.taglib.util.IncludeTag {

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

	public java.lang.Object getHeight() {
		return _height;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getOgvURL() {
		return _ogvURL;
	}

	public java.lang.String getPoster() {
		return _poster;
	}

	public java.lang.String getSwfURL() {
		return _swfURL;
	}

	public java.lang.String getUrl() {
		return _url;
	}

	public java.lang.Object getWidth() {
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

	public void setHeight(java.lang.Object height) {
		_height = height;

		setScopedAttribute("height", height);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setOgvURL(java.lang.String ogvURL) {
		_ogvURL = ogvURL;

		setScopedAttribute("ogvURL", ogvURL);
	}

	public void setPoster(java.lang.String poster) {
		_poster = poster;

		setScopedAttribute("poster", poster);
	}

	public void setSwfURL(java.lang.String swfURL) {
		_swfURL = swfURL;

		setScopedAttribute("swfURL", swfURL);
	}

	public void setUrl(java.lang.String url) {
		_url = url;

		setScopedAttribute("url", url);
	}

	public void setWidth(java.lang.Object width) {
		_width = width;

		setScopedAttribute("width", width);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_boundingBox = null;
		_cssClass = null;
		_flashPlayerVersion = "9,0,0,0";
		_height = null;
		_id = null;
		_ogvURL = null;
		_poster = null;
		_swfURL = null;
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
		setNamespacedAttribute(request, "ogvURL", _ogvURL);
		setNamespacedAttribute(request, "poster", _poster);
		setNamespacedAttribute(request, "swfURL", _swfURL);
		setNamespacedAttribute(request, "url", _url);
		setNamespacedAttribute(request, "width", _width);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:video:";

	private static final String _PAGE =
		"/html/taglib/aui/video/page.jsp";

	private java.lang.String _boundingBox = null;
	private java.lang.String _cssClass = null;
	private java.lang.String _flashPlayerVersion = "9,0,0,0";
	private java.lang.Object _height = null;
	private java.lang.String _id = null;
	private java.lang.String _ogvURL = null;
	private java.lang.String _poster = null;
	private java.lang.String _swfURL = null;
	private java.lang.String _url = null;
	private java.lang.Object _width = null;

}