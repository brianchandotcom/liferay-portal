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

package com.liferay.util.bridges.bsf;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseBSFPortlet extends GenericPortlet {

	@Override
	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String file = renderRequest.getParameter(getFileParam());

		if (file != null) {
			include(file, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			include(editFile, renderRequest, renderResponse);
		}
	}

	@Override
	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		include(helpFile, renderRequest, renderResponse);
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		include(viewFile, renderRequest, renderResponse);
	}

	@Override
	public void init() {
		editFile = getInitParameter("edit-file");
		helpFile = getInitParameter("help-file");
		viewFile = getInitParameter("view-file");
		actionFile = getInitParameter("action-file");
		resourceFile = getInitParameter("resource-file");
		globalFiles = StringUtil.split(getInitParameter("global-files"));

		BSFManager.registerScriptingEngine(
			getScriptingEngineLanguage(), getScriptingEngineClassName(),
			new String[] {getScriptingEngineExtension()});

		bsfManager = new BSFManager();
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		include(actionFile, actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		include(resourceFile, resourceRequest, resourceResponse);
	}

	protected void declareBeans(
			InputStream is, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws BSFException, IOException {

		declareBeans(
			new String(FileUtil.getBytes(is)), portletRequest, portletResponse);
	}

	protected void declareBeans(
			String code, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws BSFException, IOException {

		String script = getGlobalScript().concat(code);

		PortletConfig portletConfig = getPortletConfig();
		PortletContext portletContext = getPortletContext();
		PortletPreferences preferences = portletRequest.getPreferences();
		Map<String, String> userInfo =
			(Map<String, String>)portletRequest.getAttribute(
				PortletRequest.USER_INFO);

		bsfManager.declareBean(
			"portletConfig", portletConfig, PortletConfig.class);
		bsfManager.declareBean(
			"portletContext", portletContext, PortletContext.class);
		bsfManager.declareBean(
			"preferences", preferences, PortletPreferences.class);
		bsfManager.declareBean("userInfo", userInfo, Map.class);

		if (portletRequest instanceof ActionRequest) {
			bsfManager.declareBean(
				"actionRequest", portletRequest, ActionRequest.class);
		}
		else if (portletRequest instanceof RenderRequest) {
			bsfManager.declareBean(
				"renderRequest", portletRequest, RenderRequest.class);
		}
		else if (portletRequest instanceof ResourceRequest) {
			bsfManager.declareBean(
				"resourceRequest", portletRequest, ResourceRequest.class);
		}

		if (portletResponse instanceof ActionResponse) {
			bsfManager.declareBean(
				"actionResponse", portletResponse, ActionResponse.class);
		}
		else if (portletResponse instanceof RenderResponse) {
			bsfManager.declareBean(
				"renderResponse", portletResponse, RenderResponse.class);
		}
		else if (portletResponse instanceof ResourceResponse) {
			bsfManager.declareBean(
				"resourceResponse", portletResponse, ResourceResponse.class);
		}

		bsfManager.exec(getScriptingEngineLanguage(), "(java)", 1, 1, script);
	}

	protected abstract String getFileParam();

	protected String getGlobalScript() throws IOException {
		if (globalFiles.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < globalFiles.length; i++) {
			PortletContext portletContext = getPortletContext();

			InputStream inputStream = portletContext.getResourceAsStream(
				globalFiles[i]);

			if (inputStream == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Global file " + globalFiles[i] + " does not exist");
				}
			}

			if (inputStream != null) {
				sb.append(new String(FileUtil.getBytes(inputStream)));
				sb.append(StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	protected abstract String getScriptingEngineClassName();

	protected abstract String getScriptingEngineExtension();

	protected abstract String getScriptingEngineLanguage();

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException {

		PortletContext portletContext = getPortletContext();

		InputStream inputStream = portletContext.getResourceAsStream(path);

		if (inputStream == null) {
			_log.error(
				StringBundler.concat(
					path, " is not a valid ", getScriptingEngineLanguage(),
					" file"));

			return;
		}

		try {
			declareBeans(inputStream, portletRequest, portletResponse);
		}
		catch (BSFException bsfe) {
			logBSFException(bsfe, path);
		}
		finally {
			inputStream.close();
		}
	}

	protected void logBSFException(BSFException bsfe, String path) {
		String message =
			"The script at " + path + " or one of the global files has errors.";

		Throwable t = bsfe.getTargetException();

		_log.error(message, t);
	}

	protected String actionFile;
	protected BSFManager bsfManager;
	protected String editFile;
	protected String[] globalFiles;
	protected String helpFile;
	protected String resourceFile;
	protected String viewFile;

	private static final Log _log = LogFactoryUtil.getLog(BaseBSFPortlet.class);

}