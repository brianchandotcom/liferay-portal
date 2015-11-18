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
public abstract class BaseFieldsetTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getColumn() {
		return _column;
	}

	public boolean getCollapsed() {
		return _collapsed;
	}

	public boolean getCollapsible() {
		return _collapsible;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.String getHelpMessage() {
		return _helpMessage;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public boolean getLocalizeLabel() {
		return _localizeLabel;
	}

	public void setColumn(boolean column) {
		_column = column;

		setScopedAttribute("column", column);
	}

	public void setCollapsed(boolean collapsed) {
		_collapsed = collapsed;

		setScopedAttribute("collapsed", collapsed);
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;

		setScopedAttribute("collapsible", collapsible);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		_helpMessage = helpMessage;

		setScopedAttribute("helpMessage", helpMessage);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setLabel(java.lang.String label) {
		_label = label;

		setScopedAttribute("label", label);
	}

	public void setLocalizeLabel(boolean localizeLabel) {
		_localizeLabel = localizeLabel;

		setScopedAttribute("localizeLabel", localizeLabel);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_column = false;
		_collapsed = false;
		_collapsible = false;
		_cssClass = null;
		_helpMessage = null;
		_id = null;
		_label = null;
		_localizeLabel = true;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "column", _column);
		setNamespacedAttribute(request, "collapsed", _collapsed);
		setNamespacedAttribute(request, "collapsible", _collapsible);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "helpMessage", _helpMessage);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "localizeLabel", _localizeLabel);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:fieldset:";

	private static final String _START_PAGE =
		"/html/taglib/aui/fieldset/start.jsp";

	private boolean _column = false;
	private boolean _collapsed = false;
	private boolean _collapsible = false;
	private java.lang.String _cssClass = null;
	private java.lang.String _helpMessage = null;
	private java.lang.String _id = null;
	private java.lang.String _label = null;
	private boolean _localizeLabel = true;

}