/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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