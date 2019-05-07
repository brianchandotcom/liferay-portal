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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.text.Format;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputFieldTag extends IncludeTag {

	public Object getBean() {
		return _bean;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getDateTogglerCheckboxLabel() {
		return _dateTogglerCheckboxLabel;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public Object getDefaultValue() {
		return _defaultValue;
	}

	public String getField() {
		return _field;
	}

	public String getFieldParam() {
		return _fieldParam;
	}

	public Format getFormat() {
		return _format;
	}

	public String getFormName() {
		return _formName;
	}

	public String getId() {
		return _id;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Class<?> getModel() {
		return _model;
	}

	public String getPlaceholder() {
		return _placeholder;
	}

	public boolean isAutoComplete() {
		return _autoComplete;
	}

	public boolean isAutoFocus() {
		return _autoFocus;
	}

	public boolean isAutoSize() {
		return _autoSize;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isIgnoreRequestValue() {
		return _ignoreRequestValue;
	}

	public void setAutoComplete(boolean autoComplete) {
		_autoComplete = autoComplete;
	}

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setAutoSize(boolean autoSize) {
		_autoSize = autoSize;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDateTogglerCheckboxLabel(String dateTogglerCheckboxLabel) {
		_dateTogglerCheckboxLabel = dateTogglerCheckboxLabel;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setDefaultValue(Object defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFieldParam(String fieldParam) {
		_fieldParam = fieldParam;
	}

	public void setFormat(Format format) {
		_format = format;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setPlaceholder(String placeholder) {
		_placeholder = placeholder;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_autoComplete = true;
		_autoFocus = false;
		_autoSize = false;
		_bean = null;
		_cssClass = null;
		_dateTogglerCheckboxLabel = null;
		_defaultLanguageId = null;
		_defaultValue = null;
		_disabled = false;
		_field = null;
		_fieldParam = null;
		_format = null;
		_formName = "fm";
		_id = null;
		_ignoreRequestValue = false;
		_languageId = null;
		_model = null;
		_placeholder = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		String fieldParam = _fieldParam;

		if (Validator.isNull(fieldParam)) {
			fieldParam = _field;
		}

		String id = _id;

		if (Validator.isNull(id)) {
			id = fieldParam;
		}

		httpServletRequest.setAttribute(
			"liferay-ui:input-field:autoComplete",
			String.valueOf(_autoComplete));
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:autoFocus", String.valueOf(_autoFocus));
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:autoSize", String.valueOf(_autoSize));
		httpServletRequest.setAttribute("liferay-ui:input-field:bean", _bean);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:cssClass", _cssClass);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:dateTogglerCheckboxLabel",
			_dateTogglerCheckboxLabel);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:defaultLanguageId", _defaultLanguageId);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:defaultValue", _defaultValue);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:disabled", String.valueOf(_disabled));
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:dynamicAttributes", getDynamicAttributes());
		httpServletRequest.setAttribute("liferay-ui:input-field:field", _field);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:fieldParam", fieldParam);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:format", _format);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:formName", _formName);
		httpServletRequest.setAttribute("liferay-ui:input-field:id", id);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:ignoreRequestValue",
			String.valueOf(_ignoreRequestValue));
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:languageId", _languageId);
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:model", _model.getName());
		httpServletRequest.setAttribute(
			"liferay-ui:input-field:placeholder", _placeholder);
	}

	private static final String _PAGE = "/html/taglib/ui/input_field/page.jsp";

	private boolean _autoComplete = true;
	private boolean _autoFocus;
	private boolean _autoSize;
	private Object _bean;
	private String _cssClass;
	private String _dateTogglerCheckboxLabel;
	private String _defaultLanguageId;
	private Object _defaultValue;
	private boolean _disabled;
	private String _field;
	private String _fieldParam;
	private Format _format;
	private String _formName = "fm";
	private String _id;
	private boolean _ignoreRequestValue;
	private String _languageId;
	private Class<?> _model;
	private String _placeholder;

}