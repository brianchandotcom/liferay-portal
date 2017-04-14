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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.base.BaseFormTag;
import com.liferay.taglib.util.InlineUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FormTag extends BaseFormTag {

	@Override
	public String getAction() {
		return super.getAction();
	}

	public void setAction(PortletURL portletURL) {
		if (portletURL != null) {
			setAction(portletURL.toString());
		}
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkboxNames.clear();

		if (_validatorTagsMap != null) {
			for (List<ValidatorTag> validatorTags :
					_validatorTagsMap.values()) {

				for (ValidatorTag validatorTag : validatorTags) {
					validatorTag.cleanUp();
				}
			}

			_validatorTagsMap.clear();
		}
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processStartTag() throws Exception {
		String namespace = null;

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			namespace = AUIUtil.getNamespace(portletRequest, portletResponse);
		}

		if (Validator.isNull(namespace)) {
			namespace = AUIUtil.getNamespace(request);
		}

		String action = getAction();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAddSessionIdToURL()) {
			action = PortalUtil.getURLWithSessionId(
				action, themeDisplay.getSessionId());
		}

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<form action=\"");
		jspWriter.write(HtmlUtil.escapeAttribute(action));

		jspWriter.write("\" class=\"form ");
		jspWriter.write(GetterUtil.getString(getCssClass()));

		if (getInlineLabels()) {
			jspWriter.write(" field-labels-inline");
		}

		jspWriter.write("\" data-fm-namespace=\"");
		jspWriter.write(namespace);

		String escapedName = HtmlUtil.escapeAttribute(getName());

		jspWriter.write("\" id=\"");
		jspWriter.write(namespace);
		jspWriter.write(escapedName);

		jspWriter.write("\" method=\"");
		jspWriter.write(getMethod());

		jspWriter.write("\" name=\"");
		jspWriter.write(namespace);
		jspWriter.write(escapedName);
		jspWriter.write("\" ");

		jspWriter.write(
			InlineUtil.buildDynamicAttributes(getDynamicAttributes()));

		jspWriter.write(">");

		if (Validator.isNotNull(getOnSubmit())) {
			jspWriter.write(
				"<fieldset class=\"input-container\" disabled=\"disabled\">");
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("aui:form:validatorTagsMap", _validatorTagsMap);
		request.setAttribute(
			"LIFERAY_SHARED_aui:form:checkboxNames", _checkboxNames);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private final List<String> _checkboxNames = new ArrayList<>();
	private final Map<String, List<ValidatorTag>> _validatorTagsMap =
		new HashMap<>();

}