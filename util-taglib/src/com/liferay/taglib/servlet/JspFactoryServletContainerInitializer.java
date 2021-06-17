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

import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class JspFactoryServletContainerInitializer
	implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext servletContext)
		throws ServletException {

		PortalLifecycleUtil.register(
			new PortalLifecycle() {

				@Override
				public void portalDestroy() {
				}

				@Override
				public void portalInit() {
					JspFactorySwapper.swap();
				}

			},
			PortalLifecycle.METHOD_INIT);
	}

}