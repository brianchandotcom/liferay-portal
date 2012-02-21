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

package com.liferay.util.xml;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Jorge Ferrer
 */
public class XMLMergerTask extends Task {

	@Override
	public void execute() throws BuildException {
		_validateAttributes();

		try {
			XMLMergerRunner runner = new XMLMergerRunner(_type);

			runner.mergeAndSave(_masterFile, _slaveFile, _outputFile);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setMasterFile(File masterFile) {
		_masterFile = masterFile;
	}

	public void setOutputFile(File outputFile) {
		_outputFile = outputFile;
	}

	public void setSlaveFile(File slaveFile) {
		_slaveFile = slaveFile;
	}

	public void setType(String type) {
		_type = type;
	}

	private void _validateAttributes() {
		_validateMandatoryAttribute(_masterFile, "masterFile");
		_validateMandatoryAttribute(_slaveFile, "slaveFile");
		_validateMandatoryAttribute(_outputFile, "outputFile");
	}

	private void _validateMandatoryAttribute(File value, String name) {
		if (value == null) {
			throw new BuildException(name + " is a required attribute");
		}
	}

	private File _masterFile;
	private File _slaveFile;
	private File _outputFile;
	private String _type;

}