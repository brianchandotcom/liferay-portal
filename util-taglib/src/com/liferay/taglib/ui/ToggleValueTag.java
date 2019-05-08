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

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SessionClicks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class ToggleValueTag extends TagSupport {

	public static void doTag(
			String id, String defaultValue, PageContext pageContext)
		throws Exception {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		String value = SessionClicks.get(
			httpServletRequest, id, StringPool.BLANK);

		if (value.equals(StringPool.BLANK)) {
			value = defaultValue;
		}

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(value);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(_id, _defaultValue, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setDefaultValue(String defaultValue) {
		_defaultValue = defaultValue;
	}

	@Override
	public void setId(String id) {
		_id = id;
	}

	private String _defaultValue = "block";
	private String _id;

}