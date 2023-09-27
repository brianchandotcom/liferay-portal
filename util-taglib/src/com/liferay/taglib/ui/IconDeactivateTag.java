/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.taglib.util.TagResourceBundleUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconDeactivateTag extends IconTag {

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(getServletContext(), _PAGE)) {
			return _PAGE;
		}

		String url = getUrl();

		if (url.startsWith("javascript:")) {
			url = url.substring(11);
		}

		if (url.startsWith(Http.HTTP_WITH_SLASH) ||
			url.startsWith(Http.HTTPS_WITH_SLASH)) {

			url = StringBundler.concat(
				"submitForm(document.hrefFm, '", HtmlUtil.escapeJS(url), "');");
		}

		setMessage("deactivate");
		setUrl(
			StringBundler.concat(
				"javascript:Liferay.Util.openConfirmModal({message: '",
				UnicodeLanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					"are-you-sure-you-want-to-deactivate-this"),
				"', onConfirm: (isConfirmed) => {if (isConfirmed) {", url,
				" } else {self.focus();}}});"));

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/ui/icon_deactivate/page.jsp";

}