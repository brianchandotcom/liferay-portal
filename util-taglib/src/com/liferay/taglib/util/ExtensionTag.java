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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.TagSupport;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Carlos Sierra Andrés
 */
public class ExtensionTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		List<ViewExtension> viewExtensions =
			ViewExtensionUtil.getViewExtensions(getExtensionId());

		if ((viewExtensions != null) && !viewExtensions.isEmpty()) {
			for (ViewExtension viewExtension : viewExtensions) {
				try {
					viewExtension.include(getRequest(), getResponse());
				}
				catch (Exception e) {
					_log.error(e.getLocalizedMessage(), e);
				}
			}
		}

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
		return (HttpServletRequest)pageContext.getRequest();
	}

	protected HttpServletResponse getResponse() throws IOException {
		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

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

	private static Log _log = LogFactoryUtil.getLog(ExtensionTag.class);

	private String _extensionId;

}