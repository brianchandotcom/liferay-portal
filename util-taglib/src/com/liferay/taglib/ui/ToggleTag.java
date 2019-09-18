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

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class ToggleTag extends IncludeTag {

	public static void doTag(
			String id, String showImage, String hideImage, String showMessage,
			String hideMessage, boolean defaultShowContent, String stateVar,
			ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		doTag(
			_PAGE, id, showImage, hideImage, showMessage, hideMessage,
			defaultShowContent, stateVar, servletContext, httpServletRequest,
			httpServletResponse);
	}

	public static void doTag(
			String page, String id, String showImage, String hideImage,
			String showMessage, String hideMessage, boolean defaultShowContent,
			String stateVar, ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (Validator.isNull(showImage) && Validator.isNull(showMessage)) {
			showImage =
				themeDisplay.getPathThemeImages() + "/arrows/01_down.png";
		}

		if (Validator.isNull(hideImage) && Validator.isNull(hideImage)) {
			hideImage =
				themeDisplay.getPathThemeImages() + "/arrows/01_right.png";
		}

		String defaultStateValue =
			defaultShowContent ? StringPool.BLANK : "none";
		String defaultImage = defaultShowContent ? hideImage : showImage;
		String defaultMessage = defaultShowContent ? hideMessage : showMessage;

		String clickValue = SessionClicks.get(httpServletRequest, id, null);

		if (defaultShowContent) {
			if ((clickValue != null) && clickValue.equals("none")) {
				defaultStateValue = "none";
				defaultImage = showImage;
				defaultMessage = showMessage;
			}
			else {
				defaultStateValue = "";
				defaultImage = hideImage;
				defaultMessage = hideMessage;
			}
		}
		else {
			if ((clickValue == null) || clickValue.equals("none")) {
				defaultStateValue = "none";
				defaultImage = showImage;
				defaultMessage = showMessage;
			}
			else {
				defaultStateValue = "";
				defaultImage = hideImage;
				defaultMessage = hideMessage;
			}
		}

		if (stateVar == null) {
			stateVar = DeterminateKeyGenerator.generate(
				ToggleTag.class.getName());
		}

		httpServletRequest.setAttribute(
			"liferay-ui:toggle:defaultImage", defaultImage);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:defaultMessage", defaultMessage);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:defaultStateValue", defaultStateValue);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:hideImage", hideImage);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:hideMessage", hideMessage);
		httpServletRequest.setAttribute("liferay-ui:toggle:id", id);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:showImage", showImage);
		httpServletRequest.setAttribute(
			"liferay-ui:toggle:showMessage", showMessage);
		httpServletRequest.setAttribute("liferay-ui:toggle:stateVar", stateVar);

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, page);

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(
				getPage(), _id, _showImage, _hideImage, _showMessage,
				_hideMessage, _defaultShowContent, _stateVar,
				getServletContext(), getRequest(),
				PipingServletResponse.createPipingServletResponse(pageContext));

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getHideImage() {
		return _hideImage;
	}

	public String getHideMessage() {
		return _hideMessage;
	}

	public String getId() {
		return _id;
	}

	public String getShowImage() {
		return _showImage;
	}

	public String getShowMessage() {
		return _showMessage;
	}

	public String getStateVar() {
		return _stateVar;
	}

	public boolean isDefaultShowContent() {
		return _defaultShowContent;
	}

	public void setDefaultShowContent(boolean defaultShowContent) {
		_defaultShowContent = defaultShowContent;
	}

	public void setHideImage(String hideImage) {
		_hideImage = hideImage;
	}

	public void setHideMessage(String hideMessage) {
		_hideMessage = hideMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setShowImage(String showImage) {
		_showImage = showImage;
	}

	public void setShowMessage(String showMessage) {
		_showMessage = showMessage;
	}

	public void setStateVar(String stateVar) {
		_stateVar = stateVar;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/toggle/page.jsp";

	private boolean _defaultShowContent = true;
	private String _hideImage;
	private String _hideMessage;
	private String _id;
	private String _showImage;
	private String _showMessage;
	private String _stateVar;

}