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

package com.liferay.util.servlet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 6.2.0, moved to {@link
 *             com.liferay.portal.kernel.servlet.DynamicServletRequest}
 */
@Deprecated
public class DynamicServletRequest
	extends com.liferay.portal.kernel.servlet.DynamicServletRequest {

	public DynamicServletRequest(HttpServletRequest request) {
		super(request);
	}

	public DynamicServletRequest(HttpServletRequest request, boolean inherit) {
		super(request, inherit);
	}

	public DynamicServletRequest(
		HttpServletRequest request, Map<String, String[]> params) {

		super(request, params);
	}

	public DynamicServletRequest(
		HttpServletRequest request, Map<String, String[]> params,
		boolean inherit) {

		super(request, params, inherit);
	}

}