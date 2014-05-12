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

import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconDeactivateTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(servletContext, _PAGE)) {
			return _PAGE;
		}

		String url = getUrl();

		if (url.startsWith("javascript:")) {
			url = url.substring(11);
		}

		if (url.startsWith(Http.HTTP_WITH_SLASH) ||
			url.startsWith(Http.HTTPS_WITH_SLASH)) {

			url =
				"submitForm(document.hrefFm, '".concat(
					HttpUtil.encodeURL(url)).concat("');");
		}

		StringBundler sb = new StringBundler(5);

		sb.append("javascript:if (confirm('");
		sb.append(
			UnicodeLanguageUtil.get(
				pageContext, "are-you-sure-you-want-to-deactivate-this"));
		sb.append("')) { ");
		sb.append(url);
		sb.append(" } else { self.focus(); }");

		url = sb.toString();

		setIconCssClass("icon-ban-circle");
		setMessage("deactivate");
		setUrl(url);

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/ui/icon_deactivate/page.jsp";

}