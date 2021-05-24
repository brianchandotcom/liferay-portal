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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.io.WriterOutputStream;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletOutputStreamAdapter;
import com.liferay.portal.kernel.util.ServerDetector;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Shuyang Zhou
 */
public class PipingServletResponseFactory {

	public static HttpServletResponse createPipingServletResponse(
		PageContext pageContext) {

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		JspWriter jspWriter = pageContext.getOut();

		if (ServerDetector.isWebLogic()) {

			// This optimization cannot be applied to WebLogic because WebLogic
			// relies on the WriterOutputStream bridging logic insde
			// getOutputStream().

			// WebLogic's weblogic.servlet.internal.DelegateChunkWriter#
			// getWriter() always builds its writer on top of
			// HttpServletResponse#getOutputStream() rather than relying on
			// the HttpServletResponse#getWriter().

			// In order to avoid the potential heavy
			// BufferCacheServletResponse#getBufferSize() call, we
			// preadapt JspWriter to ServletOutputStream using
			// JspWriter#getBufferSize() rather than the
			// HttpServletResponse#getBufferSize().

			return new PipingServletResponse(
				httpServletResponse,
				new ServletOutputStreamAdapter(
					new WriterOutputStream(
						jspWriter, httpServletResponse.getCharacterEncoding(),
						jspWriter.getBufferSize(), true)));
		}

		if (!(pageContext instanceof PageContextWrapper) ||
			(jspWriter instanceof BodyContent)) {

			// This optimization cannot be applied to a page context with a
			// pushed body

			return new PipingServletResponse(httpServletResponse, jspWriter);
		}

		if (!ServerDetector.isTomcat()) {
			try {
				jspWriter.flush();
			}
			catch (IOException ioException) {
				ReflectionUtil.throwException(ioException);
			}
		}

		return httpServletResponse;
	}

}