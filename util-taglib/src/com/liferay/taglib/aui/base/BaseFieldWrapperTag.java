/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
public class BaseFieldWrapperTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.Object getData() {
		return _data;
	}

	public boolean getFirst() {
		return _first;
	}

	public java.lang.String getHelpMessage() {
		return _helpMessage;
	}

	public boolean getInlineField() {
		return _inlineField;
	}

	public java.lang.String getInlineLabel() {
		return _inlineLabel;
	}

	public java.lang.String getLabel() {
		return _label;
	}

	public boolean getLast() {
		return _last;
	}

	public java.lang.String getName() {
		return _name;
	}

	public boolean getRequired() {
		return _required;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.lang.Object data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setFirst(boolean first) {
		_first = first;

		setScopedAttribute("first", first);
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		_helpMessage = helpMessage;

		setScopedAttribute("helpMessage", helpMessage);
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;

		setScopedAttribute("inlineField", inlineField);
	}

	public void setInlineLabel(java.lang.String inlineLabel) {
		_inlineLabel = inlineLabel;

		setScopedAttribute("inlineLabel", inlineLabel);
	}

	public void setLabel(java.lang.String label) {
		_label = label;

		setScopedAttribute("label", label);
	}

	public void setLast(boolean last) {
		_last = last;

		setScopedAttribute("last", last);
	}

	public void setName(java.lang.String name) {
		_name = name;

		setScopedAttribute("name", name);
	}

	public void setRequired(boolean required) {
		_required = required;

		setScopedAttribute("required", required);
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_data = null;
		_first = false;
		_helpMessage = null;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_last = false;
		_name = null;
		_required = false;
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
		setNamespacedAttribute(request, "data", _data);
		setNamespacedAttribute(request, "first", _first);
		setNamespacedAttribute(request, "helpMessage", _helpMessage);
		setNamespacedAttribute(request, "inlineField", _inlineField);
		setNamespacedAttribute(request, "inlineLabel", _inlineLabel);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "last", _last);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "required", _required);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:field-wrapper:";

	private static final String _END_PAGE =
		"/html/taglib/aui/field_wrapper/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/field_wrapper/start.jsp";

	private java.lang.String _cssClass = null;
	private java.lang.Object _data = null;
	private boolean _first = false;
	private java.lang.String _helpMessage = null;
	private boolean _inlineField = false;
	private java.lang.String _inlineLabel = null;
	private java.lang.String _label = null;
	private boolean _last = false;
	private java.lang.String _name = null;
	private boolean _required = false;

}