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

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Sequential;

/**
 * The ant-contrib <code>&lt;if></code> while very flexible is also very verbose
 * and due to the number of elements involved results in a great deal of time
 * spent parsing. This task, while less flexible, involves only a single element
 * with attribute based (as opposed to element based) conditions. It currently
 * only performs logical checks for existence or nullity of properties and
 * files. Since most logic is based on one of these two cases, the current focus
 * was to limit checking to these.<br><br>
 *
 * Example of checking a property:<br>
 * <pre>
 * 		&lt;liferay-if notNull="some.property">
 * 			&lt;!-- do something -->
 * 		&lt;/liferay-if>
 * </pre>
 * <br>
 *
 * Example of checking a file:<br>
 * <pre>
 * 		&lt;liferay-if available="some/file">
 * 			&lt;!-- do something -->
 * 		&lt;/liferay-if>
 * </pre>
 *
 * <br><br>
 * @author Raymond Augé
 */
public class IfTask extends Sequential {

	/**
	 * Check to see if a file exists.
	 *
	 * @param file the path to a file
	 */
	public void setAvailable(File file) {
		_available = Boolean.TRUE;
		_file = file;
	}

	/**
	 * Check to see if a file is missing.
	 *
	 * @param file the path to a file
	 */
	public void setNotavailable(File file) {
		_available = Boolean.FALSE;
		_file = file;
	}

	/**
	 * Check to see if a property exists with a non-null value (a blank string
	 * is considered null).
	 *
	 * @param propertyName the name of the property to check
	 */
	public void setNotNull(String propertyName) {
		_null = Boolean.FALSE;
		_propertyName = propertyName;
	}

	/**
	 * Check to see if a property does not exist, or has a null value (a blank
	 * string is considered null).
	 *
	 * @param propertyName the name of the property to check
	 */
	public void setNull(String propertyName) {
		_null = Boolean.TRUE;
		_propertyName = propertyName;
	}

	@Override
	public void execute() {
		if (((_null != null) &&
			 ((_null.equals(Boolean.TRUE) && !propertyExists()) ||
			  (_null.equals(Boolean.FALSE) && propertyExists()))) ||
			((_file != null) &&
			 ((_available.equals(Boolean.TRUE) && _file.exists()) ||
			  (_available.equals(Boolean.FALSE) && !_file.exists())))) {

			super.execute();
		}
	}

	protected boolean propertyExists() {
		Project project = getProject();

		String property = project.getProperty(_propertyName);
		String userProperty = project.getUserProperty(_propertyName);

		if (((property != null) && (property.length() > 0)) ||
			((userProperty != null) && (userProperty.length() > 0))) {

			return true;
		}

		return false;
	}

	private Boolean _available;
	private File _file;
	private Boolean _null;
	private String _propertyName;

}