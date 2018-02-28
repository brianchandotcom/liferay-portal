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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.Set;

import javax.servlet.jsp.JspWriter;

/**
 * @author  Kyle Stiemann
 */
public class OutputDataMockImpl extends OutputData {

	public OutputDataMockImpl(JspWriter jspWriter) {
		_jspWriter = jspWriter;
	}

	@Override
	public void addData(String outputKey, String webKey, StringBundler sb) {
		String string = sb.toString();

		try {
			_jspWriter.write(string);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public boolean addOutputKey(String outputKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public StringBundler getData(String outputKey, String webKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public StringBundler getMergedData(String webKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> getOutputKeys() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputData merge(OutputData outputData) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setData(String outputKey, String webKey, StringBundler sb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputData split() {
		throw new UnsupportedOperationException();
	}

	private final JspWriter _jspWriter;

}