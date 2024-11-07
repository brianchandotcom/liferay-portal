/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyHTMLRewriterUtil;
import com.liferay.taglib.BaseBodyTagSupport;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Iván Zaera Avellón
 */
public class CSPTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			BodyContent bodyContent = getBodyContent();

			jspWriter.write(
				ContentSecurityPolicyHTMLRewriterUtil.
					rewriteInlineEventHandlers(
						bodyContent.getString(), getRequest(), _recursive));

			return super.doEndTag();
		}
		catch (IOException ioException) {
			throw new JspException(ioException);
		}
	}

	public boolean isRecursive() {
		return _recursive;
	}

	@Override
	public void release() {
		_recursive = false;

		super.release();
	}

	public void setRecursive(boolean recursive) {
		_recursive = recursive;
	}

	private boolean _recursive;

}