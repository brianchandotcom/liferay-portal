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

package com.liferay.util.transport;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.InputStream;

import java.net.DatagramPacket;

import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class MulticastDatagramHandler implements DatagramHandler {

	public MulticastDatagramHandler(boolean gzipData, boolean shortData) {
		_gzipData = gzipData;
		_shortData = shortData;
	}

	@Override
	public void errorReceived(Throwable t) {
		_log.error(t, t);
	}

	@Override
	public void process(DatagramPacket packet) {
		byte[] bytes = packet.getData();

		if (_gzipData) {
			try {
				bytes = getUnzippedBytes(bytes);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_shortData) {
			byte[] temp = new byte[96];

			System.arraycopy(bytes, 0, temp, 0, 96);

			bytes = temp;
		}

		StringBundler sb = new StringBundler(4);

		sb.append("[");
		sb.append(packet.getSocketAddress());
		sb.append("] ");
		sb.append(new String(bytes));

		if (_log.isInfoEnabled()) {
			_log.info(sb);
		}
	}

	protected byte[] getUnzippedBytes(byte[] bytes) throws Exception {
		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream(
			bytes.length);

		try (InputStream is = new GZIPInputStream(
				new UnsyncByteArrayInputStream(bytes))) {

			byte[] buffer = new byte[1500];
			int c = 0;

			while (true) {
				if (c == -1) {
					break;
				}

				c = is.read(buffer, 0, 1500);

				if (c != -1) {
					ubaos.write(buffer, 0, c);
				}
			}
		}

		ubaos.flush();
		ubaos.close();

		return ubaos.toByteArray();
	}

	private static final Log _log = LogFactory.getLog(
		MulticastDatagramHandler.class);

	private final boolean _gzipData;
	private final boolean _shortData;

}