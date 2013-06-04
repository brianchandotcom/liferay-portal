/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.taglib.aui.base.BaseSpacerTag;

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 */
public class SpacerTag extends BaseSpacerTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(CharPool.SPACE);
		}
		catch (Exception e) {
			throw new JspException(e);
		}

		return super.doStartTag();
	}

}