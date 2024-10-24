/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyHTMLRewriterUtil;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProviderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.BaseBodyTagSupport;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Iván Zaera Avellón
 */
public class ContentSecurityPolicyTag
	extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		String nonce = ContentSecurityPolicyNonceProviderUtil.getNonce(
			getRequest());

		if (Validator.isBlank(nonce)) {
			return super.doEndTag();
		}

		try {
			JspWriter jspWriter = pageContext.getOut();

			BodyContent bodyContent = getBodyContent();

			jspWriter.write(
				ContentSecurityPolicyHTMLRewriterUtil.
					rewriteInlineEventHandlers(bodyContent.getString(), nonce));

			return super.doEndTag();
		}
		catch (IOException ioException) {
			throw new JspException(ioException);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		if (Validator.isBlank(
				ContentSecurityPolicyNonceProviderUtil.getNonce(
					getRequest()))) {

			return EVAL_BODY_INCLUDE;
		}

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