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

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.util.ThemeHelper;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class WrapPortletTag
	extends ParamAndPropertyAncestorTagImpl implements BodyTag {

	public static String doTag(
			String wrapPage, String portletPage, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			PageContext pageContext)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		// Portlet content

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(portletPage);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		requestDispatcher.include(request, pipingServletResponse);

		portletDisplay.setContent(unsyncStringWriter.getStringBundler());

		// Page

		String content = null;

		String extension = theme.getTemplateExtension();

		if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_FTL)) {
			content = ThemeUtil.includeFTL(
				servletContext, request, pageContext, wrapPage, theme, false);
		}
		else if (extension.equals(ThemeHelper.TEMPLATE_EXTENSION_VM)) {
			content = ThemeUtil.includeVM(
				servletContext, request, pageContext, wrapPage, theme, false);
		}

		return _CONTENT_WRAPPER_PRE.concat(content).concat(
			_CONTENT_WRAPPER_POST);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Theme theme = themeDisplay.getTheme();
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			// Portlet content

			portletDisplay.setContent(getBodyContentAsStringBundler());

			// Page

			ThemeUtil.include(
				servletContext, request, new PipingServletResponse(pageContext),
				pageContext, getPage(), theme);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearParams();
			clearProperties();
		}
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public void setPage(String page) {
		_page = page;
	}

	protected String getPage() {
		return _page;
	}

	private static final String _CONTENT_WRAPPER_POST = "</div>";

	private static final String _CONTENT_WRAPPER_PRE =
		"<div class=\"column-1\" id=\"main-content\" role=\"main\">";

	private String _page;

}