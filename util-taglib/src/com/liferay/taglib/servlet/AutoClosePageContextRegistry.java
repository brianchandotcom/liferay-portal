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

package com.liferay.taglib.servlet;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.jsp.PageContext;

/**
 * @author Matthew Tambara
 */
public class AutoClosePageContextRegistry {

	public static final String AUTO_CLOSEABLE =
		AutoClosePageContextRegistry.class.getName() + "_autoCloseable";

	public static void registerCloseCallback(
		PageContext pageContext, Runnable runnable) {

		Object autoCloseable = pageContext.getAttribute(
			AutoClosePageContextRegistry.AUTO_CLOSEABLE);

		if ((autoCloseable == null) || Boolean.FALSE.equals(autoCloseable)) {
			return;
		}

		List<Runnable> runnables = _runnables.computeIfAbsent(
			pageContext, pc -> new ArrayList<>());

		runnables.add(runnable);
	}

	public static void runAutoCloseRunnables(PageContext pageContext) {
		List<Runnable> runnables = _runnables.remove(pageContext);

		if (runnables == null) {
			return;
		}

		runnables.forEach(Runnable::run);
	}

	private static final Map<PageContext, List<Runnable>> _runnables;

	static {
		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		if (AutoClosePageContextRegistry.class.getClassLoader() ==
				portalClassLoader) {

			_runnables = new ConcurrentHashMap<>();
		}
		else {
			try {
				Class<?> portalDeclaringClass = portalClassLoader.loadClass(
					AutoClosePageContextRegistry.class.getName());

				Field field = portalDeclaringClass.getDeclaredField(
					"_runnables");

				field.setAccessible(true);

				_runnables = (Map<PageContext, List<Runnable>>)field.get(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new ExceptionInInitializerError(roe);
			}
		}
	}

}