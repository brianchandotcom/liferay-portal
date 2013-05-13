/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util.bridges.struts;

import com.liferay.portal.kernel.servlet.ServletContextProvider;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael Young
 */
public class LiferayServletContextProviderWrapper
	implements org.apache.portals.bridges.common.ServletContextProvider {

	@Override
	public HttpServletRequest getHttpServletRequest(
		GenericPortlet portlet, PortletRequest portletRequest) {

		ServletContextProvider provider = _getProvider(portlet);

		return provider.getHttpServletRequest(portlet, portletRequest);
	}

	@Override
	public HttpServletResponse getHttpServletResponse(
		GenericPortlet portlet, PortletResponse portletResponse) {

		ServletContextProvider provider = _getProvider(portlet);

		return provider.getHttpServletResponse(portlet, portletResponse);
	}

	@Override
	public ServletContext getServletContext(GenericPortlet portlet) {
		ServletContextProvider provider = _getProvider(portlet);

		return provider.getServletContext(portlet);
	}

	private ServletContextProvider _getProvider(GenericPortlet portlet) {
		PortletContext portletContext = portlet.getPortletContext();

		if (_provider == null) {
			_provider = (ServletContextProvider)portletContext.getAttribute(
				ServletContextProvider.STRUTS_BRIDGES_CONTEXT_PROVIDER);
		}

		return _provider;
	}

	private ServletContextProvider _provider;

}