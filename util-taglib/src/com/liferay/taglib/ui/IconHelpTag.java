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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Scott Lee
 * @author Shuyang Zhou
 */
public class IconHelpTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}
		else {
			return null;
		}
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		String message = LanguageUtil.get(resourceBundle, getMessage());

		request.setAttribute("liferay-ui:icon-help:id", StringUtil.randomId());
		request.setAttribute("liferay-ui:icon-help:message", message);

		super.setAttributes(request);
	}

	private static final String _PAGE = "/html/taglib/ui/icon_help/page.jsp";

}