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

package com.liferay.util.servlet.filters;

import com.liferay.portal.kernel.servlet.Header;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class CacheResponseUtil {

	public static void setHeaders(
		HttpServletResponse httpServletResponse,
		Map<String, Set<Header>> headers) {

		if (httpServletResponse.isCommitted()) {
			return;
		}

		for (Map.Entry<String, Set<Header>> entry : headers.entrySet()) {
			String key = entry.getKey();

			boolean first = true;

			for (Header header : entry.getValue()) {
				if (first) {
					header.setToResponse(key, httpServletResponse);

					first = false;
				}
				else {
					header.addToResponse(key, httpServletResponse);
				}
			}
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse,
			CacheResponseData cacheResponseData)
		throws IOException {

		setHeaders(httpServletResponse, cacheResponseData.getHeaders());

		httpServletResponse.setContentType(cacheResponseData.getContentType());

		ServletResponseUtil.write(
			httpServletResponse, cacheResponseData.getByteBuffer());
	}

}