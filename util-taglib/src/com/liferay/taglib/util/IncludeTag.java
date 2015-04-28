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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.TrackedServletRequest;
import com.liferay.portal.kernel.servlet.taglib.TagDynamicIdFactory;
import com.liferay.portal.kernel.servlet.taglib.TagDynamicIdFactoryRegistry;
import com.liferay.portal.kernel.servlet.taglib.TagDynamicIncludeUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.servlet.JspWriterHttpServletResponse;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

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

			if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				logUnavailablePage(page);

				return processEndTag();
			}

			doInclude(page, false);

			return EVAL_PAGE;
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

			if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				logUnavailablePage(page);

				return processStartTag();
			}

			doInclude(page, true);

			return EVAL_BODY_INCLUDE;
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

		setNamespacedAttribute(request, "bodyContent", getBodyContentWrapper());
		setNamespacedAttribute(
			request, "dynamicAttributes", getDynamicAttributes());
		setNamespacedAttribute(
			request, "scopedAttributes", getScopedAttributes());

		setAttributes(request);
	}

	protected void cleanUp() {
	}

	protected void cleanUpSetAttributes() {
		if (isCleanUpSetAttributes() && (_trackedRequest != null)) {
			for (String name : _trackedRequest.getSetAttributes()) {
				_trackedRequest.removeAttribute(name);
			}

			_trackedRequest = null;
		}
	}

	protected void doInclude(
			String page, boolean dynamicIncludeAscendingPriority)
		throws JspException {

		try {
			include(page, dynamicIncludeAscendingPriority);
		}
		catch (Exception e) {
			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			String message =
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage();

			LogUtil.log(_log, e, message);

			if (e instanceof JspException) {
				throw (JspException)e;
			}
		}
	}

	protected void doIncludeTheme(String page) throws Exception {
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		ThemeUtil.include(servletContext, request, response, page, theme);
	}

	protected Object getBodyContentWrapper() {
		final BodyContent bodyContent = getBodyContent();

		if (bodyContent == null) {
			return null;
		}

		return new Object() {

			@Override
			public String toString() {
				return bodyContent.getString();
			}

		};
	}

	protected String getCustomPage(
		ServletContext servletContext, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return null;
		}

		Group group = null;

		try {
			group = StagingUtil.getLiveGroup(themeDisplay.getScopeGroupId());
		}
		catch (Exception e) {
			return null;
		}

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

	protected RequestDispatcher getRequestDispatcher(String page) {
		return DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
			servletContext, page);
	}

	protected String getStartPage() {
		return null;
	}

	protected void include(String page, boolean doStartTag) throws Exception {
		JspWriterHttpServletResponse jspWriterHttpServletResponse = null;

		Class<?> clazz = getClass();

		String tagClassName = clazz.getName();

		String tagDynamicId = null;

		String tagPointPrefix = null;

		if (doStartTag) {
			tagPointPrefix = "doStartTag#";
		}
		else {
			tagPointPrefix = "doEndTag#";
		}

		TagDynamicIdFactory tagDynamicIdFactory =
			TagDynamicIdFactoryRegistry.getTagDynamicIdFactory(tagClassName);

		if (tagDynamicIdFactory != null) {
			jspWriterHttpServletResponse = new JspWriterHttpServletResponse(
				pageContext);

			tagDynamicId = tagDynamicIdFactory.getTagDynamicId(
				request, jspWriterHttpServletResponse, this);

			TagDynamicIncludeUtil.include(
				request, jspWriterHttpServletResponse, tagClassName,
				tagDynamicId, tagPointPrefix + "before", doStartTag);
		}

		RequestDispatcher requestDispatcher = getRequestDispatcher(page);

		request.setAttribute(
			WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT, _strict);

		HttpServletResponse response = new PipingServletResponse(pageContext);

		requestDispatcher.include(request, response);

		request.removeAttribute(WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_STRICT);

		if (tagDynamicIdFactory != null) {
			TagDynamicIncludeUtil.include(
				request, jspWriterHttpServletResponse, tagClassName,
				tagDynamicId, tagPointPrefix + "after", doStartTag);
		}
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isPortalPage(String page) {
		if (page.startsWith("/html/taglib/") &&
			(page.endsWith("/end.jsp") || page.endsWith("/page.jsp") ||
			 page.endsWith("/start.jsp"))) {

			return true;
		}

		return false;
	}

	protected boolean isUseCustomPage() {
		return _useCustomPage;
	}

	protected void logUnavailablePage(String page) {
		if ((page == null) || !_log.isWarnEnabled()) {
			return;
		}

		String contextPath = servletContext.getContextPath();

		if (contextPath.equals(StringPool.BLANK)) {
			contextPath = StringPool.SLASH;
		}

		StringBundler sb = new StringBundler(13);

		sb.append("Unable to find ");
		sb.append(page);
		sb.append(" in the context ");
		sb.append(contextPath);
		sb.append(".");

		if (isPortalPage(page)) {
			if (contextPath.equals(StringPool.SLASH)) {
				sb = null;
			}
			else {
				sb.append(" You must not use a taglib from a module and ");
				sb.append("set the attribute \"servletContext\". Inline the ");
				sb.append("content directly where the taglib is invoked.");
			}
		}
		else if (contextPath.equals(StringPool.SLASH)) {
			Class<?> clazz = getClass();

			if (clazz.equals(IncludeTag.class)) {
				sb.append(" You must set the attribute \"servletContext\" ");
				sb.append("with the value \"<%= application %>\" when ");
				sb.append("invoking a taglib from a module.");
			}
			else {
				sb.append(" You must not use a taglib from a module and ");
				sb.append("set the attribute \"file\". Inline the content ");
				sb.append("directly where the taglib is invoked.");
			}
		}

		if (sb != null) {
			_log.warn(sb.toString());
		}
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

	private static final Log _log = LogFactoryUtil.getLog(IncludeTag.class);

	private String _page;
	private boolean _strict;
	private TrackedServletRequest _trackedRequest;
	private boolean _useCustomPage = true;

}