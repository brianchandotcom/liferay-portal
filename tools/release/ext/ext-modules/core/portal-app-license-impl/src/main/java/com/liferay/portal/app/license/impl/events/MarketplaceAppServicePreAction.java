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

package com.liferay.portal.app.license.impl.events;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Amos Fong
 */
public class MarketplaceAppServicePreAction extends Action {

	public MarketplaceAppServicePreAction(
		Set<String> developerBundleNames, String licensePageURL) {

		_developerBundleNames = developerBundleNames;
		_licensePageURL = licensePageURL;
	}

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			_clientIPAddresses.add(httpServletRequest.getRemoteAddr());

			if (_clientIPAddresses.size() > 10) {
				if (_isControlPanel(httpServletRequest)) {
					httpServletResponse.sendRedirect(_licensePageURL);
				}
				else if (_isLicensePage(httpServletRequest)) {
					StringBundler sb = new StringBundler(4);

					sb.append("You have exceeded the developer license ");
					sb.append("connection limit for ");
					sb.append(_getBundleSymbolicName());
					sb.append(".");

					httpServletRequest.setAttribute(
						"ERROR_MESSAGE", sb.toString());
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private String _getBundleSymbolicName() {
		for (String key : _developerBundleNames) {
			return key;
		}

		return null;
	}

	private boolean _isControlPanel(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Group group = themeDisplay.getScopeGroup();

			if (group.isControlPanel()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isLicensePage(HttpServletRequest httpServletRequest) {
		String pathInfo = httpServletRequest.getPathInfo();

		if (Validator.isNull(pathInfo) ||
			!pathInfo.startsWith(StringPool.SLASH)) {

			return false;
		}

		if (pathInfo.equals("/portal/license")) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MarketplaceAppServicePreAction.class);

	private final Set<String> _clientIPAddresses = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final Set<String> _developerBundleNames;
	private final String _licensePageURL;

}