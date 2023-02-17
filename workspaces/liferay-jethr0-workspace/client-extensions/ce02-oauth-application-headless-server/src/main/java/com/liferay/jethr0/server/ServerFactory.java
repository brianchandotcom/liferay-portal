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

package com.liferay.jethr0.server;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class ServerFactory {

	public static JenkinsServer newJenkinsServer(String jenkinsHostname) {
		try {
			URL url = new URL("https://" + jenkinsHostname);

			if (_jenkinsServers.containsKey(url)) {
				return _jenkinsServers.get(url);
			}

			JenkinsServer jenkinsServer = new JenkinsServer(url);

			_jenkinsServers.put(url, jenkinsServer);

			return jenkinsServer;
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static final Map<URL, JenkinsServer> _jenkinsServers =
		new HashMap<>();

}