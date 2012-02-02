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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.servlet.DirectServletContext;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.TrackedServletRequest;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.portal.util.PortalUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class IncludeTag extends AttributesTagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();

			String page = null;

			if (_useCustomPage) {
				page = getCustomPage(servletContext, request);
			}

			if (Validator.isNull(page)) {
				page = getPage();
			}

			if (Validator.isNull(page)) {
				page = getEndPage();
			}

			callSetAttributes();

			if (themeResourceExists(page)) {
				_doIncludeTheme(page);

				return EVAL_PAGE;
			}
			else if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				return processEndTag();
			}
			else {
				_doInclude(page);

				return EVAL_PAGE;
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearDynamicAttributes();
			clearParams();
			clearProperties();

			cleanUpSetAttributes();

			if (!ServerDetector.isResin()) {
				setPage(null);
				setUseCustomPage(true);

				cleanUp();
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();

			String page = getStartPage();

			callSetAttributes();

			if (themeResourceExists(page)) {
				_doIncludeTheme(page);

				return EVAL_BODY_INCLUDE;
			}
			else if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				return processStartTag();
			}
			else {
				_doInclude(page);

				return EVAL_BODY_INCLUDE;
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public ServletContext getServletContext() {
		ServletContext servletContext = super.getServletContext();

		try {
			if (Validator.isNull(_portletId)) {
				return servletContext;
			}

			HttpServletRequest request = getServletRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), _portletId);

			if (portlet == null) {
				return servletContext;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				return servletContext;
			}

			return PortalUtil.getServletContext(portlet, servletContext);
		}
		catch (SystemException se) {
			return servletContext;
		}
	}

	public void runEndTag() throws JspException {
		doEndTag();
	}

	public void runStartTag() throws JspException {
		doStartTag();
	}

	public void runTag() throws JspException {
		doStartTag();
		doEndTag();
	}

	public void setPage(String page) {
		_page = page;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setStrict(boolean strict) {
		_strict = strict;
	}

	public void setUseCustomPage(boolean useCustomPage) {
		_useCustomPage = useCustomPage;
	}

	protected void callSetAttributes() {
		if (_calledSetAttributes) {
			return;
		}

		_calledSetAttributes = true;

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (isCleanUpSetAttributes()) {
			_trackedRequest = new TrackedServletRequest(request);

			request = _trackedRequest;
		}

		setNamespacedAttribute(request, "bodyContent", getBodyContent());
		setNamespacedAttribute(
			request, "customAttributes", getCustomAttributes());
		setNamespacedAttribute(
			request, "dynamicAttributes", getDynamicAttributes());
		setNamespacedAttribute(
			request, "scopedAttributes", getScopedAttributes());

		setAttributes(request);
	}

	protected void cleanUp() {
	}

	protected void cleanUpSetAttributes() {
		_calledSetAttributes = false;

		if (isCleanUpSetAttributes()) {
			for (String name : _trackedRequest.getSetAttributes()) {
				_trackedRequest.removeAttribute(name);
			}

			_trackedRequest = null;
		}
	}

	protected String getCustomPage(
		ServletContext servletContext, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return null;
		}

		Group group = themeDisplay.getScopeGroup();

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		String customJspServletContextName = typeSettingsProperties.getProperty(
			"customJspServletContextName");

		if (Validator.isNull(customJspServletContextName)) {
			return null;
		}

		String page = getPage();

		if (Validator.isNull(page)) {
			page = getEndPage();
		}

		if (Validator.isNull(page)) {
			return null;
		}

		String customPage = CustomJspRegistryUtil.getCustomJspFileName(
			customJspServletContextName, page);

		if (FileAvailabilityUtil.isAvailable(servletContext, customPage)) {
			return customPage;
		}

		return null;
	}

	protected String getEndPage() {
		return null;
	}

	protected String getPage() {
		return _page;
	}

	protected String getStartPage() {
		return null;
	}

	protected void include(String page) throws Exception {
		ServletContext servletContext = getServletContext();

		if (_DIRECT_SERVLET_CONTEXT_ENABLED) {
			servletContext = new DirectServletContext(servletContext);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		HttpServletRequest request = getServletRequest();

		request.setAttribute(
			WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT, _strict);
		request.setAttribute(WebKeys.SERVLET_PATH, page);

		HttpServletResponse response = new PipingServletResponse(
			pageContext, isTrimNewLines());

		if (!isWARFile(request)) {
			requestDispatcher.include(request, response);
		}
		else {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			Class<?> clazz = classLoader.loadClass(_LIFERAY_REQUEST_DISPATCHER);

			Constructor<?> constructor = clazz.getConstructor(
				RequestDispatcher.class, String.class);

			Object obj = constructor.newInstance(requestDispatcher, page);

			Method method = clazz.getMethod(
				"include", ServletRequest.class, ServletResponse.class,
				boolean.class);

			method.invoke(obj, request, response, true);
		}

		request.removeAttribute(WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT);
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isTrimNewLines() {
		return _TRIM_NEW_LINES;
	}

	protected boolean isWARFile(HttpServletRequest request)
		throws SystemException {

		if (Validator.isNull(_portletId)) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _portletId);

		if (portlet == null) {
			return false;
		}

		PortletApp portletApp = portlet.getPortletApp();

		return portletApp.isWARFile();
	}

	protected int processEndTag() throws Exception {
		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		return EVAL_BODY_INCLUDE;
	}

	protected void setAttributes(HttpServletRequest request) {
	}

	protected void setCalledSetAttributes(boolean calledSetAttributes) {
		_calledSetAttributes = calledSetAttributes;
	}

	protected boolean themeResourceExists(String page)
		throws Exception {

		if ((page == null) || !_THEME_JSP_OVERRIDE_ENABLED || _strict) {
			return false;
		}

		ServletContext servletContext = getServletContext();
		HttpServletRequest request = getServletRequest();

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		String portletId = ThemeUtil.getPortletId(request);

		boolean exists = theme.resourceExists(servletContext, portletId, page);

		if (_log.isDebugEnabled() && exists) {
			String resourcePath = theme.getResourcePath(
				servletContext, null, page);

			_log.debug(resourcePath);
		}

		return exists;
	}

	private void _doInclude(String page) throws JspException {
		try {
			include(page);
		}
		catch (Exception e) {
			HttpServletRequest request = getServletRequest();

			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			_log.error(
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage());

			LogUtil.log(_log, e);

			if (e instanceof JspException) {
				throw (JspException)e;
			}
		}
	}

	private void _doIncludeTheme(String page) throws Exception {
		ServletContext servletContext = getServletContext();
		HttpServletRequest request = getServletRequest();
		HttpServletResponse response = getServletResponse();

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		ThemeUtil.include(
			servletContext, request, response, pageContext, page, theme);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = false;

	private static final boolean _DIRECT_SERVLET_CONTEXT_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

	private static final String _LIFERAY_REQUEST_DISPATCHER =
		"com.liferay.portal.apache.bridges.struts.LiferayRequestDispatcher";

	private static final boolean _THEME_JSP_OVERRIDE_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.THEME_JSP_OVERRIDE_ENABLED));

	private static final boolean _TRIM_NEW_LINES = false;

	private static Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private boolean _calledSetAttributes;
	private String _page;
	private String _portletId;
	private boolean _strict;
	private TrackedServletRequest _trackedRequest;
	private boolean _useCustomPage = true;

}