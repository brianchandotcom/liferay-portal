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

package com.liferay.support.tomcat.startup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringComparator;

import java.io.File;

import java.util.Arrays;

import org.apache.catalina.startup.HostConfig;

/**
 * <p>
 * Tomcat will always process XML descriptors first, then packaged WARs, and
 * then exploded WARs. However, Tomcat does not have a predictable load order
 * for the XML descriptors or the WARs. It relies on Java's
 * <code>java.io.File.list()</code> implementation which is not predictable.
 * This class overrides several of the deploy methods to ensure that the files
 * are always processed alphabetically (case sensitive).
 * </p>
 *
 * <p>
 * To use this class, modify Tomcat's conf/server.xml. Find the
 * <code>Host</code> element and add the attribute <code>hostConfigClass</code>.
 * </p>
 *
 * <p>
 * See https://issues.liferay.com/browse/LEP-2346.
 * </p>
 *
 * <p>
 * See <code>org.apache.catalina.startup.HostConfig</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalHostConfig extends HostConfig {

	@Override
	protected void deployDescriptors(File configBase, String[] files) {
		super.deployDescriptors(configBase, sortFiles(files));
	}

	@Override
	protected void deployDirectories(File appBase, String[] files) {
		super.deployDirectories(appBase, sortFiles(files));
	}

	@Override
	protected void deployWARs(File appBase, String[] files) {
		super.deployWARs(appBase, sortFiles(files));
	}

	protected String[] sortFiles(String[] files) {
		Arrays.sort(files, new StringComparator(true, true));

		if (_log.isDebugEnabled()) {
			_log.debug("Sort " + files.length + " files");

			for (int i = 0; i < files.length; i++) {
				_log.debug("File " + i + " " + files[i]);
			}
		}

		return files;
	}

	private static Log _log = LogFactoryUtil.getLog(PortalHostConfig.class);

}