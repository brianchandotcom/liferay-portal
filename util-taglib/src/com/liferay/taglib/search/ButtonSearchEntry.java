/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ButtonSearchEntry extends TextSearchEntry {

	@Override
	public Object clone() {
		ButtonSearchEntry buttonSearchEntry = new ButtonSearchEntry();

		BeanPropertiesUtil.copyProperties(this, buttonSearchEntry);

		return buttonSearchEntry;
	}

	@Override
	public void print(
			Writer writer, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String id = StringUtil.randomId();

		writer.write("<input id=\"");
		writer.write(id);
		writer.write("\" type=\"button\" value=\"");
		writer.write(getName());
		writer.write("\">");

		writer.write("<script");
		writer.write(
			ContentSecurityPolicyNonceProviderUtil.getNonce(
				httpServletRequest));
		writer.write(">document.getElementById('");
		writer.write(id);
		writer.write("').onclick=function() {");
		writer.write(getHref());
		writer.write("}</script>");
	}

}