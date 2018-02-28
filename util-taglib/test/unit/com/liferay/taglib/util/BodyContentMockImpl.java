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
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Kyle Stiemann
 */
public class BodyContentMockImpl extends BodyContent {

	public BodyContentMockImpl(JspWriter jspWriter) {
		super(jspWriter);

		_jspWriter = jspWriter;
	}

	@Override
	public void clear() throws IOException {
		_jspWriter.clear();
	}

	@Override
	public void clearBuffer() throws IOException {
		_jspWriter.clearBuffer();
	}

	@Override
	public void close() throws IOException {
		_jspWriter.close();
	}

	@Override
	public Reader getReader() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRemaining() {
		return _jspWriter.getRemaining();
	}

	@Override
	public String getString() {
		return _jspWriter.toString();
	}

	@Override
	public void newLine() throws IOException {
		_jspWriter.newLine();
	}

	@Override
	public void print(boolean b) throws IOException {
		_jspWriter.print(b);
	}

	@Override
	public void print(char c) throws IOException {
		_jspWriter.print(c);
	}

	@Override
	public void print(char[] s) throws IOException {
		_jspWriter.print(s);
	}

	@Override
	public void print(double d) throws IOException {
		_jspWriter.print(d);
	}

	@Override
	public void print(float f) throws IOException {
		_jspWriter.print(f);
	}

	@Override
	public void print(int i) throws IOException {
		_jspWriter.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		_jspWriter.print(l);
	}

	@Override
	public void print(Object obj) throws IOException {
		_jspWriter.print(obj);
	}

	@Override
	public void print(String s) throws IOException {
		_jspWriter.print(s);
	}

	@Override
	public void println() throws IOException {
		_jspWriter.println();
	}

	@Override
	public void println(boolean x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(char x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(char[] x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(double x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(float x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(int x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(long x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(Object x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void println(String x) throws IOException {
		_jspWriter.println(x);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		_jspWriter.write(cbuf, off, len);
	}

	@Override
	public void writeOut(Writer out) throws IOException {
		throw new UnsupportedOperationException();
	}

	private final JspWriter _jspWriter;

}