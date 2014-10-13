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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Brian Wing Shun Chan
 */
public class AntCommands extends Thread {

	public AntCommands(
		LiferaySelenium liferaySelenium, String fileName, String target) {

		_liferaySelenium = liferaySelenium;
		_fileName = fileName;
		_target = target;
	}

	@Override
	public void run() {
		try {
			Runtime runtime = Runtime.getRuntime();
			StringBundler sb = new StringBundler();

			if (!OSDetector.isWindows()) {
				String projectDirName = _liferaySelenium.getProjectDirName();

				projectDirName = StringUtil.replace(projectDirName, "\\", "//");

				runtime.exec("/bin/bash cd " + projectDirName);

				sb.append("/bin/bash ant -f ");
				sb.append(_fileName);
				sb.append(" ");
				sb.append(_target);
				sb.append(" -Dtest.ant.launched.by.selenium=true");
			}
			else {
				runtime.exec(
					"cmd /c cd " + _liferaySelenium.getProjectDirName());

				sb.append("cmd /c ant -f ");
				sb.append(_fileName);
				sb.append(" ");
				sb.append(_target);
				sb.append(" -Dtest.ant.launched.by.selenium=true");
			}

			Process process = runtime.exec(sb.toString());

			InputStreamReader inputStreamReader = new InputStreamReader(
				process.getInputStream());

			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);

				if (line.contains("BUILD FAILED") ||
					line.contains("BUILD SUCCESSFUL")) {

					break;
				}
			}
		}
		catch (Exception e) {
		}
	}

	private final String _fileName;
	private final LiferaySelenium _liferaySelenium;
	private final String _target;

}