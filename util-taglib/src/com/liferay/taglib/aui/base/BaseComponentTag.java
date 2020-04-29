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
public abstract class BaseComponentTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getDefineVar() {
		return _defineVar;
	}

	public java.lang.String getExcludeAttributes() {
		return _excludeAttributes;
	}

	public java.lang.String getJavaScriptAttributes() {
		return _javaScriptAttributes;
	}

	public java.lang.String getModule() {
		return _module;
	}

	public java.lang.String getName() {
		return _name;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getOptions() {
		return _options;
	}

	public java.lang.String getScriptPosition() {
		return _scriptPosition;
	}

	public javax.servlet.jsp.JspContext getTagPageContext() {
		return _tagPageContext;
	}

	public java.io.Serializable getUseJavaScript() {
		return _useJavaScript;
	}

	public java.lang.String getVar() {
		return _var;
	}

	public void setDefineVar(boolean defineVar) {
		_defineVar = defineVar;
	}

	public void setExcludeAttributes(java.lang.String excludeAttributes) {
		_excludeAttributes = excludeAttributes;
	}

	public void setJavaScriptAttributes(java.lang.String javaScriptAttributes) {
		_javaScriptAttributes = javaScriptAttributes;
	}

	public void setModule(java.lang.String module) {
		_module = module;
	}

	public void setName(java.lang.String name) {
		_name = name;
	}

	public void setOptions(java.util.Map<java.lang.String, java.lang.Object> options) {
		_options = options;
	}

	public void setScriptPosition(java.lang.String scriptPosition) {
		_scriptPosition = scriptPosition;
	}

	public void setTagPageContext(javax.servlet.jsp.JspContext tagPageContext) {
		_tagPageContext = tagPageContext;
	}

	public void setUseJavaScript(java.io.Serializable useJavaScript) {
		_useJavaScript = useJavaScript;
	}

	public void setVar(java.lang.String var) {
		_var = var;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_defineVar = true;
		_excludeAttributes = null;
		_javaScriptAttributes = null;
		_module = null;
		_name = null;
		_options = null;
		_scriptPosition = null;
		_tagPageContext = null;
		_useJavaScript = true;
		_var = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "defineVar", _defineVar);
		setNamespacedAttribute(request, "excludeAttributes", _excludeAttributes);
		setNamespacedAttribute(request, "javaScriptAttributes", _javaScriptAttributes);
		setNamespacedAttribute(request, "module", _module);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "options", _options);
		setNamespacedAttribute(request, "scriptPosition", _scriptPosition);
		setNamespacedAttribute(request, "tagPageContext", _tagPageContext);
		setNamespacedAttribute(request, "useJavaScript", _useJavaScript);
		setNamespacedAttribute(request, "var", _var);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:component:";

	private static final String _PAGE =
		"/html/taglib/aui/component/page.jsp";

	private boolean _defineVar = true;
	private java.lang.String _excludeAttributes = null;
	private java.lang.String _javaScriptAttributes = null;
	private java.lang.String _module = null;
	private java.lang.String _name = null;
	private java.util.Map<java.lang.String, java.lang.Object> _options = null;
	private java.lang.String _scriptPosition = null;
	private javax.servlet.jsp.JspContext _tagPageContext = null;
	private java.io.Serializable _useJavaScript = true;
	private java.lang.String _var = null;

}