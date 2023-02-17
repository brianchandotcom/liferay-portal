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

import java.net.URL;

import java.util.Map;

import reactor.core.publisher.Mono;

/**
 * @author Michael Hashimoto
 */
public class JenkinsServer extends BaseServer {

	public String invoke(String jobName, Map<String, String> buildParameters) {
		Mono<String> stringMono = invokeMono(jobName, buildParameters);

		return stringMono.block();
	}

	public Mono<String> invokeMono(
		String jobName, Map<String, String> buildParameters) {

		return httpRequestMono(
			"/job/" + jobName + "/buildWithParameters", null, Method.GET,
			buildParameters, null);
	}

	protected JenkinsServer(URL url) {
		super(url);
	}

}