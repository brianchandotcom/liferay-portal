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

import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class InputTextAreaTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultValue(String defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setParam(String param) {
		_param = param;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_defaultValue = StringPool.BLANK;
		_disabled = false;
		_param = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:input-textarea:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-textarea:defaultValue", _defaultValue);
		request.setAttribute(
			"liferay-ui:input-textarea:disabled", String.valueOf(_disabled));
		request.setAttribute("liferay-ui:input-textarea:param", _param);
		request.setAttribute(
			"liferay-ui:input-textarea:paramId",
			FriendlyURLNormalizerUtil.normalize(_param));
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_textarea/page.jsp";

	private String _cssClass;
	private String _defaultValue = StringPool.BLANK;
	private boolean _disabled;
	private String _param;

}