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

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public class BaseATag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getAriaRole() {
		return _ariaRole;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getData() {
		return _data;
	}

	public java.lang.String getHref() {
		return _href;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public java.lang.String getLang() {
		return _lang;
	}

	public java.lang.String getOnClick() {
		return _onClick;
	}

	public java.lang.String getTarget() {
		return _target;
	}

	public java.lang.String getTitle() {
		return _title;
	}

	public void setAriaRole(java.lang.String ariaRole) {
		_ariaRole = ariaRole;

		setScopedAttribute("ariaRole", ariaRole);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.util.Map<java.lang.String, java.lang.Object> data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setHref(java.lang.String href) {
		_href = href;

		setScopedAttribute("href", href);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setLabel(java.lang.String label) {
		_label = label;

		setScopedAttribute("label", label);
	}

	public void setLang(java.lang.String lang) {
		_lang = lang;

		setScopedAttribute("lang", lang);
	}

	public void setOnClick(java.lang.String onClick) {
		_onClick = onClick;

		setScopedAttribute("onClick", onClick);
	}

	public void setTarget(java.lang.String target) {
		_target = target;

		setScopedAttribute("target", target);
	}

	public void setTitle(java.lang.String title) {
		_title = title;

		setScopedAttribute("title", title);
	}

	@Override
	protected void cleanUp() {
		_ariaRole = null;
		_cssClass = null;
		_data = null;
		_href = null;
		_id = null;
		_label = null;
		_lang = null;
		_onClick = null;
		_target = null;
		_title = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:a:";

	private static final String _END_PAGE =
		"/html/taglib/aui/a/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/a/start.jsp";

	private java.lang.String _ariaRole = null;
	private java.lang.String _cssClass = null;
	private java.util.Map<java.lang.String, java.lang.Object> _data = null;
	private java.lang.String _href = null;
	private java.lang.String _id = null;
	private java.lang.String _label = null;
	private java.lang.String _lang = null;
	private java.lang.String _onClick = null;
	private java.lang.String _target = null;
	private java.lang.String _title = null;

}