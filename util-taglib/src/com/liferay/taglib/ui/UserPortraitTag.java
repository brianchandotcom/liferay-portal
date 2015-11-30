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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.LexiconUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserPortraitTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setImageCssClass(String imageCssClass) {
		_imageCssClass = imageCssClass;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	protected void cleanUp() {
		_cssClass = StringPool.BLANK;
		_imageCssClass = StringPool.BLANK;
		_userId = 0;
		_userName = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected User getUser() {
		return UserLocalServiceUtil.fetchUser(_userId);
	}

	protected String getUserInitials(User user) {
		if (Validator.isNull(_userName) && (user != null)) {
			return user.getInitials();
		}

		String userName = _userName;

		if (Validator.isNull(userName)) {
			userName = LanguageUtil.get(request, "user");
		}

		String[] userNames = StringUtil.split(userName, StringPool.SPACE);

		StringBundler sb = new StringBundler(userNames.length);

		for (String curUserName : userNames) {
			sb.append(
				StringUtil.toUpperCase(StringUtil.shorten(curUserName, 1)));
		}

		return sb.toString();
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return true;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:user-portrait:colorCssClass",
			LexiconUtil.getUserColorCssClass(getUser()));
		request.setAttribute("liferay-ui:user-portrait:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:user-portrait:imageCssClass", _imageCssClass);

		User user = getUser();

		if ((user != null) && (user.getPortraitId() > 0)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			try {
				request.setAttribute(
					"liferay-ui:user-portrait:portraitURL",
					user.getPortraitURL(themeDisplay));
			}
			catch (PortalException pe) {
				_log.error(pe);
			}
		}

		request.setAttribute("liferay-ui:user-portrait:user", user);
		request.setAttribute(
			"liferay-ui:user-portrait:userInitials", getUserInitials(user));
		request.setAttribute("liferay-ui:user-portrait:userName", _userName);
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_portrait/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		UserPortraitTag.class);

	private String _cssClass;
	private String _imageCssClass;
	private long _userId;
	private String _userName;

}