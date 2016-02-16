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

package com.liferay.taglib.portletext;

import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconTracker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletConfigurationIconComparator;
import com.liferay.taglib.ui.IconTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IconOptionsTag extends IconTag {

	public List<PortletConfigurationIcon>
		getPortletConfigurationIcons() {

		if (_portletConfigurationIcons != null) {
			return _portletConfigurationIcons;
		}

		_portletConfigurationIcons =
			PortletConfigurationIconTracker.getPortletConfigurationIcons(
				getPortletId(), getPortletRequest(),
				PortletConfigurationIconComparator.INSTANCE);

		return _portletConfigurationIcons;
	}

	public void setDirection(String direction) {
		_direction = direction;
	}

	public void setPortletConfigurationIcons(
		List<PortletConfigurationIcon> portletConfigurationIcons) {

		_portletConfigurationIcons = portletConfigurationIcons;
	}

	public void setShowArrow(boolean showArrow) {
		_showArrow = showArrow;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_direction = "down";
		_portletConfigurationIcons = null;
		_showArrow = true;
	}

	@Override
	protected String getPage() {
		return "/html/taglib/portlet/icon_options/page.jsp";
	}

	protected String getPortletId() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	protected PortletRequest getPortletRequest() {
		return (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	protected PortletResponse getPortletResponse() {
		return (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("liferay-ui:icon:direction", _direction);
		request.setAttribute(
			"liferay-ui:icon:showArrow", String.valueOf(_showArrow));
		request.setAttribute(
			"liferay-ui:icon-options:portletConfigurationIcons",
			getPortletConfigurationIcons());
	}

	private String _direction = "down";
	private List<PortletConfigurationIcon> _portletConfigurationIcons;
	private boolean _showArrow = true;

}