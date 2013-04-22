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

package com.liferay.portalweb.portal.evaluatelog;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portalweb.portal.BaseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
public class EvaluateLogTest extends BaseTestCase {

	public void testEvaluateLog() throws Exception {
		assertTrue(evaluateLog());
	}

	private boolean evaluateLog() throws Exception {
		String xml = FileUtil.read("log");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(xml));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.contains("Exception") && !line.contains("SEVERE")) {
				continue;
			}

			if (line.contains("[antelope:post]")) {
				continue;
			}

			if (line.contains("[junit]")) {
				continue;
			}

			if (line.contains("BasicResourcePool")) {
				continue;
			}

			if (line.contains("Caused by:")) {
				continue;
			}

			if (line.matches(
					".*The web application \\[.*\\] appears to have started " +
						"a thread.*")) {

				if (line.contains("[AWT-Windows]")) {
					continue;
				}

				if (line.contains("[com.google.inject.internal.Finalizer]")) {
					continue;
				}

				if (line.contains(
						"[MultiThreadedHttpConnectionManager cleanup]")) {

					continue;
				}

				if (line.contains(
						"[org.python.google.common.base.internal.Finalizer]")) {

					continue;
				}

				if (line.matches(".*\\[Thread-[0-9]+\\].*")) {
					continue;
				}
			}

			// LPS-17639

			if (line.contains("Table 'lportal.lock_' doesn't exist")) {
				continue;
			}

			// LPS-22821

			if (line.contains(
					"Exception sending context destroyed event to listener " +
						"instance of class com.liferay.portal.spring.context." +
							"PortalContextLoaderListener")) {

				continue;
			}

			// LPS-23351

			if (line.contains(
					"user lacks privilege or object not found: LOCK_")) {

				continue;
			}

			// LPS-23498

			if (line.contains("JBREM00200: ")) {
				continue;
			}

			// LPS-28734

			if (line.contains("java.nio.channels.ClosedChannelException")) {
				continue;
			}

			System.out.println("\nException Line:\n\n" + line + "\n");

			return false;
		}

		return true;
	}

}