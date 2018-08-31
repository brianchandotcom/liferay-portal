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
public abstract class BaseFieldWrapperTag extends com.liferay.taglib.util.IncludeTag {

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

	public boolean getLocalizeLabel() {
		return _localizeLabel;
	}

	public java.lang.String getName() {
		return _name;
	}

	public boolean getRequired() {
		return _required;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(java.lang.Object data) {
		_data = data;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(java.lang.String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setLabel(java.lang.String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setLocalizeLabel(boolean localizeLabel) {
		_localizeLabel = localizeLabel;
	}

	public void setName(java.lang.String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_data = null;
		_first = false;
		_helpMessage = null;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_last = false;
		_localizeLabel = true;
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
		request.setAttribute("aui:field-wrapper:cssClass", _cssClass);
		request.setAttribute("aui:field-wrapper:data", _data);
		request.setAttribute("aui:field-wrapper:first", String.valueOf(_first));
		request.setAttribute("aui:field-wrapper:helpMessage", _helpMessage);
		request.setAttribute("aui:field-wrapper:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:field-wrapper:inlineLabel", _inlineLabel);
		request.setAttribute("aui:field-wrapper:label", _label);
		request.setAttribute("aui:field-wrapper:last", String.valueOf(_last));
		request.setAttribute("aui:field-wrapper:localizeLabel", String.valueOf(_localizeLabel));
		request.setAttribute("aui:field-wrapper:name", _name);
		request.setAttribute("aui:field-wrapper:required", String.valueOf(_required));
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
	private boolean _localizeLabel = true;
	private java.lang.String _name = null;
	private boolean _required = false;

}