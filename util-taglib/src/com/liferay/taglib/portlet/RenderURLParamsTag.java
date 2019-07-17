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

package com.liferay.taglib.portlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.servlet.PipingPageContext;

import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class RenderURLParamsTag extends TagSupport {

	public static String doTag(PortletURL portletURL, PageContext pageContext)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		_doTag(
			portletURL, null,
			new PipingPageContext(pageContext, unsyncStringWriter));

		return unsyncStringWriter.toString();
	}

	public static String doTag(String varImpl, PageContext pageContext)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		_doTag(
			null, varImpl,
			new PipingPageContext(pageContext, unsyncStringWriter));

		return unsyncStringWriter.toString();
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			_doTag(_portletURL, _varImpl, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setVarImpl(String varImpl) {
		_varImpl = varImpl;
	}

	private static void _doTag(
			PortletURL portletURL, String varImpl, PageContext pageContext)
		throws Exception {

		if (portletURL == null) {
			portletURL = (PortletURL)pageContext.getAttribute(varImpl);
		}

		if (portletURL != null) {
			_writeParamsString(portletURL, pageContext);
		}
	}

	private static void _writeParamsString(
			PortletURL portletURL, PageContext pageContext)
		throws Exception {

		String url = portletURL.toString();

		JspWriter jspWriter = pageContext.getOut();

		String[] parameters = StringUtil.split(
			HttpUtil.getQueryString(url), CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				if (ArrayUtil.isNotEmpty(kvp)) {
					String key = kvp[0];

					String value = StringPool.BLANK;

					if (kvp.length > 1) {
						value = kvp[1];
					}

					value = HttpUtil.decodeURL(value);

					jspWriter.write("<input name=\"");
					jspWriter.write(key);
					jspWriter.write("\" type=\"hidden\" value=\"");
					jspWriter.write(HtmlUtil.escapeAttribute(value));
					jspWriter.write("\" />");
				}
			}
		}
	}

	private PortletURL _portletURL;
	private String _varImpl;

}