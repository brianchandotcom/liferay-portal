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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Adolfo Pérez
 */
public class TagResourceBundleUtil {

	public static ResourceBundle getResourceBundle(
		HttpServletRequest httpServletRequest, Locale locale) {

		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader(
			httpServletRequest);

		if (resourceBundleLoader != null) {
			return resourceBundleLoader.loadResourceBundle(locale);
		}

		ResourceBundle portletResourceBundle = getPortletResourceBundle(
			httpServletRequest, locale);

		ResourceBundle portalResourceBundle = PortalUtil.getResourceBundle(
			locale);

		return new AggregateResourceBundle(
			portletResourceBundle, portalResourceBundle);
	}

	public static ResourceBundle getResourceBundle(PageContext pageContext) {
		ResourceBundle resourceBundle =
			(ResourceBundle)pageContext.getAttribute("resourceBundle");

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		Locale locale = PortalUtil.getLocale(httpServletRequest);

		if (resourceBundle != null) {
			return new AggregateResourceBundle(
				resourceBundle, PortalUtil.getResourceBundle(locale));
		}

		return getResourceBundle(httpServletRequest, locale);
	}

	protected static ResourceBundle getPortletResourceBundle(
		HttpServletRequest httpServletRequest, Locale locale) {

		PortletConfig portletConfig =
			(PortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig != null) {
			return portletConfig.getResourceBundle(locale);
		}

		return _emptyResourceBundle;
	}

	protected static ResourceBundleLoader getResourceBundleLoader(
		HttpServletRequest httpServletRequest) {

		ResourceBundleLoader resourceBundleLoader =
			(ResourceBundleLoader)httpServletRequest.getAttribute(
				WebKeys.RESOURCE_BUNDLE_LOADER);

		if (resourceBundleLoader == null) {
			ServletContext servletContext =
				httpServletRequest.getServletContext();

			String servletContextName = servletContext.getServletContextName();

			if (Validator.isNull(servletContextName)) {
				return null;
			}

			resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByServletContextName(
						servletContextName);

			PortletConfig portletConfig =
				(PortletConfig)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			if (portletConfig != null) {
				LiferayPortletContext liferayPortletContext =
					(LiferayPortletContext)portletConfig.getPortletContext();

				ServletContext portletServletContext =
					liferayPortletContext.getServletContext();

				String portletServletContextName =
					portletServletContext.getServletContextName();

				if (servletContextName.equals(portletServletContextName)) {
					resourceBundleLoader =
						locale -> portletConfig.getResourceBundle(locale);
				}
			}
		}

		if (resourceBundleLoader == null) {
			return null;
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final ResourceBundle _emptyResourceBundle =
		new EmptyResourceBundle();

	private static class EmptyResourceBundle extends ResourceBundle {

		@Override
		public boolean containsKey(String key) {
			return false;
		}

		@Override
		public Enumeration<String> getKeys() {
			return Collections.emptyEnumeration();
		}

		@Override
		protected Object handleGetObject(String key) {
			return null;
		}

	}

}