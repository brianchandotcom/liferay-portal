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
public class IconMaximizeTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}

		PortletDisplay portletDisplay =
			(PortletDisplay)pageContext.getAttribute("portletDisplay");

		if (!portletDisplay.isShowMaxIcon()) {
			return null;
		}

		setCssClass("portlet-maximize portlet-maximize-icon");
		setImage("../aui/plus");
		setMessage("maximize");
		setMethod("get");

		String onClick =
			"submitForm(document.hrefFm, '".concat(
				portletDisplay.getURLMax()).concat("'); return false;");

		setOnClick(onClick);

		setToolTip(false);
		setUrl(portletDisplay.getURLMax());

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_maximize/page.jsp";

}