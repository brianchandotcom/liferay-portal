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

import com.liferay.kernel.servlet.taglib.ViewExtension;
import com.liferay.kernel.servlet.taglib.ViewExtensionUtil;
import com.liferay.taglib.TagSupport;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import javax.servlet.jsp.JspException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;

/**
 * @author Carlos Sierra Andrés
 */
public class ExtensionTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		List<ViewExtension> viewExtensions =
			ViewExtensionUtil.getViewExtensions(getExtensionId());

		if (viewExtensions == null || viewExtensions.isEmpty()) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getExtensionId() {
		return _extensionId;
	}

	public void setExtensionId(String extensionId) {
		_extensionId = extensionId;
	}

	protected HttpServletRequest getRequest() {
		ServletRequest request = pageContext.getRequest();

		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalStateException("This can only be used from HTTP");
		}

		return (HttpServletRequest)request;
	}

	protected HttpServletResponse getResponse() throws IOException {
		ServletResponse response = pageContext.getResponse();

		if (!(response instanceof HttpServletResponse)) {
			throw new IllegalStateException("This can only be used from HTTP");
		}

		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		return new HttpServletResponseWrapper(httpServletResponse) {

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return new ServletOutputStream() {

					@Override
					public void write(int b) throws IOException {
						JspWriter out = pageContext.getOut();

						out.write(b);
					}
				};
			}

			@Override
			public PrintWriter getWriter() throws IOException {
				return new PrintWriter(pageContext.getOut(), true);
			}

		};
	}

	private String _extensionId;

}