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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.AntCommands;
import com.liferay.portalweb.portal.util.EmailCommands;
import com.liferay.portalweb.util.RuntimeVariables;
import com.liferay.portalweb.util.TestPropsValues;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Location;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.CanvasBuilder.ElementAdder;
import org.sikuli.api.visual.CanvasBuilder.ElementAreaSetter;
import org.sikuli.api.visual.DesktopCanvas;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferaySeleniumHelper {

	public static void addToJavaScriptExceptions(Exception exception) {
		_javaScriptExceptions.add(exception);
	}

	public static void addToLiferayExceptions(Exception exception) {
		_liferayExceptions.add(exception);
	}

	public static void antCommand(
			LiferaySelenium liferaySelenium, String fileName, String target)
		throws Exception {

		AntCommands antCommands = new AntCommands(
			liferaySelenium, fileName, target);

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<Void> future = executorService.submit(antCommands);

		try {
			future.get(150, TimeUnit.SECONDS);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (TimeoutException te) {
		}
	}

	public static void assertAlert(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getAlert());
	}

	public static void assertAlertNotPresent(LiferaySelenium liferaySelenium)
		throws Exception {

		if (liferaySelenium.isAlertPresent()) {
			throw new Exception("Alert is present");
		}
	}

	public static void assertChecked(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isNotChecked(locator)) {
			throw new Exception(
				"Element is not checked at \"" + locator + "\"");
		}
	}

	public static void assertConfirmation(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		String confirmation = liferaySelenium.getConfirmation();

		if (!pattern.equals(confirmation)) {
			throw new Exception(
				"Pattern \"" + pattern + "\" does not match \"" + confirmation +
					"\"");
		}
	}

	public static void assertConsoleTextNotPresent(String text)
		throws Exception {

		if (isConsoleTextPresent(text)) {
			throw new Exception("\"" + text + "\" is present in console");
		}
	}

	public static void assertConsoleTextPresent(String text) throws Exception {
		if (!isConsoleTextPresent(text)) {
			throw new Exception("\"" + text + "\" is not present in console");
		}
	}

	public static void assertEditable(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		if (liferaySelenium.isNotEditable(locator)) {
			throw new Exception(
				"Element is not editable at \"" + locator + "\"");
		}
	}

	public static void assertElementNotPresent(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		if (liferaySelenium.isElementPresent(locator)) {
			throw new Exception("Element is present at \"" + locator + "\"");
		}
	}

	public static void assertElementPresent(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		if (liferaySelenium.isElementNotPresent(locator)) {
			throw new Exception(
				"Element is not present at \"" + locator + "\"");
		}
	}

	public static void assertEmailBody(
			LiferaySelenium liferaySelenium, String index, String body)
		throws Exception {

		BaseTestCase.assertEquals(body, liferaySelenium.getEmailBody(index));
	}

	public static void assertEmailSubject(
			LiferaySelenium liferaySelenium, String index, String subject)
		throws Exception {

		BaseTestCase.assertEquals(
			subject, liferaySelenium.getEmailSubject(index));
	}

	public static void assertHTMLSourceTextNotPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		if (isHTMLSourceTextPresent(liferaySelenium, value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does exists in the HTML source");
		}
	}

	public static void assertHTMLSourceTextPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		if (!isHTMLSourceTextPresent(liferaySelenium, value)) {
			throw new Exception(
				"Pattern \"" + value + "\" does not exists in the HTML source");
		}
	}

	public static void assertLiferayErrors() throws Exception {
		String currentDate = DateUtil.getCurrentDate(
			"yyyy-MM-dd", LocaleUtil.getDefault());

		String fileName =
			PropsValues.LIFERAY_HOME + "/logs/liferay." + currentDate + ".xml";

		if (!FileUtil.exists(fileName)) {
			return;
		}

		String content = FileUtil.read(fileName);

		if (content.equals("")) {
			return;
		}

		content = "<log4j>" + content + "</log4j>";
		content = content.replaceAll("log4j:", "");

		Document document = UnsecureSAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		List<Element> eventElements = rootElement.elements("event");

		for (Element eventElement : eventElements) {
			String level = eventElement.attributeValue("level");

			if (level.equals("ERROR")) {
				String fileContent = FileUtil.read(fileName);

				fileContent = fileContent.replaceFirst(
					"level=\"ERROR\"", "level=\"ERROR_FOUND\"");

				FileUtil.write(fileName, fileContent);

				Element messageElement = eventElement.element("message");

				String messageText = messageElement.getText();

				if (isIgnorableErrorLine(messageText)) {
					continue;
				}

				Element throwableElement = eventElement.element("throwable");

				Exception exception = null;

				if (throwableElement != null) {
					exception = new Exception(
						messageText + throwableElement.getText());

					addToLiferayExceptions(exception);

					throw exception;
				}

				exception = new Exception(messageText);

				addToLiferayExceptions(exception);

				throw exception;
			}
		}
	}

	public static void assertLocation(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNoJavaScriptExceptions() throws Exception {
		if (!_javaScriptExceptions.isEmpty()) {
			StringBundler sb = new StringBundler();

			sb.append(_javaScriptExceptions.size());
			sb.append(" Javascript Exception");

			if (_javaScriptExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown");

			System.out.println();
			System.out.println("##");
			System.out.println("## " + sb.toString());
			System.out.println("##");

			for (int i = 0; i < _javaScriptExceptions.size(); i++) {
				Exception liferayException = _javaScriptExceptions.get(i);

				System.out.println();
				System.out.println("##");
				System.out.println("## Javascript Exception #" + (i + 1));
				System.out.println("##");
				System.out.println();
				System.out.println(liferayException.getMessage());
				System.out.println();
			}

			throw new Exception(sb.toString());
		}
	}

	public static void assertNoLiferayExceptions() throws Exception {
		if (!_liferayExceptions.isEmpty()) {
			StringBundler sb = new StringBundler();

			sb.append(_liferayExceptions.size());
			sb.append(" Liferay Exception");

			if (_liferayExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown");

			System.out.println();
			System.out.println("##");
			System.out.println("## " + sb.toString());
			System.out.println("##");

			for (int i = 0; i < _liferayExceptions.size(); i++) {
				Exception liferayException = _liferayExceptions.get(i);

				System.out.println();
				System.out.println("##");
				System.out.println("## Liferay Exception #" + (i + 1));
				System.out.println("##");
				System.out.println();
				System.out.println(liferayException.getMessage());
				System.out.println();
			}

			throw new Exception(sb.toString());
		}
	}

	public static void assertNotAlert(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertNotEquals(pattern, liferaySelenium.getAlert());
	}

	public static void assertNotChecked(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isChecked(locator)) {
			throw new Exception("Element is checked at \"" + locator + "\"");
		}
	}

	public static void assertNotEditable(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		if (liferaySelenium.isEditable(locator)) {
			throw new Exception("Element is editable at \"" + locator + "\"");
		}
	}

	public static void assertNotLocation(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertNotEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNotPartialText(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isPartialText(locator, pattern)) {
			String text = liferaySelenium.getText(locator);

			throw new Exception(
				"\"" + text + "\" contains \"" + pattern + "\" at \"" +
					locator + "\"");
		}
	}

	public static void assertNotSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(selectLocator);

		if (liferaySelenium.isSelectedLabel(selectLocator, pattern)) {
			String text = liferaySelenium.getSelectedLabel(selectLocator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + text + "\" at \"" +
					selectLocator + "\"");
		}
	}

	public static void assertNotText(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isText(locator, pattern)) {
			String text = liferaySelenium.getText(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + text + "\" at \"" +
					locator + "\"");
		}
	}

	public static void assertNotValue(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isValue(locator, pattern)) {
			String value = liferaySelenium.getValue(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" matches \"" + value + "\" at \"" +
					locator + "\"");
		}
	}

	public static void assertNotVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isVisible(locator)) {
			throw new Exception("Element is visible at \"" + locator + "\"");
		}
	}

	public static void assertPartialConfirmation(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		String confirmation = liferaySelenium.getConfirmation();

		if (!confirmation.contains(pattern)) {
			throw new Exception(
				"\"" + confirmation + "\" does not contain \"" + pattern +
					"\"");
		}
	}

	public static void assertPartialText(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isNotPartialText(locator, pattern)) {
			String text = liferaySelenium.getText(locator);

			throw new Exception(
				"\"" + text + "\" does not contain \"" + pattern + "\" at \"" +
					locator + "\"");
		}
	}

	public static void assertSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(selectLocator);

		if (liferaySelenium.isNotSelectedLabel(selectLocator, pattern)) {
			String text = liferaySelenium.getSelectedLabel(selectLocator);

			throw new Exception(
				"Pattern \"" + pattern + "\" does not match \"" + text +
					"\" at \"" + selectLocator + "\"");
		}
	}

	public static void assertText(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isNotText(locator, pattern)) {
			String text = liferaySelenium.getText(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" does not match \"" + text +
					"\" at \"" + locator + "\"");
		}
	}

	public static void assertTextNotPresent(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		if (liferaySelenium.isTextPresent(pattern)) {
			throw new Exception("\"" + pattern + "\" is present");
		}
	}

	public static void assertTextPresent(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		if (liferaySelenium.isTextNotPresent(pattern)) {
			throw new Exception("\"" + pattern + "\" is not present");
		}
	}

	public static void assertValue(
			LiferaySelenium liferaySelenium, String locator, String pattern)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isNotValue(locator, pattern)) {
			String value = liferaySelenium.getValue(locator);

			throw new Exception(
				"Pattern \"" + pattern + "\" does not match \"" + value +
					"\" at \"" + locator + "\"");
		}
	}

	public static void assertVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		liferaySelenium.assertElementPresent(locator);

		if (liferaySelenium.isNotVisible(locator)) {
			throw new Exception(
				"Element is not visible at \"" + locator + "\"");
		}
	}

	public static void captureScreen(String fileName) throws Exception {
		File file = new File(fileName);

		file.mkdirs();

		Robot robot = new Robot();

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Rectangle rectangle = new Rectangle(toolkit.getScreenSize());

		BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

		ImageIO.write(bufferedImage, "jpg", file);
	}

	public static void connectToEmailAccount(
			String emailAddress, String emailPassword)
		throws Exception {

		EmailCommands.connectToEmailAccount(emailAddress, emailPassword);
	}

	public static void deleteAllEmails() throws Exception {
		EmailCommands.deleteAllEmails();
	}

	public static void echo(String message) {
		System.out.println(message);
	}

	public static void fail(String message) {
		BaseTestCase.fail(message);
	}

	public static String getEmailBody(String index) throws Exception {
		return EmailCommands.getEmailBody(GetterUtil.getInteger(index));
	}

	public static String getEmailSubject(String index) throws Exception {
		return EmailCommands.getEmailSubject(GetterUtil.getInteger(index));
	}

	public static ImageTarget getImageTarget(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		File file = new File(
			getPortalRootDirName() + liferaySelenium.getSikuliImagesDirName() +
				image);

		return new ImageTarget(file);
	}

	public static String getNumberDecrement(String value) {
		return StringUtil.valueOf(GetterUtil.getInteger(value) - 1);
	}

	public static String getNumberIncrement(String value) {
		return StringUtil.valueOf(GetterUtil.getInteger(value) + 1);
	}

	public static String getPortalRootDirName() throws Exception {
		File file = new File(".");

		String absolutePath = file.getAbsolutePath();

		return absolutePath.substring(0, absolutePath.length() - 1);
	}

	public static boolean isConfirmation(
		LiferaySelenium liferaySelenium, String pattern) {

		String confirmation = liferaySelenium.getConfirmation();

		return pattern.equals(confirmation);
	}

	public static boolean isConsoleTextPresent(String text) throws Exception {
		String currentDate = DateUtil.getCurrentDate(
			"yyyy-MM-dd", LocaleUtil.getDefault());

		String fileName =
			PropsValues.LIFERAY_HOME + "/logs/liferay." + currentDate + ".log";

		String content = FileUtil.read(fileName);

		Pattern pattern = Pattern.compile(text);

		Matcher matcher = pattern.matcher(content);

		return matcher.find();
	}

	public static boolean isElementNotPresent(
		LiferaySelenium liferaySelenium, String locator) {

		return !liferaySelenium.isElementPresent(locator);
	}

	public static boolean isElementPresentAfterWait(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				return liferaySelenium.isElementPresent(locator);
			}

			if (liferaySelenium.isElementPresent(locator)) {
				break;
			}

			Thread.sleep(1000);
		}

		return liferaySelenium.isElementPresent(locator);
	}

	public static boolean isHTMLSourceTextPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		URL url = new URL(liferaySelenium.getLocation());

		InputStream inputStream = url.openStream();

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			Pattern pattern = Pattern.compile(value);

			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				return true;
			}
		}

		inputStream.close();

		bufferedReader.close();

		return false;
	}

	public static boolean isIgnorableErrorLine(String line) {
		if (line.contains("[antelope:post]")) {
			return true;
		}

		if (line.contains("[junit]")) {
			return true;
		}

		if (line.contains("BasicResourcePool")) {
			return true;
		}

		if (line.contains("Caused by:")) {
			return true;
		}

		if (line.contains("INFO:")) {
			return true;
		}

		if (line.matches(
				".*The web application \\[.*\\] appears to have started a " +
					"thread.*")) {

			if (line.contains("[AWT-Windows]")) {
				return true;
			}

			if (line.contains("[com.google.inject.internal.Finalizer]")) {
				return true;
			}

			if (line.contains("[MultiThreadedHttpConnectionManager cleanup]")) {
				return true;
			}

			if (line.contains(
					"[org.python.google.common.base.internal.Finalizer]")) {

				return true;
			}

			if (line.matches(".*\\[Thread-[0-9]+\\].*")) {
				return true;
			}

			if (line.matches(".*\\[TrueZIP InputStream Reader\\].*")) {
				return true;
			}
		}

		// LPS-17639

		if (line.contains("Table 'lportal.lock_' doesn't exist")) {
			return true;
		}

		if (line.contains("Table 'lportal.Lock_' doesn't exist")) {
			return true;
		}

		// LPS-22821

		if (line.contains(
				"Exception sending context destroyed event to listener " +
					"instance of class com.liferay.portal.spring.context." +
						"PortalContextLoaderListener")) {

			return true;
		}

		// LPS-23351

		if (line.contains("user lacks privilege or object not found: LOCK_")) {
			return true;
		}

		// LPS-23498

		if (line.contains("JBREM00200: ")) {
			return true;
		}

		// LPS-28734

		if (line.contains("java.nio.channels.ClosedChannelException")) {
			return true;
		}

		// LPS-28954

		if (line.matches(
				".*The web application \\[/wsrp-portlet\\] created a " +
					"ThreadLocal with key of type.*")) {

			if (line.contains(
					"[org.apache.axis.utils.XMLUtils." +
						"ThreadLocalDocumentBuilder]")) {

				return true;
			}

			if (line.contains(
					"[org.apache.xml.security.utils." +
						"UnsyncByteArrayOutputStream$1]")) {

				return true;
			}
		}

		// LPS-37574

		if (line.contains("java.util.zip.ZipException: ZipFile closed")) {
			return true;
		}

		// LPS-41257

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains("[de.schlichtherle")) {
				return true;
			}
		}

		// LPS-41776

		if (line.contains("SEC5054: Certificate has expired")) {
			return true;
		}

		// LPS-41863

		if (line.contains("Disabling contextual LOB") &&
			line.contains("MSC service thread") &&
			line.contains("[org.hibernate.engine.jdbc.JdbcSupportLoader]")) {

			return true;
		}

		// LPS-46161

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains(
					"[com.google.javascript.jscomp.Tracer.ThreadTrace]")) {

				return true;
			}
		}

		// LPS-49204

		if (line.matches(
				".*The web application \\[\\] appears to have started a " +
					"thread named \\[elasticsearch\\[.*")) {

			return true;
		}

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains("[org.elasticsearch.common.inject]")) {
				return true;
			}

			if (line.contains("[org.elasticsearch.index.mapper]")) {
				return true;
			}
		}

		// LPS-49228

		if (line.matches(
				".*The web application \\[/sharepoint-hook\\] created a " +
					"ThreadLocal with key of type.*")) {

			if (line.contains(
					"[org.apache.axis.utils.XMLUtils." +
						"ThreadLocalDocumentBuilder]")) {

				return true;
			}
		}

		// LPS-49229

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains(
					"[org.apache.xmlbeans.impl.schema." +
						"SchemaTypeLoaderImpl$1]")) {

				return true;
			}

			if (line.contains("[org.apache.xmlbeans.impl.store.CharUtil$1]")) {
				return true;
			}

			if (line.contains("[org.apache.xmlbeans.impl.store.Locale$1]")) {
				return true;
			}
		}

		// LPS-49505

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains("[org.jruby.RubyEncoding$2]")) {
				return true;
			}
		}

		// LPS-49506

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains("[org.joni.StackMachine$1]")) {
				return true;
			}
		}

		// LPS-49628

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains(
					"[org.apache.poi.extractor.ExtractorFactory$1]")) {

				return true;
			}
		}

		// LPS-49629

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains("[org.apache.xmlbeans.XmlBeans$1]")) {
				return true;
			}
		}

		// LPS-50047

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains(
					"[com.sun.syndication.feed.impl.ToStringBean$1]")) {

				return true;
			}
		}

		// LPS-50936

		if (line.contains(
				"Liferay does not have the Xuggler native libraries " +
					"installed.")) {

			return true;
		}

		// LPS-51371

		if (line.matches(
				".*The web application \\[/jasperreports-web\\] created a " +
					"ThreadLocal with key of type.*")) {

			if (line.contains(
					"[net.sf.jasperreports.engine.fonts.FontUtil$1]")) {

				return true;
			}
		}

		// LPS-52346

		if (line.matches(
				".*The web application \\[\\] created a ThreadLocal with key " +
					"of type.*")) {

			if (line.contains(
					"[org.apache.jasper.runtime.JspWriterImpl." +
						"CharBufferThreadLocalPool]")) {

				return true;
			}
		}

		// LPS-52699

		if (line.matches(
				".*The web application \\[/saml-portlet\\] created a " +
					"ThreadLocal with key of type.*")) {

			if (line.matches(
					".*\\[org.apache.xml.security.algorithms." +
						"MessageDigestAlgorithm\\$[0-9]+\\].*")) {

				return true;
			}

			if (line.matches(
					".*\\[org.apache.xml.security.algorithms." +
						"SignatureAlgorithm\\$[0-9]+\\].*")) {

				return true;
			}

			if (line.matches(
					".*\\[org.apache.xml.security.utils." +
						"UnsyncBufferedOutputStream\\$[0-9]+\\].*")) {

				return true;
			}

			if (line.matches(
					".*\\[org.apache.xml.security.utils." +
						"UnsyncByteArrayOutputStream\\$[0-9]+\\].*")) {

				return true;
			}
		}

		// LPS-54539

		if (line.matches(
				".*The web application \\[/agent\\] appears to have started " +
					"a thread.*")) {

			if (line.matches(".*\\[http-bio.*\\].*")) {
				return true;
			}

			if (line.matches(".*\\[scheduler_Worker-[0-9]+\\].*")) {
				return true;
			}

			if (line.matches(".*\\[SocketListener.*\\].*")) {
				return true;
			}
		}

		// LPS-54680

		if (line.contains(
				"The web application [/reports-portlet] appears to have " +
					"started a thread named [C3P0PooledConnectionPool")) {

			return true;
		}

		// LPS-55160

		if (line.matches(".*osgi.compendium:service=.*version=1.[0-9]+.*")) {
			return true;
		}

		// LPS-57789, temporary workaround until Dante Wang fixes it

		if (line.matches(
				".*The web application \\[\\] appears to have started " +
					"a thread.*")) {

			if (line.matches(
					".*\\[Hikari housekeeper (pool HikariPool-*\\].*")) {

				return true;
			}
		}

		// LRQA-14442, temporary workaround until Kiyoshi Lee fixes it

		if (line.contains("Framework Event Dispatcher: Equinox Container:")) {
			if (line.contains("[org_eclipse_equinox_http_servlet")) {
				return true;
			}
		}

		// WCM-202

		if (line.contains("No score point assigners available")) {
			return true;
		}

		if (Validator.equals(
				TestPropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.1") ||
			Validator.equals(
				TestPropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.2") ||
			Validator.equals(
				TestPropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.3") ||
			Validator.equals(
				TestPropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.4") ||
			Validator.equals(
				TestPropsValues.LIFERAY_PORTAL_BRANCH, "ee-6.2.10")) {

			if (line.contains(
					"com.liferay.portal.kernel.search.SearchException: " +
						"java.nio.channels.ClosedByInterruptException")) {

				return true;
			}

			if (line.contains(
					"org.apache.lucene.store.AlreadyClosedException")) {

				return true;
			}
		}

		if (Validator.isNotNull(TestPropsValues.IGNORE_ERRORS)) {
			if (Validator.isNotNull(TestPropsValues.IGNORE_ERRORS_DELIMITER)) {
				String ignoreErrorsDelimiter =
					TestPropsValues.IGNORE_ERRORS_DELIMITER;

				if (ignoreErrorsDelimiter.equals("|")) {
					ignoreErrorsDelimiter = "\\|";
				}

				String[] ignoreErrors = TestPropsValues.IGNORE_ERRORS.split(
					ignoreErrorsDelimiter);

				for (String ignoreError : ignoreErrors) {
					if (line.contains(ignoreError)) {
						return true;
					}
				}
			}
			else if (line.contains(TestPropsValues.IGNORE_ERRORS)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isMobileDeviceEnabled() {
		return TestPropsValues.MOBILE_DEVICE_ENABLED;
	}

	public static boolean isNotChecked(
		LiferaySelenium liferaySelenium, String locator) {

		return !liferaySelenium.isChecked(locator);
	}

	public static boolean isNotPartialText(
		LiferaySelenium liferaySelenium, String locator, String value) {

		return !liferaySelenium.isPartialText(locator, value);
	}

	public static boolean isNotText(
		LiferaySelenium liferaySelenium, String locator, String value) {

		return !liferaySelenium.isText(locator, value);
	}

	public static boolean isNotValue(
		LiferaySelenium liferaySelenium, String locator, String value) {

		return !liferaySelenium.isValue(locator, value);
	}

	public static boolean isNotVisible(
		LiferaySelenium liferaySelenium, String locator) {

		return !liferaySelenium.isVisible(locator);
	}

	public static boolean isSikuliImagePresent(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		if (screenRegion.find(imageTarget) != null) {
			return true;
		}

		return false;
	}

	public static boolean isTCatEnabled() {
		return TestPropsValues.TCAT_ENABLED;
	}

	public static boolean isTextNotPresent(
		LiferaySelenium liferaySelenium, String pattern) {

		return !liferaySelenium.isTextPresent(pattern);
	}

	public static void pause(String waitTime) throws Exception {
		Thread.sleep(GetterUtil.getInteger(waitTime));
	}

	public static void replyToEmail(
			LiferaySelenium liferaySelenium, String to, String body)
		throws Exception {

		EmailCommands.replyToEmail(to, body);

		liferaySelenium.pause("3000");
	}

	public static void saveScreenshot(LiferaySelenium liferaySelenium)
		throws Exception {

		_screenshotCount++;

		captureScreen(
			liferaySelenium.getProjectDirName() +
				"portal-web/test-results/functional/screenshots/" +
					_screenshotCount + ".jpg");
	}

	public static void saveScreenshotBeforeAction(
			LiferaySelenium liferaySelenium, boolean actionFailed)
		throws Exception {

		if (actionFailed) {
			_screenshotErrorCount++;
		}

		captureScreen(
			liferaySelenium.getProjectDirName() +
				"portal-web/test-results/functional/screenshots/" +
					"ScreenshotBeforeAction" + _screenshotErrorCount + ".jpg");
	}

	public static void sendEmail(
			LiferaySelenium liferaySelenium, String to, String subject,
			String body)
		throws Exception {

		EmailCommands.sendEmail(to, subject, body);

		liferaySelenium.pause("3000");
	}

	public static void sikuliAssertElementNotPresent(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		if (screenRegion.wait(imageTarget, 5000) != null) {
			throw new Exception("Element is present");
		}
	}

	public static void sikuliAssertElementPresent(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		screenRegion = screenRegion.wait(imageTarget, 5000);

		if (screenRegion == null) {
			throw new Exception("Element is not present");
		}

		Canvas canvas = new DesktopCanvas();

		ElementAdder elementAdder = canvas.add();

		ElementAreaSetter elementAreaSetter = elementAdder.box();

		elementAreaSetter.around(screenRegion);

		canvas.display(2);
	}

	public static void sikuliClick(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		Mouse mouse = new DesktopMouse();

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		ScreenRegion imageTargetScreenRegion = screenRegion.find(imageTarget);

		if (imageTargetScreenRegion != null) {
			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	public static void sikuliClickByIndex(
			LiferaySelenium liferaySelenium, String image, String index)
		throws Exception {

		Mouse mouse = new DesktopMouse();

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		List<ScreenRegion> imageTargetScreenRegions = screenRegion.findAll(
			imageTarget);

		ScreenRegion imageTargetScreenRegion = imageTargetScreenRegions.get(
			Integer.parseInt(index));

		if (imageTargetScreenRegion != null) {
			mouse.click(imageTargetScreenRegion.getCenter());
		}
	}

	public static void sikuliDragAndDrop(
			LiferaySelenium liferaySelenium, String image, String coordString)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		screenRegion = screenRegion.find(imageTarget);

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());

		Robot robot = new Robot();

		robot.delay(1000);

		mouse.press();

		robot.delay(2000);

		String[] coords = coordString.split(",");

		Location location = screenRegion.getCenter();

		int x = location.getX() + GetterUtil.getInteger(coords[0]);
		int y = location.getY() + GetterUtil.getInteger(coords[1]);

		robot.mouseMove(x, y);

		robot.delay(1000);

		mouse.release();
	}

	public static void sikuliLeftMouseDown(LiferaySelenium liferaySelenium)
		throws Exception {

		liferaySelenium.pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.press();
	}

	public static void sikuliLeftMouseUp(LiferaySelenium liferaySelenium)
		throws Exception {

		liferaySelenium.pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.release();
	}

	public static void sikuliMouseMove(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		ScreenRegion screenRegion = new DesktopScreenRegion();

		ImageTarget imageTarget = getImageTarget(liferaySelenium, image);

		screenRegion = screenRegion.find(imageTarget);

		Mouse mouse = new DesktopMouse();

		mouse.move(screenRegion.getCenter());
	}

	public static void sikuliRightMouseDown(LiferaySelenium liferaySelenium)
		throws Exception {

		liferaySelenium.pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightPress();
	}

	public static void sikuliRightMouseUp(LiferaySelenium liferaySelenium)
		throws Exception {

		liferaySelenium.pause("1000");

		Mouse mouse = new DesktopMouse();

		mouse.rightRelease();
	}

	public static void sikuliType(
			LiferaySelenium liferaySelenium, String image, String value)
		throws Exception {

		sikuliClick(liferaySelenium, image);

		liferaySelenium.pause("1000");

		Keyboard keyboard = new DesktopKeyboard();

		if (value.contains("${line.separator}")) {
			String[] tokens = StringUtil.split(value, "${line.separator}");

			for (int i = 0; i < tokens.length; i++) {
				keyboard.type(tokens[i]);

				if ((i + 1) < tokens.length) {
					keyboard.type(Key.ENTER);
				}
			}

			if (value.endsWith("${line.separator}")) {
				keyboard.type(Key.ENTER);
			}
		}
		else {
			keyboard.type(value);
		}
	}

	public static void sikuliUploadCommonFile(
			LiferaySelenium liferaySelenium, String image, String value)
		throws Exception {

		sikuliClick(liferaySelenium, image);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.keyDown(Key.CTRL);

		keyboard.type("a");

		keyboard.keyUp(Key.CTRL);

		sikuliType(
			liferaySelenium, image,
			getPortalRootDirName() + liferaySelenium.getDependenciesDirName() +
				value);

		keyboard.type(Key.ENTER);
	}

	public static void sikuliUploadTCatFile(
			LiferaySelenium liferaySelenium, String image, String value)
		throws Exception {

		String tCatAdminFileName =
			TestPropsValues.TCAT_ADMIN_REPOSITORY + "/" + value;

		if (OSDetector.isWindows()) {
			tCatAdminFileName = tCatAdminFileName.replace("/", "\\");
		}

		sikuliType(liferaySelenium, image, tCatAdminFileName);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.type(Key.ENTER);
	}

	public static void sikuliUploadTempFile(
			LiferaySelenium liferaySelenium, String image, String value)
		throws Exception {

		sikuliClick(liferaySelenium, image);

		Keyboard keyboard = new DesktopKeyboard();

		keyboard.keyDown(Key.CTRL);

		keyboard.type("a");

		keyboard.keyUp(Key.CTRL);

		String slash = "/";

		if (OSDetector.isWindows()) {
			slash = "\\";
		}

		sikuliType(
			liferaySelenium, image,
			liferaySelenium.getOutputDirName() + slash + value);

		keyboard.type(Key.ENTER);
	}

	public static void typeAceEditor(
		LiferaySelenium liferaySelenium, String locator, String value) {

		liferaySelenium.typeKeys(locator, "");

		Keyboard keyboard = new DesktopKeyboard();

		Matcher matcher = _aceEditorPattern.matcher(value);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start();

			String line = value.substring(x, y);

			keyboard.type(line.trim());

			String specialCharacter = matcher.group();

			if (specialCharacter.equals("(")) {
				keyboard.type("(");
			}
			else if (specialCharacter.equals("${line.separator}")) {
				liferaySelenium.keyPress(locator, "\\SPACE");
				liferaySelenium.keyPress(locator, "\\RETURN");
			}

			x = y + specialCharacter.length();
		}

		String line = value.substring(x);

		keyboard.type(line.trim());
	}

	public static void typeCKEditor(
		LiferaySelenium liferaySelenium, String locator, String value) {

		StringBundler sb = new StringBundler();

		String idAttribute = liferaySelenium.getAttribute(locator + "@id");

		int x = idAttribute.indexOf("cke__");
		int y = idAttribute.indexOf("cke__", x + 1);

		if (y == -1) {
			y = idAttribute.length();
		}

		sb.append(idAttribute.substring(x + 4, y));

		sb.append(".setHTML(\"");
		sb.append(HtmlUtil.escapeJS(value.replace("\\", "\\\\")));
		sb.append("\")");

		liferaySelenium.runScript(sb.toString());
	}

	public static void typeScreen(String value) {
		Keyboard keyboard = new DesktopKeyboard();

		keyboard.type(value);
	}

	public static void waitForElementNotPresent(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertElementNotPresent(locator);
			}

			try {
				if (liferaySelenium.isElementNotPresent(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForElementPresent(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertElementPresent(locator);
			}

			try {
				if (liferaySelenium.isElementPresent(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotPartialText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertNotPartialText(locator, value);
			}

			try {
				if (liferaySelenium.isNotPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertNotSelectedLabel(selectLocator, pattern);
			}

			try {
				if (liferaySelenium.isNotSelectedLabel(
						selectLocator, pattern)) {

					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertNotText(locator, value);
			}

			try {
				if (liferaySelenium.isNotText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotValue(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertNotValue(locator, value);
			}

			try {
				if (liferaySelenium.isNotValue(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertNotVisible(locator);
			}

			try {
				if (liferaySelenium.isNotVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForPartialText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertPartialText(locator, value);
			}

			try {
				if (liferaySelenium.isPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertSelectedLabel(selectLocator, pattern);
			}

			try {
				if (liferaySelenium.isSelectedLabel(selectLocator, pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertText(locator, value);
			}

			try {
				if (liferaySelenium.isText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForTextNotPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertTextNotPresent(value);
			}

			try {
				if (liferaySelenium.isTextNotPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForTextPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertTextPresent(value);
			}

			try {
				if (liferaySelenium.isTextPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForValue(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertValue(locator, value);
			}

			try {
				if (liferaySelenium.isValue(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				liferaySelenium.assertVisible(locator);
			}

			try {
				if (liferaySelenium.isVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	private static final Pattern _aceEditorPattern = Pattern.compile(
		"\\(|\\$\\{line\\.separator\\}");
	private static final List<Exception> _javaScriptExceptions =
		new ArrayList<>();
	private static final List<Exception> _liferayExceptions = new ArrayList<>();
	private static int _screenshotCount = 0;
	private static int _screenshotErrorCount = 0;

}