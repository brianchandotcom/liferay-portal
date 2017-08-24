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

package com.liferay.util.ant;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

/**
 * @author Brian Wing Shun Chan
 */
public class AntUtil {

	public static Project getProject() {
		Project project = new Project();

		BuildLogger buildLogger = new DefaultLogger() {

			@Override
			public void messageLogged(BuildEvent buildEvent) {
				int priority = buildEvent.getPriority();

				if (priority > msgOutputLevel) {
					return;
				}

				StringBundler sb = new StringBundler();

				boolean first = true;

				for (String line : StringUtil.splitLines(
						buildEvent.getMessage())) {

					if (!first) {
						sb.append(StringPool.OS_EOL);
					}

					first = false;

					sb.append(StringPool.DOUBLE_SPACE);
					sb.append(line);
				}

				String message = sb.toString();

				if (priority != Project.MSG_ERR) {
					printMessage(message, out, priority);
				}
				else {
					printMessage(message, err, priority);
				}

				log(message);
			}

		};

		buildLogger.setErrorPrintStream(System.err);
		buildLogger.setMessageOutputLevel(Project.MSG_WARN);
		buildLogger.setOutputPrintStream(System.out);

		project.addBuildListener(buildLogger);

		return project;
	}

}