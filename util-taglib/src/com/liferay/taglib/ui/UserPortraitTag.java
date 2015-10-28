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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserPortraitTag extends IncludeTag {

	public void setImageCssClass(String imageCssClass) {
		_imageCssClass = imageCssClass;
	}

	public void setUserIconCssClass(String userIconCssClass) {
		_userIconCssClass = userIconCssClass;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	protected void cleanUp() {
		_imageCssClass = StringPool.BLANK;
		_userIconCssClass = StringPool.BLANK;
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

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:user-portrait:imageCssClass", _imageCssClass);
		request.setAttribute(
			"liferay-ui:user-portrait:userIconCssClass", _userIconCssClass);
		request.setAttribute("liferay-ui:user-portrait:user", getUser());
		request.setAttribute("liferay-ui:user-portrait:userName", _userName);
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_portrait/page.jsp";

	private String _imageCssClass;
	private String _userIconCssClass;
	private long _userId;
	private String _userName;

}