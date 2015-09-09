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
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrefixPredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletInstance;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryConstants;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.util.Map;

import javax.portlet.PortletPreferences;

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
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction,
			String portletInstanceId, String queryString,
			String defaultPreferences, PageContext pageContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			portletProviderClassName, portletProviderAction);

		if (Validator.isNotNull(portletId)) {
			doTag(
				portletId, portletInstanceId, queryString,
				_SETTINGS_SCOPE_DEFAULT, defaultPreferences, pageContext,
				request, response);
		}
		else {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			if (!layout.isTypeControlPanel() &&
				!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE)) {

				return;
			}

			request.setAttribute(
				"liferay-portlet:runtime:portletProviderClassName",
				portletProviderClassName);
			request.setAttribute(
				"liferay-portlet:runtime:portletProviderAction",
				portletProviderAction);

			PortalIncludeUtil.include(pageContext, _ERROR_PAGE);
		}
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

		doTag(
			portletName, StringPool.BLANK, queryString, _SETTINGS_SCOPE_DEFAULT,
			defaultPreferences, pageContext, request, response);
	}

	public static void doTag(
			String portletName, String portletInstanceId, String queryString,
			String defaultPreferences, PageContext pageContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		doTag(
			portletName, portletInstanceId, queryString,
			_SETTINGS_SCOPE_DEFAULT, defaultPreferences, pageContext, request,
			response);
	}

	public static void doTag(
			String portletName, String portletInstanceId, String queryString,
			String settingsScope, String defaultPreferences,
			PageContext pageContext, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (pageContext != null) {
			response = new PipingServletResponse(
				response, pageContext.getOut());
		}

		PortletInstance portletInstance = new PortletInstance(
			portletName, portletInstanceId);

		RestrictPortletServletRequest restrictPortletServletRequest =
			new RestrictPortletServletRequest(
				PortalUtil.getOriginalServletRequest(request));

		queryString = PortletParameterUtil.addNamespace(
			portletInstance.getPortletInstanceKey(), queryString);

		Map<String, String[]> parameterMap = request.getParameterMap();

		if (!Validator.equals(
				portletInstance.getPortletInstanceKey(),
				request.getParameter("p_p_id"))) {

			parameterMap = MapUtil.filterByKeys(
				parameterMap, new PrefixPredicateFilter("p_p_"));
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

				if (layoutTypePortlet.hasStateMaxPortletId(
						portletInstance.getPortletInstanceKey())) {

					// A portlet in the maximized state has already been
					// processed

					return;
				}
			}

			Layout layout = themeDisplay.getLayout();

			request.setAttribute(WebKeys.PORTLET_DECORATE, false);

			Portlet portlet = getPortlet(
				themeDisplay.getCompanyId(), portletInstance.getPortletName());

			PortletPreferences renderPortletPreferences =
				getRenderPortletPreferences(
					themeDisplay, settingsScope, portlet, defaultPreferences);

			if (renderPortletPreferences != null) {
				request.setAttribute(
					WebKeys.RENDER_PORTLET_PREFERENCES,
					renderPortletPreferences);
			}

			request.setAttribute(WebKeys.SETTINGS_SCOPE, settingsScope);

			JSONObject jsonObject = null;

			if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
					portletInstance.getPortletInstanceKey()) < 1) ||
				layout.isTypeControlPanel() || layout.isTypePanel()) {

				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletInstance.getPortletInstanceKey(),
					defaultPreferences);
				PortletPreferencesFactoryUtil.getPortletSetup(
					request, portletInstance.getPortletInstanceKey(),
					defaultPreferences);

				PortletLayoutListener portletLayoutListener =
					portlet.getPortletLayoutListenerInstance();

				if (portletLayoutListener != null) {
					portletLayoutListener.onAddToLayout(
						portletInstance.getPortletInstanceKey(),
						themeDisplay.getPlid());
				}

				jsonObject = JSONFactoryUtil.createJSONObject();

				PortletJSONUtil.populatePortletJSONObject(
					request, StringPool.BLANK, portlet, jsonObject);
			}

			if (jsonObject != null) {
				PortletJSONUtil.writeHeaderPaths(response, jsonObject);
			}

			PortletContainerUtil.render(request, response, portlet);

			if (jsonObject != null) {
				PortletJSONUtil.writeFooterPaths(response, jsonObject);
			}
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

			if (Validator.isNotNull(_portletProviderClassName) &&
				(_portletProviderAction != null)) {
					doTag(
						_portletProviderClassName, _portletProviderAction,
						_portletInstanceId, _queryString, _defaultPreferences,
						pageContext, request, response);
			}
			else {
				doTag(
					_portletName, _portletInstanceId, _queryString,
					_settingsScope, _defaultPreferences, pageContext, request,
					response);
			}

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

	public void setPortletInstanceId(String portletInstanceId) {
		_portletInstanceId = portletInstanceId;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public void setPortletProviderAction(
		PortletProvider.Action portletProviderAction) {

		_portletProviderAction = portletProviderAction;
	}

	public void setPortletProviderClassName(String portletProviderClassName) {
		_portletProviderClassName = portletProviderClassName;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;
	}

	public void setSettingsScope(String settingsScope) {
		_settingsScope = settingsScope;
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

	protected static PortletPreferences getRenderPortletPreferences(
		ThemeDisplay themeDisplay, String settingsScope, Portlet portlet,
		String defaultPreferences) {

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
				themeDisplay.getPlid(), portlet.getPortletId(), settingsScope);

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(), portlet, true) > 0) {

			return PortletPreferencesLocalServiceUtil.getPreferences(
				themeDisplay.getCompanyId(), portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(), portlet.getPortletId());
		}

		if (Validator.isNotNull(defaultPreferences)) {
			return PortletPreferencesFactoryUtil.fromDefaultXML(
				defaultPreferences);
		}

		return null;
	}

	private static final String _ERROR_PAGE =
		"/html/taglib/portlet/runtime/error.jsp";

	private static final String _SETTINGS_SCOPE_DEFAULT =
		PortletPreferencesFactoryConstants.SETTINGS_SCOPE_PORTLET_INSTANCE;

	private static final Log _log = LogFactoryUtil.getLog(RuntimeTag.class);

	private String _defaultPreferences;
	private String _portletInstanceId;
	private String _portletName;
	private PortletProvider.Action _portletProviderAction;
	private String _portletProviderClassName;
	private String _queryString;
	private String _settingsScope = _SETTINGS_SCOPE_DEFAULT;

}