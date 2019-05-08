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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class UserDisplayTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			PortalIncludeUtil.include(pageContext, getEndPage());

			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			httpServletRequest.removeAttribute("liferay-ui:user-display:url");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:author", String.valueOf(_author));
			httpServletRequest.setAttribute(
				"liferay-ui:user-display:displayStyle",
				String.valueOf(_displayStyle));

			if (Validator.isNull(_imageCssClass)) {
				_imageCssClass = "img-circle";
			}

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:imageCssClass", _imageCssClass);

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:showLink", String.valueOf(_showLink));
			httpServletRequest.setAttribute(
				"liferay-ui:user-display:showUserDetails",
				String.valueOf(_showUserDetails));
			httpServletRequest.setAttribute(
				"liferay-ui:user-display:showUserName",
				String.valueOf(_showUserName));

			if (Validator.isNull(_userIconCssClass)) {
				_userIconCssClass = "user-icon";
			}

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:userIconCssClass",
				String.valueOf(_userIconCssClass));

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:userId", String.valueOf(_userId));
			httpServletRequest.setAttribute(
				"liferay-ui:user-display:userName", _userName);

			User user = UserLocalServiceUtil.fetchUserById(_userId);

			if (user != null) {
				if (user.isDefaultUser()) {
					user = null;
				}

				httpServletRequest.setAttribute(
					"liferay-ui:user-display:user", user);

				pageContext.setAttribute("userDisplay", user);
			}
			else {
				httpServletRequest.removeAttribute(
					"liferay-ui:user-display:user");

				pageContext.removeAttribute("userDisplay");
			}

			httpServletRequest.setAttribute(
				"liferay-ui:user-display:url", _url);

			PortalIncludeUtil.include(pageContext, getStartPage());

			if (user != null) {
				return EVAL_BODY_INCLUDE;
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setAuthor(boolean author) {
		_author = author;
	}

	public void setDisplayStyle(Object displayStyle) {
		_displayStyle = GetterUtil.getInteger(displayStyle);
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setImageCssClass(String imageCssClass) {
		_imageCssClass = imageCssClass;
	}

	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setShowLink(boolean showLink) {
		_showLink = showLink;
	}

	public void setShowUserDetails(boolean showUserDetails) {
		_showUserDetails = showUserDetails;
	}

	public void setShowUserName(boolean showUserName) {
		_showUserName = showUserName;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setUrl(String url) {
		_url = url;
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

	protected String getEndPage() {
		if (Validator.isNotNull(_endPage)) {
			return _endPage;
		}

		if (Validator.isNotNull(_markupView)) {
			return "/html/taglib/ui/user_display/" + _markupView + "/end.jsp";
		}

		return "/html/taglib/ui/user_display/end.jsp";
	}

	protected String getStartPage() {
		if (Validator.isNotNull(_startPage)) {
			return _startPage;
		}

		if (Validator.isNotNull(_markupView)) {
			return "/html/taglib/ui/user_display/" + _markupView + "/start.jsp";
		}

		return "/html/taglib/ui/user_display/start.jsp";
	}

	private boolean _author;
	private int _displayStyle = 1;
	private String _endPage;
	private String _imageCssClass;
	private String _markupView;
	private boolean _showLink = true;
	private boolean _showUserDetails = true;
	private boolean _showUserName = true;
	private String _startPage;
	private String _url;
	private String _userIconCssClass;
	private long _userId;
	private String _userName;

}