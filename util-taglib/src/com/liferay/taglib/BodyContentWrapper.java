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

package com.liferay.taglib;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Shuyang Zhou
 */
public class BodyContentWrapper extends BodyContent
	implements com.liferay.portal.kernel.servlet.taglib.BodyContentWrapper {

	public BodyContentWrapper(
		BodyContent bodyContent, UnsyncStringWriter unsyncStringWriter) {

		super(bodyContent.getEnclosingWriter());

		_bodyContent = bodyContent;
		_unsyncStringWriter = unsyncStringWriter;
	}

	@Override
	public Writer append(char c) throws IOException {
		return _bodyContent.append(c);
	}

	@Override
	public Writer append(CharSequence charSequence) throws IOException {
		return _bodyContent.append(charSequence);
	}

	@Override
	public Writer append(CharSequence charSequence, int start, int end)
		throws IOException {

		return _bodyContent.append(charSequence, start, end);
	}

	@Override
	public void clear() throws IOException {
		_bodyContent.clear();
	}

	@Override
	public void clearBody() {
		_unsyncStringWriter.reset();
	}

	@Override
	public void clearBuffer() {
		_unsyncStringWriter.reset();
	}

	@Override
	public void close() throws IOException {
		_bodyContent.close();
	}

	@Override
	public void flush() throws IOException {
		_bodyContent.flush();
	}

	@Override
	public int getBufferSize() {
		return _bodyContent.getBufferSize();
	}

	@Override
	public JspWriter getEnclosingWriter() {
		return _bodyContent.getEnclosingWriter();
	}

	@Override
	public Reader getReader() {
		return _bodyContent.getReader();
	}

	@Override
	public int getRemaining() {
		return _bodyContent.getRemaining();
	}

	@Override
	public String getString() {
		return _unsyncStringWriter.toString();
	}

	@Override
	public StringBundler getStringBundler() {
		return _unsyncStringWriter.getStringBundler();
	}

	@Override
	public boolean isAutoFlush() {
		return _bodyContent.isAutoFlush();
	}

	@Override
	public void newLine() throws IOException {
		_bodyContent.newLine();
	}

	@Override
	public void print(boolean b) throws IOException {
		_bodyContent.print(b);
	}

	@Override
	public void print(char c) throws IOException {
		_bodyContent.print(c);
	}

	@Override
	public void print(char[] chars) throws IOException {
		_bodyContent.print(chars);
	}

	@Override
	public void print(double d) throws IOException {
		_bodyContent.print(d);
	}

	@Override
	public void print(float f) throws IOException {
		_bodyContent.print(f);
	}

	@Override
	public void print(int i) throws IOException {
		_bodyContent.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		_bodyContent.print(l);
	}

	@Override
	public void print(Object object) throws IOException {
		_bodyContent.print(object);
	}

	@Override
	public void print(String string) throws IOException {
		_bodyContent.print(string);
	}

	@Override
	public void println() throws IOException {
		_bodyContent.println();
	}

	@Override
	public void println(boolean b) throws IOException {
		_bodyContent.println(b);
	}

	@Override
	public void println(char c) throws IOException {
		_bodyContent.println(c);
	}

	@Override
	public void println(char[] chars) throws IOException {
		_bodyContent.println(chars);
	}

	@Override
	public void println(double d) throws IOException {
		_bodyContent.println(d);
	}

	@Override
	public void println(float f) throws IOException {
		_bodyContent.println(f);
	}

	@Override
	public void println(int i) throws IOException {
		_bodyContent.println(i);
	}

	@Override
	public void println(long l) throws IOException {
		_bodyContent.println(l);
	}

	@Override
	public void println(Object object) throws IOException {
		_bodyContent.println(object);
	}

	@Override
	public void println(String string) throws IOException {
		_bodyContent.println(string);
	}

	@Override
	public void write(char[] chars) throws IOException {
		_bodyContent.write(chars);
	}

	@Override
	public void write(char[] chars, int offset, int length) throws IOException {
		_bodyContent.write(chars, offset, length);
	}

	@Override
	public void write(int c) throws IOException {
		_bodyContent.write(c);
	}

	@Override
	public void write(String string) throws IOException {
		_bodyContent.write(string);
	}

	@Override
	public void write(String string, int offset, int length)
		throws IOException {

		_bodyContent.write(string, offset, length);
	}

	@Override
	public void writeOut(Writer writer) throws IOException {
		_bodyContent.writeOut(writer);
	}

	private final BodyContent _bodyContent;
	private final UnsyncStringWriter _unsyncStringWriter;

}