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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Eudaldo Alonso
 */
public class UserPortraitTag extends IncludeTag {

	public static String getUserPortraitHTML(String cssClass,
		Supplier<String> userPortraitURLSupplier) {

		StringBundler sb = new StringBundler(10);

		sb.append("<span class=\"sticker sticker-circle sticker-light");
		sb.append(" ");
		sb.append(cssClass);
		sb.append("\">");
		sb.append("<span class=\"sticker-overlay\">");
		sb.append("<img alt=\"\" class=\"sticker-img\" src=\"");
		sb.append(HtmlUtil.escape(userPortraitURLSupplier.get()));
		sb.append("\">");
		sb.append("</span>");
		sb.append("</span>");

		return sb.toString();
	}

	@Override
	public int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		User user = getUser();

		String userPortraitHTML = getUserPortraitHTML(_cssClass,
			() -> getPortraitURL(user));

		jspWriter.write(userPortraitHTML);

		return EVAL_PAGE;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setImageCssClass(String imageCssClass) {
	}

	public void setUser(User user) {
		_user = user;
	}

	public void setUserId(long userId) {
		_user = UserLocalServiceUtil.fetchUser(userId);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = StringPool.BLANK;
		_user = null;
		_userName = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getPortraitURL(User user) {
		String portraitURL = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (user != null) {
			try {
				portraitURL = user.getPortraitURL(themeDisplay);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
		else {
			portraitURL = UserConstants.getPortraitURL(
				themeDisplay.getPathImage(), true, 0, StringPool.BLANK);
		}

		return portraitURL;
	}

	protected User getUser() {
		return _user;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_portrait/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		UserPortraitTag.class);

	private String _cssClass = StringPool.BLANK;
	private User _user;
	private String _userName = StringPool.BLANK;

}