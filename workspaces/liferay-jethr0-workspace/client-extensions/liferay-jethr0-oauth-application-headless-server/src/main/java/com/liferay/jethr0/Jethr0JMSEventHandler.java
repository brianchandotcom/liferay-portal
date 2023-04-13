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

package com.liferay.jethr0;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class Jethr0JMSEventHandler {

	@JmsListener(destination = "${jms.jenkins.event.queue}")
	public void processMessage(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(message);
		}
	}

	private static final Log _log = LogFactory.getLog(
		Jethr0JMSEventHandler.class);

}