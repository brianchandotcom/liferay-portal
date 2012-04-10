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

/**
 *
 */

package com.liferay.util.appserver;

/**
 * @author Miguel Pastor
 *
 */
public interface EmbeddedApplicationServer {

	public static final String DEFAULT_HOST = "localhost";

	public static final Integer DEFAULT_PORT = Integer.valueOf(8080);

	public static final String LIFERAY_DEFAULT_CONTEXT = "/";

	/**
	 * Restart the container (usually by calling stop/start)
	 */
	void restart();

	/**
	 * Start the container at the defaults values for host and port
	 *
	 * @see #DEFAULT_HOST
	 * @see #DEFAULT_PORT
	 */
	void start();

	/**
	 * Start the container at the specified port
	 *
	 * @param port
	 *            Port number where the container will be listening for the
	 *            requests
	 */
	void start(Integer port);

	/**
	 * Start the container at the specified host and port
	 *
	 * @param hostName
	 *            Name of the host
	 * @param port
	 *            Port number where the container will be listening for the
	 *            requests
	 */
	void start(String hostName, Integer port);

	/**
	 * Stops the running container
	 */
	void stop();

}