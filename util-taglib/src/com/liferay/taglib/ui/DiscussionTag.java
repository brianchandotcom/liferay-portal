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

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Charles May
 */
public class DiscussionTag extends IncludeTag {

	public void setAssetEntryVisible(boolean assetEntryVisible) {
		_assetEntryVisible = assetEntryVisible;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setFormAction(String formAction) {
		_formAction = formAction;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHideControls(boolean hideControls) {
		_hideControls = hideControls;
	}

	public void setPaginationURL(String paginationURL) {
		_paginationURL = paginationURL;
	}

	public void setRatingsEnabled(boolean ratingsEnabled) {
		_ratingsEnabled = ratingsEnabled;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	public void setSubject(String subject) {
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	protected void cleanUp() {
		_assetEntryVisible = true;
		_className = null;
		_classPK = 0;
		_formAction = null;
		_formName = "fm";
		_hideControls = false;
		_paginationURL = null;
		_ratingsEnabled = true;
		_redirect = null;
		_userId = 0;
	}

	protected String getFormAction(HttpServletRequest request) {
		if (_formAction != null) {
			return _formAction;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() + "/portal/edit_discussion";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getPaginationURL(HttpServletRequest request) {
		if (_paginationURL != null) {
			return _paginationURL;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() + "/portal/more_comments";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:discussion:assetEntryVisible",
			String.valueOf(_assetEntryVisible));
		request.setAttribute("liferay-ui:discussion:className", _className);
		request.setAttribute(
			"liferay-ui:discussion:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:discussion:formAction", getFormAction(request));
		request.setAttribute("liferay-ui:discussion:formName", _formName);
		request.setAttribute(
			"liferay-ui:discussion:hideControls",
			String.valueOf(_hideControls));
		request.setAttribute(
			"liferay-ui:discussion:paginationURL", getPaginationURL(request));
		request.setAttribute(
			"liferay-ui:discussion:ratingsEnabled",
			String.valueOf(_ratingsEnabled));
		request.setAttribute("liferay-ui:discussion:redirect", _redirect);
		request.setAttribute(
			"liferay-ui:discussion:userId", String.valueOf(_userId));
	}

	private static final String _PAGE = "/html/taglib/ui/discussion/page.jsp";

	private boolean _assetEntryVisible = true;
	private String _className;
	private long _classPK;
	private String _formAction;
	private String _formName = "fm";
	private boolean _hideControls;
	private String _paginationURL;
	private boolean _ratingsEnabled = true;
	private String _redirect;
	private long _userId;

}