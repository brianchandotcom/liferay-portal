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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 */
public class InputPermissionsTag extends IncludeTag {

	public static String doTag(
			String formName, String modelName, PageContext pageContext)
		throws Exception {

		return doTag(_PAGE, formName, modelName, false, pageContext);
	}

	public static String doTag(
			String page, String formName, String modelName, boolean reverse,
			PageContext pageContext)
		throws Exception {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:formName", formName);
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:groupDefaultActions",
			ResourceActionsUtil.getModelResourceGroupDefaultActions(modelName));
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:guestDefaultActions",
			ResourceActionsUtil.getModelResourceGuestDefaultActions(modelName));
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:guestUnsupportedActions",
			ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
				modelName));
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:modelName", modelName);
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:reverse", reverse);
		httpServletRequest.setAttribute(
			"liferay-ui:input-permissions:supportedActions",
			ResourceActionsUtil.getModelResourceActions(modelName));

		PortalIncludeUtil.include(pageContext, page);

		return StringPool.BLANK;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(getPage(), _formName, _modelName, _reverse, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getFormName() {
		return _formName;
	}

	public String getModelName() {
		return _modelName;
	}

	public boolean isReverse() {
		return _reverse;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	public void setReverse(boolean reverse) {
		_reverse = reverse;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_permissions/page.jsp";

	private String _formName = "fm";
	private String _modelName;
	private boolean _reverse;

}