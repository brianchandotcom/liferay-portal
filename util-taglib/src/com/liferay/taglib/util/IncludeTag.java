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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.TrackedServletRequest;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CustomJspRegistryUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
				doIncludeTheme(page);

				return EVAL_PAGE;
			}
			else if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				return processEndTag();
			}
			else {
				doInclude(page);

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
			String page = getStartPage();

			callSetAttributes();

			if (themeResourceExists(page)) {
				doIncludeTheme(page);

				return EVAL_BODY_INCLUDE;
			}
			else if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				return processStartTag();
			}
			else {
				doInclude(page);

				return EVAL_BODY_INCLUDE;
			}
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void runTag() throws JspException {
		doStartTag();
		doEndTag();
	}

	public void setPage(String page) {
		_page = page;
	}

	public void setPortletId(String portletId) {
		if (Validator.isNotNull(portletId)) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			servletContext = portletBag.getServletContext();
		}
	}

	public void setStrict(boolean strict) {
		_strict = strict;
	}

	public void setUseCustomPage(boolean useCustomPage) {
		_useCustomPage = useCustomPage;
	}

	protected void callSetAttributes() {
		HttpServletRequest request = getOriginalServletRequest();

		if (isCleanUpSetAttributes()) {
			_trackedRequest = new TrackedServletRequest(request);

			request = _trackedRequest;
		}

		setNamespacedAttribute(request, "bodyContent", getBodyContent());
		setNamespacedAttribute(
			request, "dynamicAttributes", getDynamicAttributes());
		setNamespacedAttribute(
			request, "scopedAttributes", getScopedAttributes());

		setAttributes(request);
	}

	protected void cleanUp() {
	}

	protected void cleanUpSetAttributes() {
		if (isCleanUpSetAttributes()) {
			for (String name : _trackedRequest.getSetAttributes()) {
				_trackedRequest.removeAttribute(name);
			}

			_trackedRequest = null;
		}
	}

	protected void doInclude(String page) throws JspException {
		try {
			include(page);
		}
		catch (Exception e) {
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

	protected void doIncludeTheme(String page) throws Exception {
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		ThemeUtil.include(
			servletContext, request, response, pageContext, page, theme);
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

	protected HttpServletRequest getOriginalServletRequest() {
		return (HttpServletRequest)pageContext.getRequest();
	}

	protected String getPage() {
		return _page;
	}

	protected String getStartPage() {
		return null;
	}

	protected void include(String page) throws Exception {
		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, page);

		request.setAttribute(
			WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT, _strict);

		HttpServletResponse response = new PipingServletResponse(pageContext);

		requestDispatcher.include(request, response);

		request.removeAttribute(WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT);
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isUseCustomPage() {
		return _useCustomPage;
	}

	protected int processEndTag() throws Exception {
		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		return EVAL_BODY_INCLUDE;
	}

	protected void setAttributes(HttpServletRequest request) {
	}

	protected boolean themeResourceExists(String page) throws Exception {
		if ((page == null) || !_THEME_JSP_OVERRIDE_ENABLED || _strict) {
			return false;
		}

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		if (theme == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				theme = themeDisplay.getTheme();
			}
		}

		if (theme == null) {
			return false;
		}

		String portletId = ThemeUtil.getPortletId(request);

		boolean exists = theme.resourceExists(servletContext, portletId, page);

		if (_log.isDebugEnabled() && exists) {
			String resourcePath = theme.getResourcePath(
				servletContext, null, page);

			_log.debug(resourcePath);
		}

		return exists;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = false;

	private static final boolean _THEME_JSP_OVERRIDE_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.THEME_JSP_OVERRIDE_ENABLED));

	private static Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private String _page;
	private boolean _strict;
	private TrackedServletRequest _trackedRequest;
	private boolean _useCustomPage = true;

}