/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.servlet.FileAvailabilityUtil;
import com.liferay.portal.kernel.servlet.taglib.BodyContentWrapper;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;
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
			String position, String require, String use,
			String bodyContentString, BodyContent previousBodyContent,
			PageContext pageContext)
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
		scriptTag.setRequire(require);
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

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		httpServletRequest.removeAttribute(WebKeys.AUI_SCRIPT_DATA);

		scriptData.writeTo(pageContext.getOut());
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		try {
			String portletId = null;

			Portlet portlet = (Portlet)httpServletRequest.getAttribute(
				WebKeys.RENDER_PORTLET);

			if (portlet != null) {
				portletId = portlet.getPortletId();
			}

			String require = getRequire();
			String use = getUse();

			if ((use != null) && (require != null)) {
				throw new JspException(
					"Attribute \"use\" cannot be used with \"require\"");
			}

			StringBundler bodyContentSB = getBodyContentAsStringBundler();

			if (getSandbox() || (require != null) || (use != null)) {
				StringBundler sb = new StringBundler(4);

				if ((require == null) && (use == null)) {
					sb.append("(function() {");
				}

				sb.append("var $ = AUI.$;var _ = AUI._;");
				sb.append(bodyContentSB);

				if ((require == null) && (use == null)) {
					sb.append("})();");
				}

				bodyContentSB = sb;
			}

			if (isPositionInLine()) {
				ScriptData scriptData = new ScriptData();

				if (require != null) {
					scriptData.append(
						portletId, bodyContentSB, require,
						ScriptData.ModulesType.ES6);
				}
				else {
					scriptData.append(
						portletId, bodyContentSB, use,
						ScriptData.ModulesType.AUI);
				}

				String page = getPage();

				if (FileAvailabilityUtil.isAvailable(
						pageContext.getServletContext(), page)) {

					PortalIncludeUtil.include(pageContext, page);
				}
				else {
					scriptData.writeTo(pageContext.getOut());
				}
			}
			else {
				ScriptData scriptData =
					(ScriptData)httpServletRequest.getAttribute(
						WebKeys.AUI_SCRIPT_DATA);

				if (scriptData == null) {
					scriptData = new ScriptData();

					httpServletRequest.setAttribute(
						WebKeys.AUI_SCRIPT_DATA, scriptData);
				}

				if (require != null) {
					scriptData.append(
						portletId, bodyContentSB, require,
						ScriptData.ModulesType.ES6);
				}
				else {
					scriptData.append(
						portletId, bodyContentSB, use,
						ScriptData.ModulesType.AUI);
				}
			}

			return EVAL_PAGE;
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
		finally {
			cleanUp();

			httpServletRequest.removeAttribute(WebKeys.JAVASCRIPT_CONTEXT);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		httpServletRequest.setAttribute(
			WebKeys.JAVASCRIPT_CONTEXT, Boolean.TRUE);

		return super.doStartTag();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		setPosition(null);
		setRequire(null);
		setUse(null);
	}

}