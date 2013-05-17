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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThemeHelper;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.freemarker.FreeMarkerTaglibFactoryUtil;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 * @author Mika Koivisto
 * @author Shuyang Zhou
 */
public class ThemeUtil {

	public static String getPortletId(HttpServletRequest request) {
		String portletId = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			portletId = portletDisplay.getId();
		}

		return portletId;
	}

	public static void include(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext, String path,
			Theme theme)
		throws Exception {

		String extension = theme.getTemplateExtension();

		if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_FTL)) {
			includeFTL(servletContext, request, pageContext, path, theme, true);
		}
		else if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_VM)) {
			includeVM(servletContext, request, pageContext, path, theme, true);
		}
		else {
			path = theme.getTemplatesPath() + StringPool.SLASH + path;

			includeJSP(servletContext, request, response, path, theme);
		}
	}

	public static String includeFTL(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String path, Theme theme, boolean write)
		throws Exception {

		return doDispatch(
			servletContext, request, null, pageContext, path, theme, write,
			ThemeHelper.TEMPLATE_EXTENSION_FTL);
	}

	public static void includeJSP(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path, Theme theme)
		throws Exception {

		doDispatch(
			servletContext, request, response, null, path, theme, true,
			ThemeHelper.TEMPLATE_EXTENSION_JSP);
	}

	public static String includeVM(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String path, Theme theme, boolean write)
		throws Exception {

		return doDispatch(
			servletContext, request, null, pageContext, path, theme, write,
			ThemeHelper.TEMPLATE_EXTENSION_VM);
	}

	protected static String doDispatch(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext, String path,
			Theme theme, boolean write, String extension)
		throws Exception {

		String pluginServletContextName = GetterUtil.getString(
			theme.getServletContextName());

		ServletContext pluginServletContext = ServletContextPool.get(
			pluginServletContextName);

		ClassLoader pluginClassLoader = null;

		if (pluginServletContext != null) {
			pluginClassLoader = (ClassLoader)pluginServletContext.getAttribute(
				PluginContextListener.PLUGIN_CLASS_LOADER);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if ((pluginClassLoader != null) &&
			(pluginClassLoader != contextClassLoader)) {

			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_FTL)) {
				return doIncludeFTL(
					servletContext, request, pageContext, path, theme, false,
					write);
			}
			else if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_JSP)) {
				doIncludeJSP(servletContext, request, response, path, theme);
			}
			else if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_VM)) {
				return doIncludeVM(
					servletContext, request, pageContext, path, theme, false,
					write);
			}

			return null;
		}
		finally {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected static String doIncludeFTL(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String path, Theme theme,
			boolean restricted, boolean write)
		throws Exception {

		// The servlet context name will be null when the theme is deployed to
		// the root directory in Tomcat. See
		// com.liferay.portal.servlet.MainServlet and
		// com.liferay.portlet.PortletContextImpl for other cases where a null
		// servlet context name is also converted to an empty string.

		String servletContextName = GetterUtil.getString(
			theme.getServletContextName());

		if (ServletContextPool.get(servletContextName) == null) {

			// This should only happen if the FreeMarker template is the first
			// page to be accessed in the system

			ServletContextPool.put(servletContextName, servletContext);
		}

		String portletId = getPortletId(request);

		String resourcePath = theme.getResourcePath(
			servletContext, portletId, path);

		if (Validator.isNotNull(portletId) &&
			PortletConstants.hasInstanceId(portletId) &&
			!TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, resourcePath)) {

			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			resourcePath = theme.getResourcePath(
				servletContext, rootPortletId, path);
		}

		if (Validator.isNotNull(portletId) &&
			!TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, resourcePath)) {

			resourcePath = theme.getResourcePath(servletContext, null, path);
		}

		if (!TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, resourcePath)) {

			_log.error(resourcePath + " does not exist");

			return null;
		}

		TemplateResource templateResource =
			TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_FTL, resourcePath);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, restricted);

		// FreeMarker variables

		template.prepare(request);

		// Theme servlet context

		ServletContext themeServletContext = ServletContextPool.get(
			servletContextName);

		template.put("themeServletContext", themeServletContext);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = null;

		if (write) {

			// Wrapping is needed because of a bug in FreeMarker

			writer = UnsyncPrintWriterPool.borrow(pageContext.getOut());
		}
		else {
			writer = new UnsyncStringWriter();
		}

		VelocityTaglib velocityTaglib = new VelocityTaglibImpl(
			servletContext, request,
			new PipingServletResponse(response, writer), pageContext, template);

		template.put(TemplateConstants.WRITER, writer);
		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		// Portal JSP tag library factory

		TemplateHashModel portalTaglib =
			FreeMarkerTaglibFactoryUtil.createTaglibFactory(servletContext);

		template.put("PortalJspTagLibs", portalTaglib);

		// Theme JSP tag library factory

		TemplateHashModel themeTaglib =
			FreeMarkerTaglibFactoryUtil.createTaglibFactory(
				themeServletContext);

		template.put("ThemeJspTaglibs", themeTaglib);

		// FreeMarker JSP tag library support

		final Servlet servlet = (Servlet)pageContext.getPage();

		GenericServlet genericServlet = null;

		if (servlet instanceof GenericServlet) {
			genericServlet = (GenericServlet)servlet;
		}
		else {
			genericServlet = new GenericServlet() {

				@Override
				public void service(
						ServletRequest servletRequest,
						ServletResponse servletResponse)
					throws IOException, ServletException {

					servlet.service(servletRequest, servletResponse);
				}

			};

			genericServlet.init(pageContext.getServletConfig());
		}

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				genericServlet, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Request", httpRequestHashModel);

		// Merge templates

		template.processTemplate(writer);

		if (write) {
			return null;
		}
		else {
			return writer.toString();
		}
	}

	protected static void doIncludeJSP(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path, Theme theme)
		throws Exception {

		insertTilesVariables(request);

		if (theme.isWARFile()) {
			ServletContext themeServletContext = servletContext.getContext(
				theme.getContextPath());

			if (themeServletContext == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " cannot find its " +
						"servlet context at " + theme.getServletContextName());
			}
			else {
				RequestDispatcher requestDispatcher =
					themeServletContext.getRequestDispatcher(path);

				if (requestDispatcher == null) {
					_log.error(
						"Theme " + theme.getThemeId() + " does not have " +
							path);
				}
				else {
					requestDispatcher.include(request, response);
				}
			}
		}
		else {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(path);

			if (requestDispatcher == null) {
				_log.error(
					"Theme " + theme.getThemeId() + " does not have " + path);
			}
			else {
				requestDispatcher.include(request, response);
			}
		}
	}

	protected static String doIncludeVM(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String page, Theme theme,
			boolean restricted, boolean write)
		throws Exception {

		// The servlet context name will be null when the theme is deployed to
		// the root directory in Tomcat. See
		// com.liferay.portal.servlet.MainServlet and
		// com.liferay.portlet.PortletContextImpl for other cases where a null
		// servlet context name is also converted to an empty string.

		String servletContextName = GetterUtil.getString(
			theme.getServletContextName());

		if (ServletContextPool.get(servletContextName) == null) {

			// This should only happen if the Velocity template is the first
			// page to be accessed in the system

			ServletContextPool.put(servletContextName, servletContext);
		}

		String portletId = getPortletId(request);

		String resourcePath = theme.getResourcePath(
			servletContext, portletId, page);

		boolean checkResourceExists = true;

		if (Validator.isNotNull(portletId)) {
			if (PortletConstants.hasInstanceId(portletId) &&
				(checkResourceExists = !
				TemplateResourceLoaderUtil.hasTemplateResource(
					TemplateConstants.LANG_TYPE_VM, resourcePath))) {

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				resourcePath = theme.getResourcePath(
					servletContext, rootPortletId, page);
			}

			if (checkResourceExists &&
				(checkResourceExists = !
				TemplateResourceLoaderUtil.hasTemplateResource(
					TemplateConstants.LANG_TYPE_VM, resourcePath))) {

				resourcePath = theme.getResourcePath(
					servletContext, null, page);
			}
		}

		if (checkResourceExists &&
			!TemplateResourceLoaderUtil.hasTemplateResource(
				TemplateConstants.LANG_TYPE_VM, resourcePath)) {

			_log.error(resourcePath + " does not exist");

			return null;
		}

		TemplateResource templateResource =
			TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_VM, resourcePath);

		if (templateResource == null) {
			throw new Exception(
				"Unable to load template resource " + resourcePath);
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM, templateResource, restricted);

		// Velocity variables

		template.prepare(request);

		// Page context

		template.put("pageContext", pageContext);

		// Theme servlet context

		ServletContext themeServletContext = ServletContextPool.get(
			servletContextName);

		template.put("themeServletContext", themeServletContext);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = null;

		if (write) {
			writer = pageContext.getOut();
		}
		else {
			writer = new UnsyncStringWriter();
		}

		VelocityTaglib velocityTaglib = new VelocityTaglibImpl(
			servletContext, request,
			new PipingServletResponse(response, writer), pageContext, template);

		template.put(TemplateConstants.WRITER, writer);
		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		// Merge templates

		template.processTemplate(writer);

		if (write) {
			return null;
		}
		else {
			return ((UnsyncStringWriter)writer).toString();
		}
	}

	protected static void insertTilesVariables(HttpServletRequest request) {
		ComponentContext componentContext =
			(ComponentContext)request.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT);

		if (componentContext == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String tilesTitle = (String)componentContext.getAttribute("title");
		String tilesContent = (String)componentContext.getAttribute("content");
		boolean tilesSelectable = GetterUtil.getBoolean(
			(String)componentContext.getAttribute("selectable"));

		themeDisplay.setTilesTitle(tilesTitle);
		themeDisplay.setTilesContent(tilesContent);
		themeDisplay.setTilesSelectable(tilesSelectable);
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeUtil.class);

}