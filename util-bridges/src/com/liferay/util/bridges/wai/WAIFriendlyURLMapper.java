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

package com.liferay.util.bridges.wai;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 */
public class WAIFriendlyURLMapper implements FriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		String portletId = liferayPortletURL.getPortletId();

		String prefix = portletId;

		int pos = portletId.indexOf(PortletConstants.WAR_SEPARATOR);

		if (pos != -1) {
			prefix = portletId.substring(0, pos);
		}

		String appUrl = GetterUtil.getString(
			liferayPortletURL.getParameter("appURL"));

		liferayPortletURL.addParameterIncludedInPath("p_p_id");

		return StringPool.SLASH + _MAPPING + StringPool.SLASH + prefix +
			StringPool.SLASH + appUrl;
	}

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public Router getRouter() {
		return router;
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	@Override
	public boolean isPortletInstanceable() {
		return false;
	}

	@Override
	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		int x = friendlyURLPath.indexOf(_MAPPING);
		int y = friendlyURLPath.indexOf(
			CharPool.SLASH, x + _MAPPING.length() + 1);

		if (x == -1) {
			return;
		}

		String prefix = friendlyURLPath.substring(x + _MAPPING.length() + 1, y);

		String portletId = prefix + PortletConstants.WAR_SEPARATOR + prefix;

		parameterMap.put("p_p_id", new String[] {portletId});
		parameterMap.put("p_p_lifecycle", new String[] {"0"});

		if (hasBinaryExtension(friendlyURLPath)) {
			parameterMap.put(
				"p_p_state",
				new String[] {LiferayWindowState.EXCLUSIVE.toString()});
		}
		else {
			parameterMap.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}

		parameterMap.put(
			"p_p_mode", new String[] {PortletMode.VIEW.toString()});

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String path = friendlyURLPath.substring(y);

		parameterMap.put(namespace + "appURL", new String[] {path});
	}

	@Override
	public void setMapping(String mapping) {
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@Override
	public void setPortletInstanceable(boolean portletInstanceable) {
	}

	@Override
	public void setRouter(Router router) {
		this.router = router;
	}

	protected boolean hasBinaryExtension(String friendlyURLPath) {
		for (int i = 0; i < _BINARY_EXTENSIONS.length; i++) {
			String binaryExtension = _BINARY_EXTENSIONS[i];

			if (friendlyURLPath.endsWith(binaryExtension)) {
				return true;
			}
		}

		return false;
	}

	protected Router router;

	private static final String[] _BINARY_EXTENSIONS = new String[] {
		".css", ".doc", ".gif", ".jpeg", ".jpg", ".js", ".odp", ".png", ".ppt",
		".tgz", ".xls", ".zip",
	};

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static final String _MAPPING = "waiapp";

	private String _portletId;

}