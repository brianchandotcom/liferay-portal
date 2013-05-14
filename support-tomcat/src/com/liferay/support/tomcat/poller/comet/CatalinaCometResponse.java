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

package com.liferay.support.tomcat.poller.comet;

import com.liferay.portal.kernel.poller.comet.CometException;
import com.liferay.portal.kernel.poller.comet.CometResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CatalinaCometResponse implements CometResponse {

	public CatalinaCometResponse(CometEvent cometEvent) {
		_response = cometEvent.getHttpServletResponse();
	}

	@Override
	public void close() {
		synchronized (this) {
			_open = false;
		}
	}

	@Override
	public boolean isOpen() {
		return _open;
	}

	@Override
	public void writeData(byte[] data) throws CometException {
		synchronized (this) {
			if (!_open) {
				throw new CometException("Stream is closed");
			}

			try {
				OutputStream outputStream = _response.getOutputStream();

				outputStream.write(data);

				outputStream.flush();
			}
			catch (IOException ioe) {
				throw new CometException(ioe);
			}
		}
	}

	@Override
	public void writeData(String data) throws CometException {
		synchronized (this) {
			if (!_open) {
				throw new CometException("Writer is closed");
			}

			try {
				Writer writer = _response.getWriter();

				writer.write(data);

				writer.flush();
			}
			catch (IOException ioe) {
				throw new CometException(ioe);
			}
		}
	}

	private boolean _open = true;
	private HttpServletResponse _response;

}