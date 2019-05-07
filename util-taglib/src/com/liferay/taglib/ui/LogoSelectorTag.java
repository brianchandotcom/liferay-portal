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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class LogoSelectorTag extends IncludeTag {

	public int getAspectRatio() {
		return _aspectRatio;
	}

	public String getCurrentLogoURL() {
		return _currentLogoURL;
	}

	public String getDefaultLogoURL() {
		return _defaultLogoURL;
	}

	public String getEditLogoFn() {
		return _editLogoFn;
	}

	public String getLogoDisplaySelector() {
		return _logoDisplaySelector;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public String getTempImageFileName() {
		return _tempImageFileName;
	}

	public boolean isDefaultLogo() {
		return _defaultLogo;
	}

	public boolean isPreserveRatio() {
		return _preserveRatio;
	}

	public boolean isShowBackground() {
		return _showBackground;
	}

	public boolean isShowButtons() {
		return _showButtons;
	}

	public void setAspectRatio(int aspectRatio) {
		_aspectRatio = aspectRatio;
	}

	public void setCurrentLogoURL(String currentLogoURL) {
		_currentLogoURL = currentLogoURL;
	}

	public void setDefaultLogo(boolean defaultLogo) {
		_defaultLogo = defaultLogo;
	}

	public void setDefaultLogoURL(String defaultLogoURL) {
		_defaultLogoURL = defaultLogoURL;
	}

	public void setEditLogoFn(String editLogoFn) {
		_editLogoFn = editLogoFn;
	}

	public void setLogoDisplaySelector(String logoDisplaySelector) {
		_logoDisplaySelector = logoDisplaySelector;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public void setPreserveRatio(boolean preserveRatio) {
		_preserveRatio = preserveRatio;
	}

	public void setShowBackground(boolean showBackground) {
		_showBackground = showBackground;
	}

	public void setShowButtons(boolean showButtons) {
		_showButtons = showButtons;
	}

	public void setTempImageFileName(String tempImageFileName) {
		_tempImageFileName = tempImageFileName;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_aspectRatio = 0;
		_currentLogoURL = null;
		_defaultLogo = false;
		_defaultLogoURL = null;
		_editLogoFn = null;
		_logoDisplaySelector = null;
		_maxFileSize = 0;
		_preserveRatio = false;
		_showBackground = true;
		_showButtons = true;
		_tempImageFileName = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:aspectRatio",
			String.valueOf(_aspectRatio));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:currentLogoURL", _currentLogoURL);
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:defaultLogo",
			String.valueOf(_defaultLogo));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:defaultLogoURL", _defaultLogoURL);
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:editLogoFn", _editLogoFn);
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:logoDisplaySelector",
			_logoDisplaySelector);

		if (_maxFileSize == 0) {
			try {
				_maxFileSize =
					UploadServletRequestConfigurationHelperUtil.getMaxSize();
			}
			catch (SystemException se) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
		}

		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:maxFileSize",
			String.valueOf(_maxFileSize));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:preserveRatio",
			String.valueOf(_preserveRatio));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:showBackground",
			String.valueOf(_showBackground));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:showButtons",
			String.valueOf(_showButtons));
		httpServletRequest.setAttribute(
			"liferay-ui:logo-selector:tempImageFileName", _tempImageFileName);
	}

	private static final String _PAGE =
		"/html/taglib/ui/logo_selector/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		LogoSelectorTag.class);

	private int _aspectRatio;
	private String _currentLogoURL;
	private boolean _defaultLogo;
	private String _defaultLogoURL;
	private String _editLogoFn;
	private String _logoDisplaySelector;
	private long _maxFileSize;
	private boolean _preserveRatio;
	private boolean _showBackground = true;
	private boolean _showButtons = true;
	private String _tempImageFileName;

}