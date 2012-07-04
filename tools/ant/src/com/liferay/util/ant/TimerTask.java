/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import org.apache.tools.ant.taskdefs.Sequential;

/**
 * The timer task is useful for reporting the time taken to perform some series
 * of task(s). The timer task has an optional name attribute which can be used
 * to identify particular instances of the timer task.<br><br>
 *
 * Example of using the timer task to report the time taken to perform a deploy
 * task:<br>
 * <pre>
 * 		&lt;liferay-timer name="build">
 * 			&lt;antcall target="deploy" />
 * 		&lt;/liferay-timer>
 * </pre>
 *
 * <br><br>
 * @author Raymond Augé
 */
public class TimerTask extends Sequential {

	public TimerTask() {
		_name = "current timer";
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public void execute() {
		long _initialTime = System.nanoTime();

		super.execute();

		long _finalTime = System.nanoTime();

		getProject().log(
			"Elapsed time of [" + _name + "] is " +
				((_finalTime - _initialTime) / 1000000) + "ms, " +
					(_finalTime - _initialTime) + "ns");
	}

	private String _name;

}