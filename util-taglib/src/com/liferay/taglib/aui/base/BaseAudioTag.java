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
public abstract class BaseAudioTag extends com.liferay.taglib.util.IncludeTag {

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

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getOggURL() {
		return _oggURL;
	}

	public java.lang.String getSwfURL() {
		return _swfURL;
	}

	public java.lang.String getType() {
		return _type;
	}

	public java.lang.String getUrl() {
		return _url;
	}

	public void setBoundingBox(java.lang.String boundingBox) {
		_boundingBox = boundingBox;

		setScopedAttribute("boundingBox", boundingBox);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setOggURL(java.lang.String oggURL) {
		_oggURL = oggURL;

		setScopedAttribute("oggURL", oggURL);
	}

	public void setSwfURL(java.lang.String swfURL) {
		_swfURL = swfURL;

		setScopedAttribute("swfURL", swfURL);
	}

	public void setType(java.lang.String type) {
		_type = type;

		setScopedAttribute("type", type);
	}

	public void setUrl(java.lang.String url) {
		_url = url;

		setScopedAttribute("url", url);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_boundingBox = null;
		_cssClass = null;
		_id = null;
		_oggURL = null;
		_swfURL = null;
		_type = "mp3";
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "boundingBox", _boundingBox);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "oggURL", _oggURL);
		setNamespacedAttribute(request, "swfURL", _swfURL);
		setNamespacedAttribute(request, "type", _type);
		setNamespacedAttribute(request, "url", _url);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:audio:";

	private static final String _PAGE =
		"/html/taglib/aui/audio/page.jsp";

	private java.lang.String _boundingBox = null;
	private java.lang.String _cssClass = null;
	private java.lang.String _id = null;
	private java.lang.String _oggURL = null;
	private java.lang.String _swfURL = null;
	private java.lang.String _type = "mp3";
	private java.lang.String _url = null;

}