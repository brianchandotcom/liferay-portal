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
public class BaseSelectTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.Object getBean() {
		return _bean;
	}

	public boolean getChangesContext() {
		return _changesContext;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getData() {
		return _data;
	}

	public boolean getDisabled() {
		return _disabled;
	}

	public boolean getFirst() {
		return _first;
	}

	public java.lang.String getHelpMessage() {
		return _helpMessage;
	}

	public java.lang.String getId() {
		return _id;
	}

	public boolean getIgnoreRequestValue() {
		return _ignoreRequestValue;
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

	public java.lang.String getListType() {
		return _listType;
	}

	public java.lang.String getListTypeFieldName() {
		return _listTypeFieldName;
	}

	public boolean getMultiple() {
		return _multiple;
	}

	public java.lang.String getName() {
		return _name;
	}

	public java.lang.String getOnChange() {
		return _onChange;
	}

	public java.lang.String getOnClick() {
		return _onClick;
	}

	public java.lang.String getPrefix() {
		return _prefix;
	}

	public boolean getShowEmptyOption() {
		return _showEmptyOption;
	}

	public java.lang.String getSuffix() {
		return _suffix;
	}

	public java.lang.String getTitle() {
		return _title;
	}

	public boolean getUseNamespace() {
		return _useNamespace;
	}

	public void setBean(java.lang.Object bean) {
		_bean = bean;

		setScopedAttribute("bean", bean);
	}

	public void setChangesContext(boolean changesContext) {
		_changesContext = changesContext;

		setScopedAttribute("changesContext", changesContext);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.util.Map<java.lang.String, java.lang.Object> data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;

		setScopedAttribute("disabled", disabled);
	}

	public void setFirst(boolean first) {
		_first = first;

		setScopedAttribute("first", first);
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		_helpMessage = helpMessage;

		setScopedAttribute("helpMessage", helpMessage);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;

		setScopedAttribute("ignoreRequestValue", ignoreRequestValue);
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

	public void setListType(java.lang.String listType) {
		_listType = listType;

		setScopedAttribute("listType", listType);
	}

	public void setListTypeFieldName(java.lang.String listTypeFieldName) {
		_listTypeFieldName = listTypeFieldName;

		setScopedAttribute("listTypeFieldName", listTypeFieldName);
	}

	public void setMultiple(boolean multiple) {
		_multiple = multiple;

		setScopedAttribute("multiple", multiple);
	}

	public void setName(java.lang.String name) {
		_name = name;

		setScopedAttribute("name", name);
	}

	public void setOnChange(java.lang.String onChange) {
		_onChange = onChange;

		setScopedAttribute("onChange", onChange);
	}

	public void setOnClick(java.lang.String onClick) {
		_onClick = onClick;

		setScopedAttribute("onClick", onClick);
	}

	public void setPrefix(java.lang.String prefix) {
		_prefix = prefix;

		setScopedAttribute("prefix", prefix);
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;

		setScopedAttribute("showEmptyOption", showEmptyOption);
	}

	public void setSuffix(java.lang.String suffix) {
		_suffix = suffix;

		setScopedAttribute("suffix", suffix);
	}

	public void setTitle(java.lang.String title) {
		_title = title;

		setScopedAttribute("title", title);
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;

		setScopedAttribute("useNamespace", useNamespace);
	}

	@Override
	protected void cleanUp() {
		_bean = null;
		_changesContext = false;
		_cssClass = null;
		_data = null;
		_disabled = false;
		_first = false;
		_helpMessage = null;
		_id = null;
		_ignoreRequestValue = false;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_last = false;
		_listType = null;
		_listTypeFieldName = null;
		_multiple = false;
		_name = null;
		_onChange = null;
		_onClick = null;
		_prefix = null;
		_showEmptyOption = false;
		_suffix = null;
		_title = null;
		_useNamespace = true;
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
		setNamespacedAttribute(request, "bean", _bean);
		setNamespacedAttribute(request, "changesContext", _changesContext);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "data", _data);
		setNamespacedAttribute(request, "disabled", _disabled);
		setNamespacedAttribute(request, "first", _first);
		setNamespacedAttribute(request, "helpMessage", _helpMessage);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "ignoreRequestValue", _ignoreRequestValue);
		setNamespacedAttribute(request, "inlineField", _inlineField);
		setNamespacedAttribute(request, "inlineLabel", _inlineLabel);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "last", _last);
		setNamespacedAttribute(request, "listType", _listType);
		setNamespacedAttribute(request, "listTypeFieldName", _listTypeFieldName);
		setNamespacedAttribute(request, "multiple", _multiple);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "onChange", _onChange);
		setNamespacedAttribute(request, "onClick", _onClick);
		setNamespacedAttribute(request, "prefix", _prefix);
		setNamespacedAttribute(request, "showEmptyOption", _showEmptyOption);
		setNamespacedAttribute(request, "suffix", _suffix);
		setNamespacedAttribute(request, "title", _title);
		setNamespacedAttribute(request, "useNamespace", _useNamespace);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:select:";

	private static final String _END_PAGE =
		"/html/taglib/aui/select/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/select/start.jsp";

	private java.lang.Object _bean = null;
	private boolean _changesContext = false;
	private java.lang.String _cssClass = null;
	private java.util.Map<java.lang.String, java.lang.Object> _data = null;
	private boolean _disabled = false;
	private boolean _first = false;
	private java.lang.String _helpMessage = null;
	private java.lang.String _id = null;
	private boolean _ignoreRequestValue = false;
	private boolean _inlineField = false;
	private java.lang.String _inlineLabel = null;
	private java.lang.String _label = null;
	private boolean _last = false;
	private java.lang.String _listType = null;
	private java.lang.String _listTypeFieldName = null;
	private boolean _multiple = false;
	private java.lang.String _name = null;
	private java.lang.String _onChange = null;
	private java.lang.String _onClick = null;
	private java.lang.String _prefix = null;
	private boolean _showEmptyOption = false;
	private java.lang.String _suffix = null;
	private java.lang.String _title = null;
	private boolean _useNamespace = true;

}