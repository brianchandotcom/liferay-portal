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
public class BaseWorkflowStatusTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.Object getBean() {
		return _bean;
	}

	public java.lang.String getHelpMessage() {
		return _helpMessage;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.Class<?> getModel() {
		return _model;
	}

	public boolean getShowIcon() {
		return _showIcon;
	}

	public boolean getShowLabel() {
		return _showLabel;
	}

	public java.lang.Integer getStatus() {
		return _status;
	}

	public java.lang.String getStatusMessage() {
		return _statusMessage;
	}

	public java.lang.String getVersion() {
		return _version;
	}

	public void setBean(java.lang.Object bean) {
		_bean = bean;

		setScopedAttribute("bean", bean);
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		_helpMessage = helpMessage;

		setScopedAttribute("helpMessage", helpMessage);
	}

	public void setId(java.lang.String id) {
		_id = id;

		setScopedAttribute("id", id);
	}

	public void setModel(java.lang.Class<?> model) {
		_model = model;

		setScopedAttribute("model", model);
	}

	public void setShowIcon(boolean showIcon) {
		_showIcon = showIcon;

		setScopedAttribute("showIcon", showIcon);
	}

	public void setShowLabel(boolean showLabel) {
		_showLabel = showLabel;

		setScopedAttribute("showLabel", showLabel);
	}

	public void setStatus(java.lang.Integer status) {
		_status = status;

		setScopedAttribute("status", status);
	}

	public void setStatusMessage(java.lang.String statusMessage) {
		_statusMessage = statusMessage;

		setScopedAttribute("statusMessage", statusMessage);
	}

	public void setVersion(java.lang.String version) {
		_version = version;

		setScopedAttribute("version", version);
	}

	@Override
	protected void cleanUp() {
		_bean = null;
		_helpMessage = null;
		_id = null;
		_model = null;
		_showIcon = true;
		_showLabel = true;
		_status = null;
		_statusMessage = null;
		_version = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "bean", _bean);
		setNamespacedAttribute(request, "helpMessage", _helpMessage);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "model", _model);
		setNamespacedAttribute(request, "showIcon", _showIcon);
		setNamespacedAttribute(request, "showLabel", _showLabel);
		setNamespacedAttribute(request, "status", _status);
		setNamespacedAttribute(request, "statusMessage", _statusMessage);
		setNamespacedAttribute(request, "version", _version);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:workflow-status:";

	private static final String _PAGE =
		"/html/taglib/aui/workflow_status/page.jsp";

	private java.lang.Object _bean = null;
	private java.lang.String _helpMessage = null;
	private java.lang.String _id = null;
	private java.lang.Class<?> _model = null;
	private boolean _showIcon = true;
	private boolean _showLabel = true;
	private java.lang.Integer _status = null;
	private java.lang.String _statusMessage = null;
	private java.lang.String _version = null;

}