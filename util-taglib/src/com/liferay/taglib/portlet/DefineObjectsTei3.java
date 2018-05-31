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

package com.liferay.taglib.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ClientDataRequest;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.MimeResponse;
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceParameters;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;

import javax.servlet.http.Cookie;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Neil Griffin
 */
public class DefineObjectsTei3 extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		return Concealer._variableInfo;
	}

	private static class Concealer {

		private static final VariableInfo[] _variableInfo = {
			new VariableInfo(
				"actionParams", ActionParameters.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"actionRequest", ActionRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"actionResponse", ActionResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"clientDataRequest", ClientDataRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"contextPath", String.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"cookies", Cookie[].class.getCanonicalName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"eventRequest", EventRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"eventResponse", EventResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"headerRequest", HeaderRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"headerResponse", HeaderResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"liferayPortletRequest", LiferayPortletRequest.class.getName(),
				true, VariableInfo.AT_END),
			new VariableInfo(
				"liferayPortletResponse",
				LiferayPortletResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"locale", Locale.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"locales", Locale[].class.getCanonicalName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"mimeResponse", MimeResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"mutableRenderParams", MutableRenderParameters.class.getName(),
				true, VariableInfo.AT_END),
			new VariableInfo(
				"namespace", String.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"portletConfig", PortletConfig.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletContext", PortletContext.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletMode", PortletMode.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletName", String.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletPreferences", PortletPreferences.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletPreferencesValues", Map.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletRequest", PortletRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletResponse", PortletResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletSession", PortletSession.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletSessionScope", Map.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"renderParams", RenderParameters.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"renderRequest", RenderRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"renderResponse", RenderResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"resourceRequest", ResourceRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"resourceResponse", ResourceResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"resourceParams", ResourceParameters.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"stateAwareResponse", StateAwareResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"windowId", String.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"windowState", WindowState.class.getName(), true,
				VariableInfo.AT_END)
		};

	}

}