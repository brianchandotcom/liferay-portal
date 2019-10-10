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

	public static boolean registerCloseCallback(
		PageContext pageContext, Runnable runnable) {

		if (!_isAutoCloseable(pageContext)) {
			return false;
		}

		List<Runnable> runnables = _runnables.computeIfAbsent(
			pageContext, pc -> new ArrayList<>());

		runnables.add(runnable);

		return true;
	}

	public static void runAutoCloseRunnables(PageContext pageContext) {
		List<Runnable> runnables = _runnables.remove(pageContext);

		if (runnables == null) {
			return;
		}

		runnables.forEach(Runnable::run);
	}

	private static boolean _isAutoCloseable(PageContext pageContext) {
		Object autoCloseable = pageContext.getAttribute(
			AutoClosePageContextRegistry.AUTO_CLOSEABLE);

		if ((autoCloseable != null) && (Boolean)autoCloseable) {
			return true;
		}

		return false;
	}

	private static final Map<PageContext, List<Runnable>> _runnables =
		new ConcurrentHashMap<>();

}