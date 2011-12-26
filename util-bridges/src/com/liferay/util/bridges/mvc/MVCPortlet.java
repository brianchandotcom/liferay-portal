/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.util.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MVCPortlet extends LiferayPortlet {

	@Override
	public void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(aboutJSP, renderRequest, renderResponse);
	}

	@Override
	public void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(configJSP, renderRequest, renderResponse);
	}

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editJSP, renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editDefaultsJSP, renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editGuestJSP, renderRequest, renderResponse);
		}
	}

	@Override
	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(helpJSP, renderRequest, renderResponse);
	}

	@Override
	public void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(previewJSP, renderRequest, renderResponse);
	}

	@Override
	public void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(printJSP, renderRequest, renderResponse);
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(viewJSP, renderRequest, renderResponse);
	}

	public String getTemplateToken() {
		return _TEMPLATE_TOKEN;
	}

	@Override
	public void init() throws PortletException {
		super.init();

		jspPath = getInitParameter(getTemplateToken() + "-path");

		if (Validator.isNull(jspPath)) {
			jspPath = StringPool.SLASH;
		}
		else if (jspPath.contains(StringPool.BACK_SLASH) ||
				 jspPath.contains(StringPool.DOUBLE_SLASH) ||
				 jspPath.contains(StringPool.PERIOD) ||
				 jspPath.contains(StringPool.SPACE)) {

			throw new PortletException(
				getTemplateToken() + "-path " + jspPath +
					" has invalid characters");
		}
		else if (!jspPath.startsWith(StringPool.SLASH) ||
				 !jspPath.endsWith(StringPool.SLASH)) {

			throw new PortletException(
				getTemplateToken() + "-path " + jspPath +
					" must start and end with a /");
		}

		aboutJSP = getInitParameter("about-" + getTemplateToken());
		configJSP = getInitParameter("config-" + getTemplateToken());
		editJSP = getInitParameter("edit-" + getTemplateToken());
		editDefaultsJSP = getInitParameter(
			"edit-defaults-" + getTemplateToken());
		editGuestJSP = getInitParameter("edit-guest-" + getTemplateToken());
		helpJSP = getInitParameter("help-" + getTemplateToken());
		previewJSP = getInitParameter("preview-" + getTemplateToken());
		printJSP = getInitParameter("print-" + getTemplateToken());
		viewJSP = getInitParameter("view-" + getTemplateToken());

		clearRequestParameters = GetterUtil.getBoolean(
			getInitParameter("clear-request-parameters"));
		copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"));

		String packagePrefix = getInitParameter(
			ActionCommandCache.ACTION_PACKAGE_NAME);

		if (Validator.isNotNull(packagePrefix)) {
			_actionCommandCache = new ActionCommandCache(packagePrefix);
		}
	}

	public void invokeTaglibDiscussion(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletConfig portletConfig = getPortletConfig();

		PortalClassInvoker.invoke(
			true,
			"com.liferay.portlet.messageboards.action.EditDiscussionAction",
			"processAction",
			new String[] {
				"org.apache.struts.action.ActionMapping",
				"org.apache.struts.action.ActionForm",
				PortletConfig.class.getName(), ActionRequest.class.getName(),
				ActionResponse.class.getName()
			},
			null, null, portletConfig, actionRequest, actionResponse);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		if (copyRequestParameters) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String jspPage = resourceRequest.getParameter(
			getTemplateToken() + "Page");

		if (jspPage != null) {
			include(
				jspPage, resourceRequest, resourceResponse,
				PortletRequest.RESOURCE_PHASE);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Override
	protected boolean callActionMethod(
			ActionRequest request, ActionResponse response)
		throws PortletException {

		if (_actionCommandCache == null) {
			return super.callActionMethod(request, response);
		}

		String actionName = ParamUtil.getString(
			request, ActionRequest.ACTION_NAME);

		if (!actionName.contains(StringPool.COMMA)) {
			ActionCommand actionCommand = _actionCommandCache.getActionCommand(
				actionName);

			if (actionCommand != ActionCommandCache.EMPTY) {
				return actionCommand.processCommand(request, response);
			}
		}
		else {
			List<ActionCommand> actionCommands =
				_actionCommandCache.getActionCommandChain(actionName);

			if (actionCommands.isEmpty()) {
				return false;
			}

			for (ActionCommand actionCommand : actionCommands) {
				if (!actionCommand.processCommand(request, response)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected void checkJSPPath(String path) throws PortletException {
		if (!path.startsWith(jspPath) ||
			path.contains(StringPool.DOUBLE_PERIOD) ||
			!PortalUtil.isValidResourceId(path)) {

			throw new PortletException(
				"Path " + path + " is not accessible by this portlet");
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String jspPage = renderRequest.getParameter(
			getTemplateToken() + "Page");

		if (jspPage != null) {
			if (!isProcessRenderRequest(renderRequest)) {
				renderRequest.setAttribute(
					WebKeys.PORTLET_DECORATE, Boolean.FALSE);

				return;
			}

			WindowState windowState = renderRequest.getWindowState();

			if (windowState.equals(WindowState.MINIMIZED)) {
				return;
			}

			include(jspPage, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected void include(
			String path, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException, PortletException {

		include(
			path, actionRequest, actionResponse, PortletRequest.ACTION_PHASE);
	}

	protected void include(
			String path, EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		include(path, eventRequest, eventResponse, PortletRequest.EVENT_PHASE);
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			checkJSPPath(path);

			portletRequestDispatcher.include(portletRequest, portletResponse);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty("clear-request-parameters", "true");
			}
		}
	}

	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException {

		include(
			path, renderRequest, renderResponse, PortletRequest.RENDER_PHASE);
	}

	protected void include(
			String path, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		include(
			path, resourceRequest, resourceResponse,
			PortletRequest.RESOURCE_PHASE);
	}

	private static Log _log = LogFactoryUtil.getLog(MVCPortlet.class);

	protected static String _TEMPLATE_TOKEN = "jsp";

	protected ActionCommandCache _actionCommandCache;

	protected String aboutJSP;
	protected boolean clearRequestParameters;
	protected String configJSP;
	protected boolean copyRequestParameters;
	protected String editDefaultsJSP;
	protected String editGuestJSP;
	protected String editJSP;
	protected String helpJSP;
	protected String jspPath;
	protected String previewJSP;
	protected String printJSP;
	protected String viewJSP;

}