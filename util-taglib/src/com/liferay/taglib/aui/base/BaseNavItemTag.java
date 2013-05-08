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
public class BaseNavItemTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getAnchorCssClass() {
		return _anchorCssClass;
	}

	public java.lang.String getAnchorId() {
		return _anchorId;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public boolean getDropdown() {
		return _dropdown;
	}

	public java.lang.Object getHref() {
		return _href;
	}

	public java.lang.String getIconClass() {
		return _iconClass;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public boolean getSelected() {
		return _selected;
	}

	public java.lang.String getTitle() {
		return _title;
	}

	public void setAnchorCssClass(java.lang.String anchorCssClass) {
		_anchorCssClass = anchorCssClass;

		setScopedAttribute("anchorCssClass", anchorCssClass);
	}

	public void setAnchorId(java.lang.String anchorId) {
		_anchorId = anchorId;

		setScopedAttribute("anchorId", anchorId);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setDropdown(boolean dropdown) {
		_dropdown = dropdown;

		setScopedAttribute("dropdown", dropdown);
	}

	public void setHref(java.lang.Object href) {
		_href = href;

		setScopedAttribute("href", href);
	}

	public void setIconClass(java.lang.String iconClass) {
		_iconClass = iconClass;

		setScopedAttribute("iconClass", iconClass);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setLabel(java.lang.String label) {
		_label = label;

		setScopedAttribute("label", label);
	}

	public void setSelected(boolean selected) {
		_selected = selected;

		setScopedAttribute("selected", selected);
	}

	public void setTitle(java.lang.String title) {
		_title = title;

		setScopedAttribute("title", title);
	}

	@Override
	protected void cleanUp() {
		_anchorCssClass = null;
		_anchorId = null;
		_cssClass = null;
		_dropdown = false;
		_href = "javascript:void(0);";
		_iconClass = null;
		_id = null;
		_label = null;
		_selected = false;
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

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "anchorCssClass", _anchorCssClass);
		setNamespacedAttribute(request, "anchorId", _anchorId);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "dropdown", _dropdown);
		setNamespacedAttribute(request, "href", _href);
		setNamespacedAttribute(request, "iconClass", _iconClass);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "selected", _selected);
		setNamespacedAttribute(request, "title", _title);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:nav-item:";

	private static final String _END_PAGE =
		"/html/taglib/aui/nav_item/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/nav_item/start.jsp";

	private java.lang.String _anchorCssClass = null;
	private java.lang.String _anchorId = null;
	private java.lang.String _cssClass = null;
	private boolean _dropdown = false;
	private java.lang.Object _href = "javascript:void(0);";
	private java.lang.String _iconClass = null;
	private java.lang.String _id = null;
	private java.lang.String _label = null;
	private boolean _selected = false;
	private java.lang.String _title = null;

}