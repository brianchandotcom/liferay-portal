/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util.log4j;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.xml.DOMConfigurator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class Log4JUtil {

	public static void configureLog4J(ClassLoader classLoader) {
		configureLog4J(classLoader.getResource("META-INF/portal-log4j.xml"));

		try {
			Enumeration<URL> enu = classLoader.getResources(
				"META-INF/portal-log4j-ext.xml");

			while (enu.hasMoreElements()) {
				configureLog4J(enu.nextElement());
			}
		}
		catch (IOException ioe) {
			java.util.logging.Logger logger =
				java.util.logging.Logger.getLogger(Log4JUtil.class.getName());

			logger.log(
				java.util.logging.Level.WARNING,
				"Unable to load portal-log4j-ext.xml", ioe);
		}

		if (ServerDetector.isJBoss5()) {
			Logger rootLogger = LogManager.getRootLogger();

			Enumeration<Appender> enu = rootLogger.getAllAppenders();

			while (enu.hasMoreElements()) {
				Appender appender = enu.nextElement();

				if (appender instanceof WriterAppender) {
					WriterAppender writerAppender = (WriterAppender)appender;

					writerAppender.activateOptions();
				}
			}
		}
	}

	public static void configureLog4J(URL url) {
		if (url == null) {
			return;
		}

		String urlContent = _getURLContent(url);

		if (urlContent == null) {
			return;
		}

		// See LPS-6029, LPS-8865, and LPS-24280

		DOMConfigurator domConfigurator = new DOMConfigurator();

		Reader urlReader = new StringReader(urlContent);

		domConfigurator.doConfigure(
			urlReader, LogManager.getLoggerRepository());

		Set<String> currentLoggerNames = new HashSet<String>();

		Enumeration<Logger> enu = LogManager.getCurrentLoggers();

		while (enu.hasMoreElements()) {
			Logger logger = enu.nextElement();

			currentLoggerNames.add(logger.getName());
		}

		try {
			SAXReader saxReader = new SAXReader();

			Reader reader = new StringReader(urlContent);

			Document document = saxReader.read(reader, url.toExternalForm());

			Element rootElement = document.getRootElement();

			List<Element> categoryElements = rootElement.elements("category");

			for (Element categoryElement : categoryElements) {
				String name = categoryElement.attributeValue("name");

				Element priorityElement = categoryElement.element("priority");

				String priority = priorityElement.attributeValue("value");

				setLevel(name, priority, false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getCustomLogSettings() {
		return new HashMap<String, String>(_customLogSettings);
	}

	public static String getOriginalLevel(String className) {
		Level level = Level.ALL;

		Enumeration<Logger> enu = LogManager.getCurrentLoggers();

		while (enu.hasMoreElements()) {
			Logger logger = enu.nextElement();

			if (className.equals(logger.getName())) {
				level = logger.getLevel();

				break;
			}
		}

		return level.toString();
	}

	public static void initLog4J(
		String serverId, String liferayHome, ClassLoader classLoader,
		LogFactory logFactory, Map<String, String> customLogSettings) {

		ServerDetector.init(serverId);

		_liferayHome = liferayHome;

		configureLog4J(classLoader);

		try {
			LogFactoryUtil.setLogFactory(logFactory);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (String name : customLogSettings.keySet()) {
			String priority = customLogSettings.get(name);

			setLevel(name, priority, false);
		}
	}

	public static void setLevel(String name, String priority, boolean custom) {
		Logger logger = Logger.getLogger(name);

		logger.setLevel(Level.toLevel(priority));

		java.util.logging.Logger jdkLogger = java.util.logging.Logger.getLogger(
			name);

		jdkLogger.setLevel(_getJdkLevel(priority));

		if (custom) {
			_customLogSettings.put(name, priority);
		}
	}

	/**
	 * @see {@link com.liferay.portal.util.FileImpl#getBytes(InputStream, int,
	 *      boolean)}
	 */
	private static byte[] _getBytes(InputStream inputStream)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream, -1, true);

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private static java.util.logging.Level _getJdkLevel(String priority) {
		if (priority.equalsIgnoreCase(Level.DEBUG.toString())) {
			return java.util.logging.Level.FINE;
		}
		else if (priority.equalsIgnoreCase(Level.ERROR.toString())) {
			return java.util.logging.Level.SEVERE;
		}
		else if (priority.equalsIgnoreCase(Level.WARN.toString())) {
			return java.util.logging.Level.WARNING;
		}
		else {
			return java.util.logging.Level.INFO;
		}
	}

	private static String _getLiferayHome() {
		if (_liferayHome == null) {
			_liferayHome = PropsUtil.get(PropsKeys.LIFERAY_HOME);
		}

		return _liferayHome;
	}

	private static String _getURLContent(URL url) {
		Map<String, String> variables = new HashMap<String, String>();

		variables.put("@liferay.home@", _getLiferayHome());

		String spiId = System.getProperty("spi.id");

		if (spiId == null) {
			spiId = StringPool.BLANK;
		}

		variables.put("@spi.id@", spiId);

		String urlContent = null;

		InputStream inputStream = null;

		try {
			inputStream = url.openStream();

			byte[] bytes = _getBytes(inputStream);

			urlContent = new String(bytes, StringPool.UTF8);
		}
		catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		for (Map.Entry<String, String> variable : variables.entrySet()) {
			urlContent = StringUtil.replace(
				urlContent, variable.getKey(), variable.getValue());
		}

		if (ServerDetector.getServerId() != null) {
			return urlContent;
		}

		int x = urlContent.indexOf("<appender name=\"FILE\"");

		int y = urlContent.indexOf("</appender>", x);

		if (y != -1) {
			y = urlContent.indexOf("<", y + 1);
		}

		if ((x != -1) && (y != -1)) {
			urlContent = urlContent.substring(0, x) + urlContent.substring(y);
		}

		urlContent = StringUtil.replace(
			urlContent, "<appender-ref ref=\"FILE\" />", StringPool.BLANK);

		return urlContent;
	}

	private static Map<String, String> _customLogSettings =
		new ConcurrentHashMap<String, String>();
	private static String _liferayHome;

}