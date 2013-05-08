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
public class BaseNavBarSearchTag extends com.liferay.taglib.util.IncludeTag {

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

	public java.lang.String getFile() {
		return _file;
	}

	public com.liferay.portal.kernel.dao.search.SearchContainer getSearchContainer() {
		return _searchContainer;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setFile(java.lang.String file) {
		_file = file;

		setScopedAttribute("file", file);
	}

	public void setSearchContainer(com.liferay.portal.kernel.dao.search.SearchContainer searchContainer) {
		_searchContainer = searchContainer;

		setScopedAttribute("searchContainer", searchContainer);
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_id = null;
		_file = null;
		_searchContainer = null;
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
		setNamespacedAttribute(request, "file", _file);
		setNamespacedAttribute(request, "searchContainer", _searchContainer);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:nav-bar-search:";

	private static final String _END_PAGE =
		"/html/taglib/aui/nav_bar_search/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/nav_bar_search/start.jsp";

	private java.lang.String _cssClass = null;
	private java.lang.String _id = null;
	private java.lang.String _file = null;
	private com.liferay.portal.kernel.dao.search.SearchContainer _searchContainer = null;

}