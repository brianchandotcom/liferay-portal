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

package com.liferay.taglib.security;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsURLTag extends TagSupport {

	public static PortletURL createURL(
		String redirect, String modelResource, String modelResourceDescription,
		String resourceGroupId, String resourcePrimKey, String windowState,
		int[] roleTypes, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (resourceGroupId == null) {
			resourceGroupId = String.valueOf(themeDisplay.getScopeGroupId());
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		Layout layout = themeDisplay.getLayout();

		if (Validator.isNull(redirect) &&
			(Validator.isNull(windowState) ||
			 !windowState.equals(LiferayWindowState.POP_UP.toString()))) {

			redirect = PortalUtil.getCurrentURL(request);
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.PORTLET_CONFIGURATION, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		try {
			if (Validator.isNotNull(windowState)) {
				portletURL.setWindowState(
					WindowStateFactory.getWindowState(windowState));
			}
			else if (themeDisplay.isStatePopUp()) {
				portletURL.setWindowState(LiferayWindowState.POP_UP);
			}
			else {
				portletURL.setWindowState(WindowState.MAXIMIZED);
			}
		}
		catch (WindowStateException wse) {
			throw new SystemException(
				"Unable to create permissions portlet URL", wse);
		}

		portletURL.setParameter(
			"struts_action", "/portlet_configuration/edit_permissions");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);

			if (!themeDisplay.isStateMaximized()) {
				portletURL.setParameter("returnToFullPageURL", redirect);
			}
		}

		portletURL.setParameter("portletResource", portletDisplay.getId());
		portletURL.setParameter("modelResource", modelResource);
		portletURL.setParameter(
			"modelResourceDescription", modelResourceDescription);
		portletURL.setParameter(
			"resourceGroupId", String.valueOf(resourceGroupId));
		portletURL.setParameter("resourcePrimKey", resourcePrimKey);

		if (roleTypes != null) {
			portletURL.setParameter("roleTypes", StringUtil.merge(roleTypes));
		}

		return portletURL;
	}

	public static void doTag(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, String var,
			int[] roleTypes, PageContext pageContext)
		throws Exception {

		String resourceGroupIdString = null;

		if (resourceGroupId instanceof Number) {
			Number resourceGroupIdNumber = (Number)resourceGroupId;

			if (resourceGroupIdNumber.longValue() < 0) {
				resourceGroupIdString = null;
			}
			else {
				resourceGroupIdString = resourceGroupIdNumber.toString();
			}
		}
		else if (resourceGroupId instanceof String) {
			resourceGroupIdString = (String)resourceGroupId;

			if (resourceGroupIdString.length() == 0) {
				resourceGroupIdString = null;
			}
		}

		PortletURL portletURL = createURL(
			redirect, modelResourceDescription, modelResourceDescription,
			resourceGroupIdString, resourcePrimKey, windowState, roleTypes,
			(HttpServletRequest)pageContext.getRequest());

		String portletURLToString = portletURL.toString();

		if (Validator.isNotNull(var)) {
			pageContext.setAttribute(var, portletURLToString);
		}
		else {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(portletURLToString);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(
				_redirect, _modelResource, _modelResourceDescription,
				_resourceGroupId, _resourcePrimKey, _windowState, _var,
				_roleTypes, pageContext);
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public void setModelResource(String modelResource) {
		_modelResource = modelResource;
	}

	public void setModelResourceDescription(String modelResourceDescription) {
		_modelResourceDescription = modelResourceDescription;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setResourceGroupId(Object resourceGroupId) {
		_resourceGroupId = resourceGroupId;
	}

	public void setResourcePrimKey(String resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public void setRoleTypes(int[] roleTypes) {
		_roleTypes = roleTypes;
	}

	public void setVar(String var) {
		_var = var;
	}

	public void setWindowState(String windowState) {
		_windowState = windowState;
	}

	private String _modelResource;
	private String _modelResourceDescription;
	private String _redirect;
	private Object _resourceGroupId;
	private String _resourcePrimKey;
	private int[] _roleTypes;
	private String _var;
	private String _windowState;

}