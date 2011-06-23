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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariablesUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThemeHelper;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.velocity.VelocityVariablesUtil;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;

import java.io.Writer;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

	public static void include(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext, String page,
			Theme theme)
		throws Exception {

		String extension = theme.getTemplateExtension();

		if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_FTL)) {
			includeFTL(servletContext, request, pageContext, page, theme, true);
		}
		else if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_VM)) {
			includeVM(servletContext, request, pageContext, page, theme, true);
		}
		else {
			String path =
				theme.getTemplatesPath() + StringPool.SLASH + page;

			includeJSP(servletContext, request, response, path, theme);
		}
	}

	public static String includeFTL(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String path, Theme theme, boolean write)
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

		String resourcePath = ThemeHelper.getResourcePath(
			servletContext, theme, path);

		if (!FreeMarkerEngineUtil.resourceExists(resourcePath)) {
			_log.error(resourcePath + " does not exist");

			return null;
		}

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedStandardToolsContext();

		// FreeMarker variables

		FreeMarkerVariablesUtil.insertVariables(freeMarkerContext, request);

		// Theme servlet context

		ServletContext themeServletContext = ServletContextPool.get(
			servletContextName);

		freeMarkerContext.put("themeServletContext", themeServletContext);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = null;

		if (write) {

			// Wrapping is needed because of a bug in FreeMarker

			writer = new UnsyncPrintWriter(pageContext.getOut());
		}
		else {
			writer = new UnsyncStringWriter();
		}

		VelocityTaglib velocityTaglib = new VelocityTaglib(
			servletContext, request,
			new PipingServletResponse(response, writer), pageContext);

		request.setAttribute(WebKeys.VELOCITY_TAGLIB, velocityTaglib);

		freeMarkerContext.put("taglibLiferay", velocityTaglib);
		freeMarkerContext.put("theme", velocityTaglib);

		// Portal JSP tag library factory

		TaglibFactory portalTaglib = new TaglibFactory(servletContext);

		freeMarkerContext.put("PortalJspTagLibs", portalTaglib);

		// Theme JSP tag library factory

		TaglibFactory themeTaglib = new TaglibFactory(themeServletContext);

		freeMarkerContext.put("ThemeJspTaglibs", themeTaglib);

		// FreeMarker JSP tag library support

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				(GenericServlet)pageContext.getPage(),
				ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Request", httpRequestHashModel);

		// Merge templates

		FreeMarkerEngineUtil.mergeTemplate(
			resourcePath, freeMarkerContext, writer);

		if (write) {
			return null;
		}
		else {
			return ((UnsyncStringWriter)writer).toString();
		}
	}

	public static void includeJSP(
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

	public static String includeVM(
			ServletContext servletContext, HttpServletRequest request,
			PageContext pageContext, String page, Theme theme, boolean write)
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

		String resourcePath = ThemeHelper.getResourcePath(
			servletContext, theme, page);

		if (!VelocityEngineUtil.resourceExists(resourcePath)) {
			_log.error(resourcePath + " does not exist");

			return null;
		}

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		// Velocity variables

		VelocityVariablesUtil.insertVariables(velocityContext, request);

		// Page context

		velocityContext.put("pageContext", pageContext);

		// Theme servlet context

		ServletContext themeServletContext = ServletContextPool.get(
			servletContextName);

		velocityContext.put("themeServletContext", themeServletContext);

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

		VelocityTaglib velocityTaglib = new VelocityTaglib(
			servletContext, request,
			new PipingServletResponse(response, writer), pageContext);

		request.setAttribute(WebKeys.VELOCITY_TAGLIB, velocityTaglib);

		velocityContext.put("taglibLiferay", velocityTaglib);
		velocityContext.put("theme", velocityTaglib);
		velocityContext.put("writer", writer);

		// Merge templates

		VelocityEngineUtil.mergeTemplate(resourcePath, velocityContext, writer);

		if (write) {
			return null;
		}
		else {
			return ((UnsyncStringWriter)writer).toString();
		}
	}

	public static void insertTilesVariables(HttpServletRequest request) {
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