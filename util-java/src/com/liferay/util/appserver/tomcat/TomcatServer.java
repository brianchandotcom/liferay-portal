/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.util.appserver.tomcat;

import com.liferay.util.appserver.EmbeddedApplicationServer;

/**
 *
 * @author Miguel Pastor
 */
public abstract class TomcatServer implements EmbeddedApplicationServer {

	public void restart() {
		stop();

		start();
	}

	public void start() {
		start(null, null);
	}

	public void start(Integer port) {
		start(null, port);
	}

	public void start(String hostName, Integer port) {
		String host = hostName != null ? hostName : DEFAULT_HOST;
		Integer p = port != null ? port : DEFAULT_PORT;

		this.doStart(host, p);
	}

	public abstract void stop();

	protected abstract void doStart(String hostName, int port);

}