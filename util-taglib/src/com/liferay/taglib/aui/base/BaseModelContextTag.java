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

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public class BaseModelContextTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.Object getBean() {
		return _bean;
	}

	public java.lang.String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public java.lang.Class<?> getModel() {
		return _model;
	}

	public void setBean(java.lang.Object bean) {
		_bean = bean;

		setScopedAttribute("bean", bean);
	}

	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;

		setScopedAttribute("defaultLanguageId", defaultLanguageId);
	}

	public void setModel(java.lang.Class<?> model) {
		_model = model;

		setScopedAttribute("model", model);
	}

	@Override
	protected void cleanUp() {
		_bean = null;
		_defaultLanguageId = null;
		_model = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:model-context:";

	private static final String _PAGE =
		"/html/taglib/aui/model_context/page.jsp";

	private java.lang.Object _bean = null;
	private java.lang.String _defaultLanguageId = null;
	private java.lang.Class<?> _model = null;

}