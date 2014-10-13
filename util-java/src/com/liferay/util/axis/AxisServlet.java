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

package com.liferay.util.axis;

import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.UncommittedServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.lang.reflect.Field;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.cache.MethodCache;

/**
 * @author Brian Wing Shun Chan
 */
public class AxisServlet extends org.apache.axis.transport.http.AxisServlet {

	@Override
	public void destroy() {
		if (ServerDetector.isWebLogic()) {
			doDestroy();
		}
		else {
			DestroyThread destroyThread = new DestroyThread();

			destroyThread.start();

			try {
				destroyThread.join();
			}
			catch (InterruptedException ie) {
				throw new RuntimeException(ie);
			}

			Exception e = destroyThread.getException();

			if (e != null) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException)e;
				}
				else {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		_servletConfig = servletConfig;

		StringBundler sb = new StringBundler(9);

		sb.append("<complexType abstract=\"true\" name=\"OrderByComparator\">");
		sb.append("<sequence><element name=\"ascending\" ");
		sb.append("type=\"xsd:boolean\"/><element name=\"orderBy\" ");
		sb.append("nillable=\"true\" type=\"soapenc:string\"/><element ");
		sb.append("name=\"orderByConditionFields\" nillable=\"true\" ");
		sb.append("type=\"impl:ArrayOf_xsd_string\"/><element ");
		sb.append("name=\"orderByFields\" nillable=\"true\" ");
		sb.append("type=\"impl:ArrayOf_xsd_string\"/></sequence>");
		sb.append("</complexType>");

		_correctOrderByComparator = sb.toString();

		sb = new StringBundler(5);

		sb.append("<complexType name=\"ArrayOf_xsd_long\"><complexContent>");
		sb.append("<restriction base=\"soapenc:Array\"><attribute ");
		sb.append("ref=\"soapenc:arrayType\" ");
		sb.append("wsdl:arrayType=\"soapenc:long[]\"/></restriction>");
		sb.append("</complexContent></complexType>");

		_correctLongArray = sb.toString();

		sb = new StringBundler(5);

		sb.append("<complexType name=\"ArrayOf_xsd_string\"><complexContent>");
		sb.append("<restriction base=\"soapenc:Array\"><attribute ");
		sb.append("ref=\"soapenc:arrayType\" ");
		sb.append("wsdl:arrayType=\"soapenc:string[]\"/></restriction>");
		sb.append("</complexContent></complexType>");

		_correctStringArray = sb.toString();

		sb = new StringBundler(2);

		sb.append("<complexType name=\"OrderByComparator\"><simpleContent>");
		sb.append("<extension/></simpleContent></complexType>");

		_incorrectOrderByComparator = sb.toString();

		sb = new StringBundler(2);

		sb.append("<complexType name=\"ArrayOf_xsd_long\"><simpleContent>");
		sb.append("<extension/></simpleContent></complexType>");

		_incorrectLongArray = sb.toString();

		sb = new StringBundler(2);

		sb.append("<complexType name=\"ArrayOf_xsd_string\"><simpleContent>");
		sb.append("<extension/></simpleContent></complexType>");

		_incorrectStringArray = sb.toString();

		if (ServerDetector.isResin() || ServerDetector.isWebLogic()) {
			doInit();
		}
		else {
			InitThread initThread = new InitThread();

			initThread.start();

			try {
				initThread.join();
			}
			catch (InterruptedException ie) {
				throw new ServletException(ie);
			}

			Exception e = initThread.getException();

			if (e != null) {
				if (e instanceof ServletException) {
					throw (ServletException)e;
				}
				else {
					throw new ServletException(e);
				}
			}
		}
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			if (!_ready) {
				return;
			}

			BufferCacheServletResponse bufferCacheServletResponse =
				new BufferCacheServletResponse(response);

			super.service(request, bufferCacheServletResponse);

			String contentType = bufferCacheServletResponse.getContentType();

			response.setContentType(contentType);

			String content = bufferCacheServletResponse.getString();

			if (_fixContent) {
				if (contentType.contains(ContentTypes.TEXT_HTML)) {
					content = _HTML_TOP_WRAPPER.concat(content).concat(
						_HTML_BOTTOM_WRAPPER);
				}
				else if (contentType.contains(ContentTypes.TEXT_XML)) {
					content = fixXml(content);
				}
			}

			ServletResponseUtil.write(
				new UncommittedServletResponse(response),
				content.getBytes(StringPool.UTF8));
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		finally {
			try {
				ThreadLocal<?> cache = (ThreadLocal<?>)_CACHE_FIELD.get(null);

				if (cache != null) {
					cache.remove();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void doDestroy() {
		_ready = false;

		super.destroy();
	}

	protected void doInit() throws ServletException {
		super.init(_servletConfig);

		_fixContent = GetterUtil.getBoolean(
			_servletConfig.getInitParameter("fix-content"), true);

		_ready = true;
	}

	protected String fixXml(String xml) throws Exception {
		if (!xml.contains("<wsdl:definitions")) {
			return xml;
		}

		xml = StringUtil.replace(
			xml,
			new String[] {
				"\r\n", "\n", "  ", "> <", _incorrectOrderByComparator,
				_incorrectLongArray, _incorrectStringArray
			},
			new String[] {
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, "><",
				_correctOrderByComparator, _correctLongArray,
				_correctStringArray
			});

		Document document = SAXReaderUtil.read(xml);

		return document.formattedString();
	}

	private static final Field _CACHE_FIELD;

	private static final String _HTML_BOTTOM_WRAPPER = "</body></html>";

	private static final String _HTML_TOP_WRAPPER = "<html><body>";

	private static final Log _log = LogFactoryUtil.getLog(AxisServlet.class);

	static {
		try {
			_CACHE_FIELD = ReflectionUtil.getDeclaredField(
				MethodCache.class, "cache");
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

	private String _correctLongArray;
	private String _correctOrderByComparator;
	private String _correctStringArray;
	private boolean _fixContent;
	private String _incorrectLongArray;
	private String _incorrectOrderByComparator;
	private String _incorrectStringArray;
	private boolean _ready;
	private ServletConfig _servletConfig;

	private class DestroyThread extends Thread {

		public DestroyThread() {
			setDaemon(true);
		}

		public Exception getException() {
			return _exception;
		}

		@Override
		public void run() {
			try {
				doDestroy();
			}
			catch (Exception e) {
				_exception = e;
			}
		}

		private Exception _exception;

	}

	private class InitThread extends Thread {

		public InitThread() {
			setDaemon(true);
		}

		public Exception getException() {
			return _exception;
		}

		@Override
		public void run() {
			try {
				doInit();
			}
			catch (Exception e) {
				_exception = e;
			}
		}

		private Exception _exception;

	}

}