/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.taglib.BaseBodyTagSupport;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Iván Zaera Avellón
 */
public class OnTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.print("<script");
			jspWriter.print(
				ContentSecurityPolicyNonceProviderUtil.getNonceAttribute(
					getRequest()));
			jspWriter.print(StringPool.GREATER_THAN);
			jspWriter.print(
				"Liferay.Util.findEventSourceNode(document.currentScript).");
			jspWriter.print("on");
			jspWriter.print(_event);
			jspWriter.print(" = function(event) {");

			BodyContent bodyContent = getBodyContent();

			jspWriter.print(bodyContent.getString());

			jspWriter.print("};");
			jspWriter.print("</script>");

			return super.doEndTag();
		}
		catch (IOException ioException) {
			throw new JspException(ioException);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	public String getEvent() {
		return _event;
	}

	@Override
	public void release() {
		_event = null;

		super.release();
	}

	public void setEvent(String event) {
		_event = event;
	}

	private String _event;

}