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

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspWriter;

/**
 * This class is an implementation of {@link JspWriter} that writes to an underlying String instead of JSP.
 *
 * @author Neil Griffin
 */
public class JspWriterStringImpl extends JspWriter {

	public JspWriterStringImpl() {
		super(_DEFAULT_BUFFER_SIZE, _DEFAULT_AUTO_FLUSH);

		_stringWriter = new StringWriter(_DEFAULT_BUFFER_SIZE);
	}

	@Override
	public void clear() throws IOException {
		_stringWriter = new StringWriter(getBufferSize());
	}

	@Override
	public void clearBuffer() throws IOException {
		_stringWriter = new StringWriter(getBufferSize());
	}

	@Override
	public void close() throws IOException {
		_stringWriter.close();
	}

	@Override
	public void flush() throws IOException {
		_stringWriter.flush();
	}

	@Override
	public int getRemaining() {
		return getBufferSize() - _stringWriter.getBuffer().length();
	}

	@Override
	public void newLine() throws IOException {
		_stringWriter.write("\n");
	}

	@Override
	public void print(boolean b) throws IOException {
		_stringWriter.write(Boolean.toString(b));
	}

	@Override
	public void print(char c) throws IOException {
		_stringWriter.write(Character.toString(c));
	}

	@Override
	public void print(char[] s) throws IOException {
		_stringWriter.write(s);
	}

	@Override
	public void print(double d) throws IOException {
		_stringWriter.write(Double.toString(d));
	}

	@Override
	public void print(float f) throws IOException {
		_stringWriter.write(Float.toString(f));
	}

	@Override
	public void print(int i) throws IOException {
		_stringWriter.write(Integer.toString(i));
	}

	@Override
	public void print(long l) throws IOException {
		_stringWriter.write(Long.toString(l));
	}

	@Override
	public void print(Object o) throws IOException {
		if (o != null) {
			_stringWriter.write(o.toString());
		}
	}

	@Override
	public void print(String s) throws IOException {
		if (s != null) {
			_stringWriter.write(s);
		}
	}

	@Override
	public void println() throws IOException {
		_stringWriter.write("\n");
	}

	@Override
	public void println(boolean b) throws IOException {
		print(b);
		println();
	}

	@Override
	public void println(char c) throws IOException {
		print(c);
		println();
	}

	@Override
	public void println(char[] s) throws IOException {
		print(s);
		println();
	}

	@Override
	public void println(double d) throws IOException {
		print(d);
		println();
	}

	@Override
	public void println(float f) throws IOException {
		print(f);
		println();
	}

	@Override
	public void println(int i) throws IOException {
		print(i);
		println();
	}

	@Override
	public void println(long l) throws IOException {
		print(l);
		println();
	}

	@Override
	public void println(Object o) throws IOException {
		print(o);
		println();
	}

	@Override
	public void println(String s) throws IOException {
		print(s);
		println();
	}

	@Override
	public String toString() {
		return _stringWriter.toString();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		_stringWriter.write(cbuf, off, len);
	}

	private static final boolean _DEFAULT_AUTO_FLUSH = true;

	private static final int _DEFAULT_BUFFER_SIZE = 1024;

	private StringWriter _stringWriter;

}