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

import com.liferay.portal.kernel.poller.comet.BaseCometRequest;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.comet.CometEvent;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CatalinaCometRequest extends BaseCometRequest {

	public CatalinaCometRequest(CometEvent cometEvent) {
		super(cometEvent.getHttpServletRequest());
	}

	@Override
	public String getParameter(String name) {
		HttpServletRequest request = getRequest();

		return request.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		HttpServletRequest request = getRequest();

		return request.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		HttpServletRequest request = getRequest();

		return request.getParameterNames();
	}

}