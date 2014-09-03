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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsURLTag extends TagSupport {

	/**
	 * Create a {@link PortletURL} to be able to configure the permissions of a
	 * resource.
	 * @param redirect 
	 * 		the redirect parameter (if it is null and the dialog is not open as 
	 * 		a popup, the current URL as obtained by 
	 * 		{@link PortalUtil#getCurrentURL(HttpServletRequest)} is used
	 * @param modelResource 
	 * 		a String describing the class of the resource for which we want to 
	 * 		configure permissions
	 * @param modelResourceDescription the description of the resource
	 * @param resourceGroupId 
	 * 		the group id to which the resource belongs (if it is null, it is 
	 * 		obtained from {@link ThemeDisplay#getScopeGroupId()})
	 * @param resourcePrimKey the primary key of the resource
	 * @param windowState 
	 * 		the window state when opening the permissions configuration dialog 
	 * 		(see {@link LiferayWindowState})
	 * @param roleTypes
	 * @param request the current request
	 * @return 
	 * 		the {@link PortletURL} to be used to open the permissions 
	 * 		configuration dialog
	 * @see LiferayWindowState
	 */
	public static PortletURL createURL(
		String redirect, String modelResource, String modelResourceDescription,
		Long resourceGroupId, String resourcePrimKey, String windowState,
		int[] roleTypes, HttpServletRequest request) {

		try {
			return _doTag(
				redirect, modelResourceDescription, modelResourceDescription,
				resourceGroupId, resourcePrimKey, windowState, roleTypes,
				request);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to create permissions portlet URL", e);
		}
	}

	/**
	 * Renders or stores in a JSP variable a URL to be able to configure the 
	 * permissions of a resource.
	 * @param redirect 
	 * 		the redirect parameter (if it is null and the dialog is not open as 
	 * 		a popup, the current URL as obtained by 
	 * 		{@link PortalUtil#getCurrentURL(HttpServletRequest)} is used
	 * @param modelResource 
	 * 		a String describing the class of the resource for which we want to 
	 * 		configure permissions
	 * @param modelResourceDescription the description of the resource
	 * @param resourceGroupId 
	 * 		the group id to which the resource belongs (if it is null, it is 
	 * 		obtained from {@link ThemeDisplay#getScopeGroupId()})
	 * @param resourcePrimKey the primary key of the resource
	 * @param windowState 
	 * 		the window state when opening the permissions configuration dialog 
	 * 		(see {@link LiferayWindowState})
	 * @param var 
	 * 		the name of a JSP variable where the resulting URL must be stored,
	 * 		or null to render the URL using the writer of the given page context
	 * @param roleTypes 
	 * @param pageContext the current JSP context
	 * @return 
	 * 		the {@link PortletURL} to be used to open the permissions 
	 * 		configuration dialog
	 * @see LiferayWindowState
	 * @throws Exception
	 */
	public static void doTag(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, String var,
			int[] roleTypes, PageContext pageContext)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletURL portletURL = _doTag(
			redirect, modelResource, modelResourceDescription, resourceGroupId,
			resourcePrimKey, windowState, roleTypes, request);

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

	private static PortletURL _doTag(
			String redirect, String modelResource,
			String modelResourceDescription, Object resourceGroupId,
			String resourcePrimKey, String windowState, int[] roleTypes,
			HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (resourceGroupId instanceof Number) {
			Number resourceGroupIdNumber = (Number)resourceGroupId;

			if (resourceGroupIdNumber.longValue() < 0) {
				resourceGroupId = null;
			}
		}
		else if (resourceGroupId instanceof String) {
			String esourceGroupIdString = (String)resourceGroupId;

			if (esourceGroupIdString.length() == 0) {
				resourceGroupId = null;
			}
		}

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

	private String _modelResource;
	private String _modelResourceDescription;
	private String _redirect;
	private Object _resourceGroupId;
	private String _resourcePrimKey;
	private int[] _roleTypes;
	private String _var;
	private String _windowState;

}