/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.util.freemarker;

import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateTaglibSupportProvider;
import com.liferay.portal.util.PortalUtil;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class FreeMarkerTaglibSupportProvider
	implements TemplateTaglibSupportProvider {

	public void addTaglibSupport(
			Template template, String servletContextName,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		template.prepare(request);

		template.put(
			"fullTemplatesPath", servletContextName.concat(
				TemplateResource.SERVLET_SEPARATOR));

		TemplateHashModel taglibsFactory =
			FreeMarkerTaglibFactoryUtil.createTaglibFactory(servletContext);

		template.put("PortletJspTagLibs", taglibsFactory);

		ServletConfig servletConfig =
			(ServletConfig)portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_CONFIG);

		final PortletServlet portletServlet = new PortletServlet();

		portletServlet.init(servletConfig);

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				portletServlet, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Request", httpRequestHashModel);
	}

	private static final String _SERVLET_SEPARATOR = "_SERVLET_CONTEXT_";

}