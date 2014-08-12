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

package com.liferay.util.axis;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPSender;

/**
 * @author Brian Wing Shun Chan
 */
public class SimpleHTTPSender extends HTTPSender {

	public SimpleHTTPSender() {
		String regexp = SystemProperties.get(
			SimpleHTTPSender.class.getName() + ".regexp.pattern");

		if (Validator.isNotNull(regexp)) {
			_pattern = Pattern.compile(regexp);
		}
		else {
			_pattern = null;
		}
	}

	@Override
	public void invoke(MessageContext messageContext) throws AxisFault {
		String url = messageContext.getStrProp(MessageContext.TRANS_URL);

		Matcher matcher = null;

		if (_pattern != null) {
			matcher = _pattern.matcher(url);
		}

		if ((matcher != null) && matcher.matches()) {
			if (_log.isDebugEnabled()) {
				_log.debug("A match was found for " + url);
			}

			_invoke(messageContext, url);
		}
		else {
			super.invoke(messageContext);
		}
	}

	private void _invoke(MessageContext messageContext, String url)
		throws AxisFault {

		try {
			String userName = messageContext.getUsername();
			String password = messageContext.getPassword();

			if ((userName != null) && (password != null)) {
				Authenticator.setDefault(
					new SimpleAuthenticator(userName, password));
			}

			URL urlObj = new URL(url);

			URLConnection urlConnection = urlObj.openConnection();

			_writeToConnection(urlConnection, messageContext);
			_readFromConnection(urlConnection, messageContext);
		}
		catch (Exception e) {
			throw AxisFault.makeFault(e);
		}
		finally {
			Authenticator.setDefault(null);
		}
	}

	private void _readFromConnection(
			URLConnection urlConnection, MessageContext messageContext)
		throws Exception {

		HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;

		InputStream inputStream = httpURLConnection.getErrorStream();

		if (inputStream == null) {
			inputStream = urlConnection.getInputStream();
		}

		inputStream = new UnsyncBufferedInputStream(inputStream, 8192);

		String contentType = urlConnection.getContentType();
		String contentLocation = urlConnection.getHeaderField(
			"Content-Location");

		Message message = new Message(
			inputStream, false, contentType, contentLocation);

		message.setMessageType(Message.RESPONSE);

		messageContext.setResponseMessage(message);
	}

	private void _writeToConnection(
			URLConnection urlConnection, MessageContext messageContext)
		throws Exception {

		urlConnection.setDoOutput(true);

		Message message = messageContext.getRequestMessage();

		String contentType = message.getContentType(
			messageContext.getSOAPConstants());

		urlConnection.setRequestProperty("Content-Type", contentType);

		if (messageContext.useSOAPAction()) {
			urlConnection.setRequestProperty(
				"SOAPAction", messageContext.getSOAPActionURI());
		}

		OutputStream outputStream = new UnsyncBufferedOutputStream(
			urlConnection.getOutputStream(), 8192);

		message.writeTo(outputStream);

		outputStream.flush();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SimpleHTTPSender.class);

	private final Pattern _pattern;

}