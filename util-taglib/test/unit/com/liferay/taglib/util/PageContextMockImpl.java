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
import java.io.Writer;

import java.util.Enumeration;

import javax.el.ELContext;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.ErrorData;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Kyle Stiemann
 */
public class PageContextMockImpl extends PageContext {

	public PageContextMockImpl(JspWriter jspWriter) {
		_jspWriter = jspWriter;
	}

	@Override
	public Object findAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forward(String relativeUrlPath)
		throws IOException, ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name, int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttributesScope(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ELContext getELContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ErrorData getErrorData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Exception getException() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionEvaluator getExpressionEvaluator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter getOut() {
		return _jspWriter;
	}

	@Override
	public Object getPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRequest getRequest() {
		return new HttpServletRequestMockImpl(_jspWriter);
	}

	@Override
	public ServletResponse getResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletConfig getServletConfig() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HttpSession getSession() {
		throw new UnsupportedOperationException();
	}

	@Override
	public VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Exception e)
		throws IOException, ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Throwable t)
		throws IOException, ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void include(String relativeUrlPath)
		throws IOException, ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void include(String relativeUrlPath, boolean flush)
		throws IOException, ServletException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void initialize(
			Servlet servlet, ServletRequest request, ServletResponse response,
			String errorPageURL, boolean needsSession, int bufferSize,
			boolean autoFlush)
		throws IllegalArgumentException, IllegalStateException,
			   IOException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter popBody() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BodyContent pushBody() {
		JspWriter jspWriter = new JspWriterStringImpl();

		return new BodyContentMockImpl(jspWriter);
	}

	@Override
	public JspWriter pushBody(Writer writer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void release() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name, int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		throw new UnsupportedOperationException();
	}

	private final JspWriter _jspWriter;

}