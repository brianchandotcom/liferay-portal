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

import com.liferay.portal.kernel.util.Validator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.FilterSet;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Miguel Pastor
 */
public class FormatTask extends Echo {

	@Override
	public void execute () throws BuildException {
		_validateTaskConfiguration();

		String[] arguments = _arguments.split(_separator);

		String value = MessageFormat.format(message, arguments);

		if (_property != null) {
			Project project = getProject();

			project.setProperty(_property, value);

			return;
		}

		setMessage(value);

		super.execute();
	}

	public void setArguments(String arguments) {
		_arguments = arguments;
	}

	public void setProperty(String property) {
		_property = property;
	}

	public void setSeparator(String separator) {
		_separator = separator;
	}

	private void _validateTaskConfiguration() throws BuildException {
		if (message == null) {
			throw new BuildException("The message attribute is mandatory");
		}

		return;
	}

	private String _arguments;

	private String _property;

	private String _separator = ",";

}