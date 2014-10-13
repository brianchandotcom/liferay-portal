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

package com.liferay.taglib.servlet;

import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Shuyang Zhou
 */
public class PipingPageContext extends PageContextWrapper {

	public PipingPageContext(PageContext pageContext, Writer writer) {
		super(pageContext);

		_jspWriter = new PipingJspWriter(writer);
	}

	@Override
	public JspWriter getOut() {
		return _jspWriter;
	}

	private final JspWriter _jspWriter;

}