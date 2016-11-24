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

import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.ui.IconTag;

/**
 * @author     Brian Wing Shun Chan
 * @author     Shuyang Zhou
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class IconBackTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}

		PortletDisplay portletDisplay =
			(PortletDisplay)pageContext.getAttribute("portletDisplay");

		if (!portletDisplay.isShowBackIcon()) {
			return null;
		}

		setCssClass("portlet-back portlet-back-icon");
		setImage("../portlet/back");
		setMessage("back");
		setToolTip(false);
		setUrl(portletDisplay.getURLBack());

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_back/page.jsp";

}