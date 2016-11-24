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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.TagSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author     Eduardo Lundgren
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class SpacerTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(StringPool.SPACE);
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return super.doStartTag();
	}

}