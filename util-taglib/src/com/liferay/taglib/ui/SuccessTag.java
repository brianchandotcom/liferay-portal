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
 * @author Brian Wing Shun Chan
 */
public class SuccessTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTargetNode(String targetNode) {
		_targetNode = targetNode;
	}

	public void setTranslateMessage(boolean translateMessage) {
		_translateMessage = translateMessage;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:success:key", _key);
		request.setAttribute("liferay-ui:success:message", _message);
		request.setAttribute("liferay-ui:success:targetNode", _targetNode);
		request.setAttribute(
			"liferay-ui:success:translateMessage",
			String.valueOf(_translateMessage));
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/success/page.jsp";

	private String _key;
	private String _message;
	private String _targetNode;
	private boolean _translateMessage = true;

}