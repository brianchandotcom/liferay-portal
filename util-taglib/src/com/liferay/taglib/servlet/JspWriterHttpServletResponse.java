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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author     Carlos Sierra Andrés
 * @deprecated As of 7.0.0, replaced by {@link PipingServletResponse}
 */
@Deprecated
public class JspWriterHttpServletResponse extends HttpServletResponseWrapper {

	public JspWriterHttpServletResponse(PageContext pageContext) {
		super((HttpServletResponse)pageContext.getResponse());

		_pageContext = pageContext;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return new ServletOutputStream() {

			@Override
			public void write(int b) throws IOException {
				JspWriter jspWriter = _pageContext.getOut();

				jspWriter.write(b);
			}

		};
	}

	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(_pageContext.getOut(), true);
	}

	private final PageContext _pageContext;

}