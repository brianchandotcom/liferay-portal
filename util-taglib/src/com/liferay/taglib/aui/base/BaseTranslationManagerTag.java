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
public abstract class BaseTranslationManagerTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.util.Locale[] getAvailableLocales() {
		return _availableLocales;
	}

	public boolean getChangeableDefaultLanguage() {
		return _changeableDefaultLanguage;
	}

	public java.lang.String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public java.lang.String getEditingLanguageId() {
		return _editingLanguageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public java.lang.String getId() {
		return _id;
	}

	public boolean getInitialize() {
		return _initialize;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public void setAvailableLocales(java.util.Locale[] availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setChangeableDefaultLanguage(boolean changeableDefaultLanguage) {
		_changeableDefaultLanguage = changeableDefaultLanguage;
	}

	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setEditingLanguageId(java.lang.String editingLanguageId) {
		_editingLanguageId = editingLanguageId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setInitialize(boolean initialize) {
		_initialize = initialize;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_availableLocales = null;
		_changeableDefaultLanguage = true;
		_defaultLanguageId = null;
		_editingLanguageId = null;
		_groupId = 0;
		_id = null;
		_initialize = true;
		_readOnly = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "availableLocales", _availableLocales);
		setNamespacedAttribute(request, "changeableDefaultLanguage", _changeableDefaultLanguage);
		setNamespacedAttribute(request, "defaultLanguageId", _defaultLanguageId);
		setNamespacedAttribute(request, "editingLanguageId", _editingLanguageId);
		setNamespacedAttribute(request, "groupId", _groupId);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "initialize", _initialize);
		setNamespacedAttribute(request, "readOnly", _readOnly);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:translation-manager:";

	private static final String _PAGE =
		"/html/taglib/aui/translation_manager/page.jsp";

	private java.util.Locale[] _availableLocales = null;
	private boolean _changeableDefaultLanguage = true;
	private java.lang.String _defaultLanguageId = null;
	private java.lang.String _editingLanguageId = null;
	private long _groupId = 0;
	private java.lang.String _id = null;
	private boolean _initialize = true;
	private boolean _readOnly = false;

}