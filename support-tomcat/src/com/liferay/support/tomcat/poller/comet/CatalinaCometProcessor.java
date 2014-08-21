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

package com.liferay.support.tomcat.poller.comet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.comet.CometHandler;
import com.liferay.portal.kernel.poller.comet.CometHandlerPoolUtil;
import com.liferay.portal.kernel.poller.comet.CometSession;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CatalinaCometProcessor
	extends HttpServlet implements CometProcessor {

	@Override
	public void destroy() {
		super.destroy();

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy comet processor");
		}
	}

	@Override
	public void event(CometEvent cometEvent) throws ServletException {
		try {
			doEvent(cometEvent);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize comet processor");
		}

		String cometHandlerImpl = servletConfig.getInitParameter(
			"comet-handler-impl");

		try {
			_cometHandler = (CometHandler)InstanceFactory.newInstance(
				cometHandlerImpl);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ServletException(e);
		}
	}

	protected void closeConnection(
			CometEvent cometEvent, HttpServletRequest request,
			HttpSession session)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Close comet connection " + session.getId());
		}

		String sessionId = CatalinaCometSessionUtil.getSessionId(cometEvent);

		CometHandlerPoolUtil.closeCometHandler(sessionId);

		cometEvent.close();
	}

	protected void doEvent(CometEvent cometEvent) throws Exception {
		CometEvent.EventType eventType = cometEvent.getEventType();

		HttpServletRequest request = cometEvent.getHttpServletRequest();

		HttpSession session = request.getSession();

		if (_log.isDebugEnabled()) {
			_log.debug(session.getId() + " " + eventType);
		}

		if (eventType.equals(CometEvent.EventType.BEGIN)) {
			startCometHandler(cometEvent, request, session);
		}
		else if (eventType.equals(CometEvent.EventType.END) ||
				 eventType.equals(CometEvent.EventType.ERROR)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					session.getId() + " " + cometEvent.getEventSubType());
			}

			closeConnection(cometEvent, request, session);
		}
		else if (eventType.equals(CometEvent.EventType.READ)) {
			readData(cometEvent, request, session);
		}
	}

	protected void readData(
			CometEvent cometEvent, HttpServletRequest request,
			HttpSession session)
		throws Exception {

		InputStream inputStream = request.getInputStream();

		byte[] buffer = new byte[512];

		StringBundler sb = new StringBundler();

		while (inputStream.available() > 0) {
			int read = inputStream.read(buffer);

			if (read > 0) {
				String dataSegment = new String(buffer);

				sb.append(dataSegment);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						session.getId() + " " + cometEvent.getEventSubType());
				}
			}
		}

		String data = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Read " + data);
		}

		CometHandler cometHandler = CometHandlerPoolUtil.getCometHandler(
			session.getId());

		cometHandler.receiveData(data);
	}

	protected void startCometHandler(
			CometEvent cometEvent, HttpServletRequest request,
			HttpSession session)
		throws Exception {

		CometSession cometSession = new CatalinaCometSession(cometEvent);

		cometSession.setCometRequest(new CatalinaCometRequest(cometEvent));
		cometSession.setCometResponse(new CatalinaCometResponse(cometEvent));

		String sessionId = CatalinaCometSessionUtil.getSessionId(cometEvent);

		cometSession.setSessionId(sessionId);

		CometHandler cometHandler = _cometHandler.clone();

		CometHandlerPoolUtil.startCometHandler(cometSession, cometHandler);

		HttpServletResponse response = cometEvent.getHttpServletResponse();

		response.setContentType(ContentTypes.TEXT_PLAIN_UTF8);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CatalinaCometProcessor.class);

	private CometHandler _cometHandler;

}