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

package com.liferay.taglib.portletext;

import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.taglib.ui.IconTag;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconMinimizeTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}

		PortletDisplay portletDisplay =
			(PortletDisplay)pageContext.getAttribute("portletDisplay");

		if (!portletDisplay.isShowMinIcon()) {
			return null;
		}

		setCssClass("portlet-minimize portlet-minimize-icon");

		String image = null;
		String message = null;

		if (portletDisplay.isStateMin()) {
			image = "resize-vertical";
			message = "restore";
		}
		else {
			image = "minus";
			message = "minimize";
		}

		setImage("../aui/".concat(image));
		setMessage(message);

		String onClick =
			"Liferay.Portlet.minimize('#p_p_id_".concat(
				portletDisplay.getId()).concat("_', this); return false;");

		setOnClick(onClick);

		setToolTip(false);
		setUrl(portletDisplay.getURLMin());

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_minimize/page.jsp";

}