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
public class BaseInputTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getAutoSize() {
		return _autoSize;
	}

	public java.lang.Object getBean() {
		return _bean;
	}

	public boolean getChangesContext() {
		return _changesContext;
	}

	public boolean getChecked() {
		return _checked;
	}

	public java.lang.String getCheckBoxLabel() {
		return _checkBoxLabel;
	}

	public long getClassPK() {
		return _classPK;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.Object getData() {
		return _data;
	}

	public java.lang.String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public boolean getDisabled() {
		return _disabled;
	}

	public java.lang.String getField() {
		return _field;
	}

	public java.lang.String getFieldParam() {
		return _fieldParam;
	}

	public boolean getFirst() {
		return _first;
	}

	public java.lang.String getFormName() {
		return _formName;
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

	public java.lang.String getLanguageId() {
		return _languageId;
	}

	public boolean getLast() {
		return _last;
	}

	public java.lang.Class<?> getModel() {
		return _model;
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

	public java.lang.String getPlaceholder() {
		return _placeholder;
	}

	public java.lang.String getPrefix() {
		return _prefix;
	}

	public boolean getRequired() {
		return _required;
	}

	public boolean getResizable() {
		return _resizable;
	}

	public boolean getShowRequiredLabel() {
		return _showRequiredLabel;
	}

	public java.lang.String getSuffix() {
		return _suffix;
	}

	public java.lang.String getTitle() {
		return _title;
	}

	public java.lang.String getType() {
		return _type;
	}

	public boolean getUseNamespace() {
		return _useNamespace;
	}

	public java.lang.Object getValue() {
		return _value;
	}

	public void setAutoSize(boolean autoSize) {
		_autoSize = autoSize;

		setScopedAttribute("autoSize", autoSize);
	}

	public void setBean(java.lang.Object bean) {
		_bean = bean;

		setScopedAttribute("bean", bean);
	}

	public void setChangesContext(boolean changesContext) {
		_changesContext = changesContext;

		setScopedAttribute("changesContext", changesContext);
	}

	public void setChecked(boolean checked) {
		_checked = checked;

		setScopedAttribute("checked", checked);
	}

	public void setCheckBoxLabel(java.lang.String checkBoxLabel) {
		_checkBoxLabel = checkBoxLabel;

		setScopedAttribute("checkBoxLabel", checkBoxLabel);
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		setScopedAttribute("classPK", classPK);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.lang.Object data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;

		setScopedAttribute("defaultLanguageId", defaultLanguageId);
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;

		setScopedAttribute("disabled", disabled);
	}

	public void setField(java.lang.String field) {
		_field = field;

		setScopedAttribute("field", field);
	}

	public void setFieldParam(java.lang.String fieldParam) {
		_fieldParam = fieldParam;

		setScopedAttribute("fieldParam", fieldParam);
	}

	public void setFirst(boolean first) {
		_first = first;

		setScopedAttribute("first", first);
	}

	public void setFormName(java.lang.String formName) {
		_formName = formName;

		setScopedAttribute("formName", formName);
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

	public void setLanguageId(java.lang.String languageId) {
		_languageId = languageId;

		setScopedAttribute("languageId", languageId);
	}

	public void setLast(boolean last) {
		_last = last;

		setScopedAttribute("last", last);
	}

	public void setModel(java.lang.Class<?> model) {
		_model = model;

		setScopedAttribute("model", model);
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

	public void setPlaceholder(java.lang.String placeholder) {
		_placeholder = placeholder;

		setScopedAttribute("placeholder", placeholder);
	}

	public void setPrefix(java.lang.String prefix) {
		_prefix = prefix;

		setScopedAttribute("prefix", prefix);
	}

	public void setRequired(boolean required) {
		_required = required;

		setScopedAttribute("required", required);
	}

	public void setResizable(boolean resizable) {
		_resizable = resizable;

		setScopedAttribute("resizable", resizable);
	}

	public void setShowRequiredLabel(boolean showRequiredLabel) {
		_showRequiredLabel = showRequiredLabel;

		setScopedAttribute("showRequiredLabel", showRequiredLabel);
	}

	public void setSuffix(java.lang.String suffix) {
		_suffix = suffix;

		setScopedAttribute("suffix", suffix);
	}

	public void setTitle(java.lang.String title) {
		_title = title;

		setScopedAttribute("title", title);
	}

	public void setType(java.lang.String type) {
		_type = type;

		setScopedAttribute("type", type);
	}

	public void setUseNamespace(boolean useNamespace) {
		_useNamespace = useNamespace;

		setScopedAttribute("useNamespace", useNamespace);
	}

	public void setValue(java.lang.Object value) {
		_value = value;

		setScopedAttribute("value", value);
	}

	@Override
	protected void cleanUp() {
		_autoSize = false;
		_bean = null;
		_changesContext = false;
		_checked = false;
		_checkBoxLabel = null;
		_classPK = 0;
		_cssClass = null;
		_data = null;
		_defaultLanguageId = null;
		_disabled = false;
		_field = null;
		_fieldParam = null;
		_first = false;
		_formName = null;
		_helpMessage = null;
		_id = null;
		_ignoreRequestValue = false;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_languageId = null;
		_last = false;
		_model = null;
		_multiple = false;
		_name = null;
		_onChange = null;
		_onClick = null;
		_placeholder = null;
		_prefix = null;
		_required = false;
		_resizable = false;
		_showRequiredLabel = true;
		_suffix = null;
		_title = null;
		_type = null;
		_useNamespace = true;
		_value = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "autoSize", _autoSize);
		setNamespacedAttribute(request, "bean", _bean);
		setNamespacedAttribute(request, "changesContext", _changesContext);
		setNamespacedAttribute(request, "checked", _checked);
		setNamespacedAttribute(request, "checkBoxLabel", _checkBoxLabel);
		setNamespacedAttribute(request, "classPK", _classPK);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "data", _data);
		setNamespacedAttribute(request, "defaultLanguageId", _defaultLanguageId);
		setNamespacedAttribute(request, "disabled", _disabled);
		setNamespacedAttribute(request, "field", _field);
		setNamespacedAttribute(request, "fieldParam", _fieldParam);
		setNamespacedAttribute(request, "first", _first);
		setNamespacedAttribute(request, "formName", _formName);
		setNamespacedAttribute(request, "helpMessage", _helpMessage);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "ignoreRequestValue", _ignoreRequestValue);
		setNamespacedAttribute(request, "inlineField", _inlineField);
		setNamespacedAttribute(request, "inlineLabel", _inlineLabel);
		setNamespacedAttribute(request, "label", _label);
		setNamespacedAttribute(request, "languageId", _languageId);
		setNamespacedAttribute(request, "last", _last);
		setNamespacedAttribute(request, "model", _model);
		setNamespacedAttribute(request, "multiple", _multiple);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "onChange", _onChange);
		setNamespacedAttribute(request, "onClick", _onClick);
		setNamespacedAttribute(request, "placeholder", _placeholder);
		setNamespacedAttribute(request, "prefix", _prefix);
		setNamespacedAttribute(request, "required", _required);
		setNamespacedAttribute(request, "resizable", _resizable);
		setNamespacedAttribute(request, "showRequiredLabel", _showRequiredLabel);
		setNamespacedAttribute(request, "suffix", _suffix);
		setNamespacedAttribute(request, "title", _title);
		setNamespacedAttribute(request, "type", _type);
		setNamespacedAttribute(request, "useNamespace", _useNamespace);
		setNamespacedAttribute(request, "value", _value);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:input:";

	private static final String _PAGE =
		"/html/taglib/aui/input/page.jsp";

	private boolean _autoSize = false;
	private java.lang.Object _bean = null;
	private boolean _changesContext = false;
	private boolean _checked = false;
	private java.lang.String _checkBoxLabel = null;
	private long _classPK = 0;
	private java.lang.String _cssClass = null;
	private java.lang.Object _data = null;
	private java.lang.String _defaultLanguageId = null;
	private boolean _disabled = false;
	private java.lang.String _field = null;
	private java.lang.String _fieldParam = null;
	private boolean _first = false;
	private java.lang.String _formName = null;
	private java.lang.String _helpMessage = null;
	private java.lang.String _id = null;
	private boolean _ignoreRequestValue = false;
	private boolean _inlineField = false;
	private java.lang.String _inlineLabel = null;
	private java.lang.String _label = null;
	private java.lang.String _languageId = null;
	private boolean _last = false;
	private java.lang.Class<?> _model = null;
	private boolean _multiple = false;
	private java.lang.String _name = null;
	private java.lang.String _onChange = null;
	private java.lang.String _onClick = null;
	private java.lang.String _placeholder = null;
	private java.lang.String _prefix = null;
	private boolean _required = false;
	private boolean _resizable = false;
	private boolean _showRequiredLabel = true;
	private java.lang.String _suffix = null;
	private java.lang.String _title = null;
	private java.lang.String _type = null;
	private boolean _useNamespace = true;
	private java.lang.Object _value = null;

}