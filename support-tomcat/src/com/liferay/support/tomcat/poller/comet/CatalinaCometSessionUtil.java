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

import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.comet.CometEvent;

/**
 * @author Edward Han
 */
public class CatalinaCometSessionUtil {

	public static String getSessionId(CometEvent cometEvent) {
		HttpServletRequest request = cometEvent.getHttpServletRequest();

		Object sessionId = request.getAttribute(_CATALINA_COMET_CONNECTION_ID);

		if ((sessionId == null) || !(sessionId instanceof String)) {
			sessionId = PortalUUIDUtil.generate();

			request.setAttribute(_CATALINA_COMET_CONNECTION_ID, sessionId);
		}

		return (String)sessionId;
	}

	private static final String _CATALINA_COMET_CONNECTION_ID =
		"CATALINA_COMET_CONNECTION_ID";

}