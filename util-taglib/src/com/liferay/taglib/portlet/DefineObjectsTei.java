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

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineObjectsTei extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		return Concealer._variableInfo;
	}

	private static class Concealer {

		private static final VariableInfo[] _variableInfo = {
			new VariableInfo(
				"actionRequest", ActionRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"actionResponse", ActionResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"eventRequest", EventRequest.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"eventResponse", EventResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"liferayPortletRequest", LiferayPortletRequest.class.getName(),
				true, VariableInfo.AT_END),
			new VariableInfo(
				"liferayPortletResponse",
				LiferayPortletResponse.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletConfig", PortletConfig.class.getName(), true,
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
				"portletSession", PortletSession.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"portletSessionScope", Map.class.getName(), true,
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
				VariableInfo.AT_END)
		};

	}

}