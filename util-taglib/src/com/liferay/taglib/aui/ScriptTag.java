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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.BodyContentWrapper;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.aui.base.BaseScriptTag;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptTag extends BaseScriptTag {

	public static void doTag(
			String position, String use, String bodyContentString,
			BodyContent previousBodyContent, PageContext pageContext)
		throws Exception {

		String previousBodyContentString = null;

		if ((previousBodyContent != null) &&
			!(previousBodyContent instanceof BodyContentWrapper)) {

			// LPS-22413

			previousBodyContentString = previousBodyContent.getString();
		}

		ScriptTag scriptTag = new ScriptTag();

		scriptTag.setPageContext(pageContext);
		scriptTag.setPosition(position);
		scriptTag.setUse(use);

		BodyContent bodyContent = pageContext.pushBody();

		scriptTag.setBodyContent(bodyContent);

		bodyContent.write(bodyContentString);

		pageContext.popBody();

		scriptTag.doEndTag();

		scriptTag.release();

		if (previousBodyContentString != null) {

			// LPS-22413

			previousBodyContent.clear();

			previousBodyContent.append(previousBodyContentString);
		}
	}

	public static void flushScriptData(PageContext pageContext)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ScriptData scriptData = (ScriptData)request.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		request.removeAttribute(WebKeys.AUI_SCRIPT_DATA);

		scriptData.writeTo(request, pageContext.getOut());
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		try {
			String portletId = null;

			Portlet portlet = (Portlet)request.getAttribute(
				WebKeys.RENDER_PORTLET);

			if (portlet != null) {
				portletId = portlet.getPortletId();
			}

			StringBundler bodyContentSB = getBodyContentAsStringBundler();

			String use = getUse();

			if (getSandbox() || (use != null)) {
				StringBundler sb = new StringBundler();

				if (use == null) {
					sb.append("(function() {");
				}

				sb.append("var $ = AUI.$;");
				sb.append("var _ = AUI._;");
				sb.append(bodyContentSB);

				if (use == null) {
					sb.append("})();");
				}

				bodyContentSB = sb;
			}

			if (isPositionInLine()) {
				ScriptData scriptData = new ScriptData();

				scriptData.append(portletId, bodyContentSB, use);

				String page = getPage();

				if (FileAvailabilityUtil.isAvailable(
						pageContext.getServletContext(), page)) {

					PortalIncludeUtil.include(pageContext, page);
				}
				else {
					scriptData.writeTo(request, pageContext.getOut());
				}
			}
			else {
				ScriptData scriptData = (ScriptData)request.getAttribute(
					WebKeys.AUI_SCRIPT_DATA);

				if (scriptData == null) {
					scriptData = new ScriptData();

					request.setAttribute(WebKeys.AUI_SCRIPT_DATA, scriptData);
				}

				scriptData.append(portletId, bodyContentSB, use);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				cleanUp();
			}

			request.removeAttribute(WebKeys.JAVASCRIPT_CONTEXT);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(WebKeys.JAVASCRIPT_CONTEXT, Boolean.TRUE);

		return super.doStartTag();
	}

	@Override
	protected void cleanUp() {
		setPosition(null);
		setUse(null);
	}

}