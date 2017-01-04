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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class AlertTag extends IncludeTag {

	@Override
	public int doStartTag() {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return EVAL_BODY_INCLUDE;
	}

	public void setAnimationTime(Integer animationTime) {
		_animationTime = animationTime;
	}

	public void setCloseable(boolean closeable) {
		_closeable = closeable;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDestroyOnHide(boolean destroyOnHide) {
		_destroyOnHide = destroyOnHide;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTargetNode(String targetNode) {
		_targetNode = targetNode;
	}

	public void setTimeout(Integer timeout) {
		_timeout = timeout;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_animationTime = 500;
		_closeable = true;
		_icon = "info-circle";
		_message = null;
		_cssClass = null;
		_destroyOnHide = false;
		_targetNode = null;
		_timeout = -1;
		_title = null;
		_type = "info";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "animationTime", _animationTime);
		setNamespacedAttribute(request, "closeable", _closeable);
		setNamespacedAttribute(request, "icon", _icon);
		setNamespacedAttribute(request, "message", _message);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "destroyOnHide", _destroyOnHide);
		setNamespacedAttribute(request, "targetNode", _targetNode);
		setNamespacedAttribute(request, "timeout", _timeout);
		setNamespacedAttribute(request, "title", _title);
		setNamespacedAttribute(request, "type", _type);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "liferay-ui:alert:";

	private static final String _PAGE = "/html/taglib/ui/alert/page.jsp";

	private Integer _animationTime = 500;
	private boolean _closeable = true;
	private String _cssClass;
	private boolean _destroyOnHide;
	private String _icon = "info-circle";
	private String _message;
	private String _targetNode;
	private Integer _timeout = -1;
	private String _title;
	private String _type = "info";

}