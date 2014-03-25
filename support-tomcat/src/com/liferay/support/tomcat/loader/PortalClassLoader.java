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

package com.liferay.support.tomcat.loader;

import org.apache.catalina.loader.WebappClassLoader;

/**
 * <p>
 * See sample-struts-liferay-portlet.war. Add META-INF/context.xml to any WAR
 * and set the loaderClass attribute to reference this class. This will allow
 * that WAR to use the portal's class loader.
 * </p>
 *
 * <p>
 * See https://issues.liferay.com/browse/LEP-2346.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalClassLoader extends WebappClassLoader {

	public PortalClassLoader(ClassLoader parent) {
		super(PortalClassLoaderFactory.getClassLoader());
	}

}