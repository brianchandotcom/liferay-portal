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

package com.liferay.taglib.portletext;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletParameterUtil;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrefixPredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class RuntimeTag extends TagSupport {

	public static void doTag(
			String portletName, PageContext pageContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		doTag(portletName, null, pageContext, request, response);
	}

	public static void doTag(
			String portletName, String queryString, PageContext pageContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		doTag(portletName, queryString, null, pageContext, request, response);
	}

	public static void doTag(
			String portletName, String queryString, String defaultPreferences,
			PageContext pageContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (pageContext != null) {
			response = new PipingServletResponse(
				response, pageContext.getOut());
		}

		String portletId = portletName;

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(request);

		queryString = PortletParameterUtil.addNamespace(portletId, queryString);

		Map<String, String[]> parameterMap = request.getParameterMap();

		if (!portletId.equals(request.getParameter("p_p_id"))) {
			parameterMap = MapUtil.filter(
				parameterMap, new HashMap<String, String[]>(),
				new PrefixPredicateFilter("p_p_"));
		}

		request = DynamicServletRequest.addQueryString(
			restrictPortletServletRequest, parameterMap, queryString, false);

		try {
			request.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (themeDisplay.isStateMaximized()) {
				LayoutTypePortlet layoutTypePortlet =
					themeDisplay.getLayoutTypePortlet();

				if (layoutTypePortlet.hasStateMaxPortletId(portletId)) {

					// A portlet in the maximized state has already been
					// processed

					return;
				}
			}

			Layout layout = themeDisplay.getLayout();

			Portlet portlet = getPortlet(
				themeDisplay.getCompanyId(), portletId);

			if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
					portletId) < 1) ||
				layout.isTypeControlPanel() ||
				layout.isTypePanel()) {

				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);
				PortletPreferencesFactoryUtil.getPortletSetup(
					request, portletId, defaultPreferences);

				PortletLayoutListener portletLayoutListener =
					portlet.getPortletLayoutListenerInstance();

				if (portletLayoutListener != null) {
					portletLayoutListener.onAddToLayout(
						portletId, themeDisplay.getPlid());
				}
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			PortletJSONUtil.populatePortletJSONObject(
				request, StringPool.BLANK, portlet, jsonObject);

			PortletJSONUtil.writeHeaderPaths(response, jsonObject);

			PortletContainerUtil.render(request, response, portlet);

			PortletJSONUtil.writeFooterPaths(response, jsonObject);
		}
		finally {
			restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

			if (layout == null) {
				return EVAL_PAGE;
			}

			HttpServletResponse response =
				(HttpServletResponse)pageContext.getResponse();

			doTag(
				_portletName, _queryString, _defaultPreferences, pageContext,
				request, response);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new JspException(e);
		}
	}

	public void setDefaultPreferences(String defaultPreferences) {
		_defaultPreferences = defaultPreferences;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;
	}

	/**
	 * @see com.liferay.portal.model.impl.LayoutTypePortletImpl#getStaticPortlets(
	 *      String)
	 */
	protected static Portlet getPortlet(long companyId, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		// See LayoutTypePortletImpl#getStaticPortlets for why we only clone
		// non-instanceable portlets

		if (!portlet.isInstanceable()) {
			portlet = (Portlet)portlet.clone();
		}

		portlet.setStatic(true);

		return portlet;
	}

	private static final Log _log = LogFactoryUtil.getLog(RuntimeTag.class);

	private String _defaultPreferences;
	private String _portletName;
	private String _queryString;

}