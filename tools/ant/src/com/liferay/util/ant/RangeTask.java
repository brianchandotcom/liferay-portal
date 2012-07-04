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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * The range task is used to created integer ranges mostly for doing iterative
 * performance testing. The result of calling this task is a string property
 * containing a comma delimited list of sequential integers useful for use with
 * for loops when you want to repeat an operation a number of times, for
 * instance to test it's performance.<br><br>
 *
 * Example of generating a list of 10000 integers, then using it in a for
 * loop:<br>
 * <pre>
 * 		&lt;liferay-range first="1" last="10000" property="range" />
 *
 *		&lt;for list="${range}" param="index">
 *			&lt;sequential>
 *				&lt;!-- do something with @{index} -->
 *			&lt;/sequential>
 *		&lt;/for>
 * </pre>
 *
 * <br><br>
 * @author Raymond Augé
 */
public class RangeTask extends Task {

	/**
	 * Set the lower bound of the range.
	 *
	 * @param first first (smallest) value in the range
	 */
	public void setFirst(int first) {
		_first = first;
	}

	/**
	 * Set the upper bound of the range.
	 *
	 * @param last last (largest) value in the range
	 */
	public void setLast(int last) {
		_last = last;
	}

	/**
	 * Set the name of the property from which the range will be available.
	 *
	 * @param property the name of the range property
	 */
	public void setProperty(String property) {
		_property = property;
	}

	@Override
	public void execute() throws BuildException {
		if (_first >= _last) {
			throw new BuildException("first must be smaller than last");
		}

		StringBuffer sb = new StringBuffer();

		for (int i = _first; i <= _last; i++) {
			if (i > _first ) {
				sb.append(",");
			}

			sb.append(i);
		}

		getProject().setProperty(_property, sb.toString());
	}

	private int _first;
	private int _last;
	private String _property;

}