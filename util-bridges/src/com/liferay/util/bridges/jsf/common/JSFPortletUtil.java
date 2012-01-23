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

package com.liferay.util.bridges.jsf.common;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.reflect.Method;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Neil Griffin
 */
public class JSFPortletUtil {

	public static long getCompanyId(FacesContext facesContext) {
		return getCompanyId(getPortletRequest(facesContext));
	}

	public static long getCompanyId(PortletRequest portletRequest) {
		long companyId = 0;

		Map<String, String> userInfo =
			(Map<String, String>)portletRequest.getAttribute(
				RenderRequest.USER_INFO);

		if (userInfo != null) {
			companyId = GetterUtil.getLong(userInfo.get("liferay.company.id"));
		}

		return companyId;
	}

	public static Locale getLocale(FacesContext facesContext) {
		Locale locale = facesContext.getViewRoot().getLocale();

		if (locale == null) {
			locale = facesContext.getApplication().getDefaultLocale();
		}

		return (locale);
	}

	public static PortletPreferences getPortletPreferences(
		FacesContext facesContext) {

		return JSFPortletUtil.getPortletRequest(facesContext).getPreferences();
	}

	public static PortletRequest getPortletRequest(FacesContext facesContext) {
		Object request = facesContext.getExternalContext().getRequest();

		if (request == null) {
			return null;
		}

		if (request instanceof PortletRequest) {
			return (PortletRequest)request;
		}
		else if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;

			Object portletArtifactHack = httpServletRequest.getAttribute(
				"com.icesoft.faces.portletHack");

			if (portletArtifactHack == null) {
				return null;
			}

			try {
				Class<?> portletArtifactHackClass =
					portletArtifactHack.getClass();

				Method method = portletArtifactHackClass.getMethod(
					"getPortletRequest");

				if (method != null) {
					Object value = method.invoke(portletArtifactHack);

					if ((value != null) && (value instanceof PortletRequest)) {
						return (PortletRequest)value;
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return null;
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName) {

		return getPreferenceValue(facesContext, preferenceName, null);
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName, String defaultValue) {

		return getPreferenceValue(
			getPortletPreferences(facesContext), preferenceName, defaultValue);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName) {

		return getPreferenceValue(portletPreferences, preferenceName, null);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName,
		String defaultValue) {

		String value = defaultValue;

		if (portletPreferences != null) {
			value = portletPreferences.getValue(preferenceName, defaultValue);
		}

		return value;
	}

	private static Log _log = LogFactoryUtil.getLog(JSFPortletUtil.class);

}