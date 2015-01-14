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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class LogoSelectorTag extends IncludeTag {

	public void setCurrentLogoURL(String currentLogoURL) {
		_currentLogoURL = currentLogoURL;
	}

	public void setDefaultLogoURL(String defaultLogoURL) {
		_defaultLogoURL = defaultLogoURL;
	}

	public void setEditLogoURL(String editLogoURL) {
		_editLogoURL = editLogoURL;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public void setLogoDisplaySelector(String logoDisplaySelector) {
		_logoDisplaySelector = logoDisplaySelector;
	}

	public void setShowBackground(boolean showBackground) {
		_showBackground = showBackground;
	}

	@Override
	protected void cleanUp() {
		_currentLogoURL = null;
		_defaultLogoURL = null;
		_editLogoURL = null;
		_imageId = 0;
		_logoDisplaySelector = null;
		_showBackground = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:logo-selector:currentLogoURL", _currentLogoURL);
		request.setAttribute(
			"liferay-ui:logo-selector:defaultLogoURL", _defaultLogoURL);
		request.setAttribute(
			"liferay-ui:logo-selector:editLogoURL", _editLogoURL);
		request.setAttribute(
			"liferay-ui:logo-selector:imageId", String.valueOf(_imageId));
		request.setAttribute(
			"liferay-ui:logo-selector:logoDisplaySelector",
			_logoDisplaySelector);
		request.setAttribute(
			"liferay-ui:logo-selector:showBackground",
			String.valueOf(_showBackground));
	}

	private static final String _PAGE =
		"/html/taglib/ui/logo_selector/page.jsp";

	private String _currentLogoURL;
	private String _defaultLogoURL;
	private String _editLogoURL;
	private long _imageId;
	private String _logoDisplaySelector;
	private boolean _showBackground = true;

}