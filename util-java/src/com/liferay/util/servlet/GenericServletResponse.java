/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.servlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.servlet.ServletOutputStreamAdapter;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class GenericServletResponse extends HttpServletResponseWrapper {

	public GenericServletResponse(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
	}

	public int getContentLength() {
		if (_contentLength > Integer.MAX_VALUE) {
			return -1;
		}

		return (int)_contentLength;
	}

	public long getContentLengthLong() {
		return _contentLength;
	}

	@Override
	public String getContentType() {
		return _contentType;
	}

	public byte[] getData() {
		return _unsyncByteArrayOutputStream.toByteArray();
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return new ServletOutputStreamAdapter(_unsyncByteArrayOutputStream);
	}

	@Override
	public PrintWriter getWriter() {
		return UnsyncPrintWriterPool.borrow(
			getOutputStream(), getCharacterEncoding());
	}

	@Override
	public void setContentLength(int length) {
		super.setContentLength(length);

		_contentLength = length;
	}

	@Override
	public void setContentLengthLong(long length) {
		super.setContentLengthLong(length);

		_contentLength = length;
	}

	@Override
	public void setContentType(String contentType) {
		super.setContentType(contentType);

		_contentType = contentType;
	}

	private long _contentLength;
	private String _contentType;
	private final UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream =
		new UnsyncByteArrayOutputStream();

}