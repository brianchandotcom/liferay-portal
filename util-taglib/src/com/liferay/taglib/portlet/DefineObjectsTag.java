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

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.function.Function;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		String lifecycle = (String)httpServletRequest.getAttribute(
			PortletRequest.LIFECYCLE_PHASE);

		PortletConfig portletConfig =
			(PortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig != null) {
			pageContext.setAttribute("portletConfig", portletConfig);
			pageContext.setAttribute(
				"portletName", portletConfig.getPortletName());
		}

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			pageContext.setAttribute(
				"liferayPortletRequest",
				PortalUtil.getLiferayPortletRequest(portletRequest));

			if (lifecycle != null) {
				String portletRequestAttrName = null;

				if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
					portletRequestAttrName = "actionRequest";
				}
				else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
					portletRequestAttrName = "eventRequest";
				}
				else if (lifecycle.equals(PortletRequest.HEADER_PHASE)) {
					portletRequestAttrName = "headerRequest";
				}
				else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					portletRequestAttrName = "renderRequest";
				}
				else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					portletRequestAttrName = "resourceRequest";
				}

				pageContext.setAttribute(
					portletRequestAttrName, portletRequest);
			}

			PortletPreferences portletPreferences =
				portletRequest.getPreferences();

			pageContext.setAttribute("portletPreferences", portletPreferences);
			pageContext.setAttribute(
				"portletPreferencesValues",
				_mapProxyProviderFunction.apply(
					new PortletPreferencesValuesInvocationHandler(
						portletPreferences)));

			PortletSession portletSession = portletRequest.getPortletSession();

			pageContext.setAttribute("portletSession", portletSession);

			try {
				pageContext.setAttribute(
					"portletSessionScope", portletSession.getAttributeMap());
			}
			catch (IllegalStateException ise) {
			}
		}

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			return SKIP_BODY;
		}

		pageContext.setAttribute(
			"liferayPortletResponse",
			PortalUtil.getLiferayPortletResponse(portletResponse));

		if (lifecycle != null) {
			String portletResponseAttrName = null;

			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				portletResponseAttrName = "actionResponse";
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				portletResponseAttrName = "eventResponse";
			}
			else if (lifecycle.equals(PortletRequest.HEADER_PHASE)) {
				portletResponseAttrName = "headerResponse";
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponseAttrName = "renderResponse";
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				portletResponseAttrName = "resourceResponse";
			}

			pageContext.setAttribute(portletResponseAttrName, portletResponse);
		}

		return SKIP_BODY;
	}

	private static final Function<InvocationHandler, Map<?, ?>>
		_mapProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			Map.class);

	private static class PortletPreferencesValuesInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws ReflectiveOperationException {

			if (_map == null) {
				_map = _portletPreferences.getMap();
			}

			return method.invoke(_map, args);
		}

		private PortletPreferencesValuesInvocationHandler(
			PortletPreferences portletPreferences) {

			_portletPreferences = portletPreferences;
		}

		private Map<String, String[]> _map;
		private final PortletPreferences _portletPreferences;

	}

}