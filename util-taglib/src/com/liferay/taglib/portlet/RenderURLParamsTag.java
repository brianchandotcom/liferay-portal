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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class RenderURLParamsTag extends TagSupport {

	public static String doTag(
			PortletURL portletURL, String varImpl, PageContext pageContext)
		throws Exception {

		if (portletURL == null) {
			portletURL = (PortletURL)pageContext.getAttribute(varImpl);
		}

		String params = StringPool.BLANK;

		if (portletURL != null) {
			params = _toParamsString(portletURL, pageContext);

			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(params);
		}

		return params;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #doTag(PortletURL, String,
	 *             PageContext)}
	 */
	@Deprecated
	public static String doTag(String varImpl, PageContext pageContext)
		throws Exception {

		return doTag(null, varImpl, pageContext);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(_portletURL, _varImpl, pageContext);

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

	private static String _toParamsString(
			PortletURL portletURL, PageContext pageContext)
		throws Exception {

		StringBundler sb = new StringBundler();

		String url = portletURL.toString();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (ParamUtil.getBoolean(request, "wsrp")) {
			int x = url.indexOf("/wsrp_rewrite");

			url = url.substring(0, x);
		}

		String queryString = HttpUtil.getQueryString(url);

		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

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

					sb.append("<input name=\"");
					sb.append(key);
					sb.append("\" type=\"hidden\" value=\"");
					sb.append(HtmlUtil.escapeAttribute(value));
					sb.append("\" />");
				}
			}
		}

		return sb.toString();
	}

	private PortletURL _portletURL;
	private String _varImpl;

}