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
public abstract class BaseNavTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getAriaLabel() {
		return _ariaLabel;
	}

	public java.lang.String getAriaRole() {
		return _ariaRole;
	}

	public boolean getCollapsible() {
		return _collapsible;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.String getIcon() {
		return _icon;
	}

	public java.lang.String getId() {
		return _id;
	}

	public com.liferay.portal.kernel.dao.search.SearchContainer<?> getSearchContainer() {
		return _searchContainer;
	}

	public boolean getUseNamespace() {
		return _useNamespace;
	}

	public void setAriaLabel(java.lang.String ariaLabel) {
		_ariaLabel = ariaLabel;
	}

	public void setAriaRole(java.lang.String ariaRole) {
		_ariaRole = ariaRole;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;
	}

	public void setIcon(java.lang.String icon) {
		_icon = icon;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setSearchContainer(com.liferay.portal.kernel.dao.search.SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_ariaLabel = null;
		_ariaRole = null;
		_collapsible = false;
		_cssClass = null;
		_icon = null;
		_id = null;
		_searchContainer = null;
		_useNamespace = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "ariaLabel", _ariaLabel);
		setNamespacedAttribute(request, "ariaRole", _ariaRole);
		setNamespacedAttribute(request, "collapsible", _collapsible);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "icon", _icon);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "searchContainer", _searchContainer);
		setNamespacedAttribute(request, "useNamespace", _useNamespace);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:nav:";

	private static final String _PAGE =
		"/html/taglib/aui/nav/page.jsp";

	private java.lang.String _ariaLabel = null;
	private java.lang.String _ariaRole = null;
	private boolean _collapsible = false;
	private java.lang.String _cssClass = null;
	private java.lang.String _icon = null;
	private java.lang.String _id = null;
	private com.liferay.portal.kernel.dao.search.SearchContainer<?> _searchContainer = null;
	private boolean _useNamespace = true;

}