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
public class BaseAudioTag extends com.liferay.taglib.util.IncludeTag {

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

	public java.lang.Object getFixedAttributes() {
		return _fixedAttributes;
	}

	public java.lang.Object getFlashVars() {
		return _flashVars;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getOggUrl() {
		return _oggUrl;
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

	public void setFixedAttributes(java.lang.Object fixedAttributes) {
		_fixedAttributes = fixedAttributes;

		setScopedAttribute("fixedAttributes", fixedAttributes);
	}

	public void setFlashVars(java.lang.Object flashVars) {
		_flashVars = flashVars;

		setScopedAttribute("flashVars", flashVars);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setOggUrl(java.lang.String oggUrl) {
		_oggUrl = oggUrl;

		setScopedAttribute("oggUrl", oggUrl);
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
		_boundingBox = null;
		_cssClass = null;
		_fixedAttributes = null;
		_flashVars = null;
		_id = null;
		_oggUrl = null;
		_type = "mp3";
		_url = null;
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
		setNamespacedAttribute(request, "boundingBox", _boundingBox);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "fixedAttributes", _fixedAttributes);
		setNamespacedAttribute(request, "flashVars", _flashVars);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "oggUrl", _oggUrl);
		setNamespacedAttribute(request, "type", _type);
		setNamespacedAttribute(request, "url", _url);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:audio:";

	private static final String _END_PAGE =
		"/html/taglib/aui/audio/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/audio/start.jsp";

	private java.lang.String _boundingBox = null;
	private java.lang.String _cssClass = null;
	private java.lang.Object _fixedAttributes = null;
	private java.lang.Object _flashVars = null;
	private java.lang.String _id = null;
	private java.lang.String _oggUrl = null;
	private java.lang.String _type = "mp3";
	private java.lang.String _url = null;

}