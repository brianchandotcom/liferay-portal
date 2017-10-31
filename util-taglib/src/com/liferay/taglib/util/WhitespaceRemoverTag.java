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

package com.liferay.taglib.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class WhitespaceRemoverTag extends BodyTagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(getBodyContentString());
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	protected String getBodyContentString() {
		BodyContent bodyContent = getBodyContent();

		String bodyContentString = StringUtil.trim(bodyContent.getString());

		bodyContentString = StringUtil.removeChars(
			bodyContentString, CharPool.NEW_LINE, CharPool.TAB);

		return bodyContentString;
	}

}