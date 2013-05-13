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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.poller.comet.BaseCometSession;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.comet.CometEvent;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CatalinaCometSession extends BaseCometSession {

	public CatalinaCometSession(CometEvent cometEvent) {
		_cometEvent = cometEvent;
	}

	@Override
	public Object getAttribute(String name) {
		HttpServletRequest request = _cometEvent.getHttpServletRequest();

		HttpSession session = request.getSession();

		return session.getAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object object) {
		HttpServletRequest request = _cometEvent.getHttpServletRequest();

		HttpSession session = request.getSession();

		session.setAttribute(name, object);
	}

	@Override
	protected void doClose() throws SystemException {
		try {
			_cometEvent.close();
		}
		catch (IllegalStateException ise) {
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private CometEvent _cometEvent;

}