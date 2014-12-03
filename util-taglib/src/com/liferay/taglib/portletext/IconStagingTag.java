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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.ui.IconTag;

/**
 * @author Levente Hudák
 */
public class IconStagingTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}

		PortletDisplay portletDisplay =
			(PortletDisplay)pageContext.getAttribute("portletDisplay");

		if (!portletDisplay.isShowStagingIcon()) {
			return null;
		}

		setCssClass("portlet-export-import portlet-export-import-icon");
		setImage("../aui/share");
		setMessage("staging");
		setMethod("get");

		StringBundler sb = new StringBundler(11);

		sb.append("Liferay.Portlet.openWindow('#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', '");
		sb.append(portletDisplay.getId());
		sb.append("', '");
		sb.append(HtmlUtil.escapeJS(portletDisplay.getURLStaging()));
		sb.append("', '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', '");
		sb.append(LanguageUtil.get(request, "staging"));
		sb.append("'); return false;");

		setOnClick(sb.toString());

		setToolTip(false);
		setUrl(portletDisplay.getURLStaging());

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_staging/page.jsp";

}