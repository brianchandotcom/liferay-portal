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

package com.liferay.util.axis;

import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.cache.MethodCache;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class AxisCleanUpFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		try {
			processFilter(
				AxisCleanUpFilter.class, request, response, filterChain);
		}
		finally {
			try {
				ThreadLocal<?> cacheThreadLocal =
					(ThreadLocal<?>)_CACHE_FIELD.get(null);

				if (cacheThreadLocal != null) {
					cacheThreadLocal.remove();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static final Field _CACHE_FIELD;

	private static final Log _log = LogFactoryUtil.getLog(
		AxisCleanUpFilter.class);

	static {
		try {
			_CACHE_FIELD = ReflectionUtil.getDeclaredField(
				MethodCache.class, "cache");
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

}