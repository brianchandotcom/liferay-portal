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

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author  Kyle Stiemann
 */
public class HtmlTopTagTest {

	@Test
	public void testDataSennaTrackAttributeAddedLPS_77775()
		throws IOException, JspException {

		_testDataSennaTrackAttributeAdded(
			"<script type=\"text/javascript\" " +
				"src=\"http://liferay.com/javascript-file.js\"></script>",
			"permanent");
		_testDataSennaTrackAttributeAdded(
			"<link rel=\"stylesheet\" type=\"text/css\" " +
				"src=\"http://liferay.com/css-file.css\">",
			"temporary");
		_testDataSennaTrackAttributeAdded(
			"<style type=\"text/css\">.example{background-color:red;}</style>",
			"temporary");
		_testDataSennaTrackAttributeAdded(
			"<script type=\"text/javascript\" " +
				"src=\"http://liferay.com/javascript-file.js\" " +
					"data-senna-track=\"temporary\"></script>",
			"temporary");
		_testDataSennaTrackAttributeAdded(
			"<link rel=\"stylesheet\" type=\"text/css\" " +
				"src=\"http://liferay.com/css-file.css\" " +
					"data-senna-track=\"permanent\">",
			"permanent");
		_testDataSennaTrackAttributeAdded(
			"<style type=\"text/css\" data-senna-track=\"permanent\"" +
				">.example{background-color:red;}</style>",
			"permanent");
		_testDataSennaTrackAttributeAdded(
			"<meta content=\"initial-scale=1.0, width=device-width\" " +
				"name=\"viewport\">",
			null);
	}

	private static void _assertContainsRegex(
		String string, String containedRegex, String message) {

		Pattern pattern = Pattern.compile(containedRegex);

		Matcher matcher = pattern.matcher(string);

		if (message != null) {
			Assert.assertTrue(message, matcher.find());
		}
		else {
			Assert.assertTrue(matcher.find());
		}
	}

	private static String _getElementAttributes(String element) {
		Matcher matcher = _getElementNameAndAttributesPattern.matcher(element);

		matcher.find();

		return matcher.group(_ELEMENT_ATTRIBUTES_GROUP_INDEX);
	}

	private static String _getElementName(String element) {
		Matcher matcher = _getElementNameAndAttributesPattern.matcher(element);

		matcher.find();

		return matcher.group(_ELEMENT_NAME_GROUP_INDEX);
	}

	private static void _testDataSennaTrackAttributeAdded(
			String element, String expectedDataSennaTrackValue)
		throws IOException, JspException {

		HtmlTopTag htmlTopTag = new HtmlTopTag();
		JspWriter jspWriter = new JspWriterStringImpl();

		PageContext pageContext = new PageContextMockImpl(jspWriter);

		htmlTopTag.setPageContext(pageContext);

		htmlTopTag.setPosition("auto");
		htmlTopTag.doStartTag();

		BodyContent bodyContent = pageContext.pushBody();

		bodyContent.print(element);

		htmlTopTag.setBodyContent(bodyContent);

		htmlTopTag.doEndTag();

		String htmlTopTagOutputString = jspWriter.toString();
		String elementName = _getElementName(element);

		String elementBeginRegex = "<" + elementName + "[^>]+";

		String dataSennaTrackAttributeName = "data-senna-track";

		if (expectedDataSennaTrackValue != null) {
			String dataSennaTrackAttribute =
				dataSennaTrackAttributeName + "=\"" +
					expectedDataSennaTrackValue + "\"";

			String dataSennaTrackAttributeRegex =
				elementBeginRegex + dataSennaTrackAttribute + "[\\s>]";

			_assertContainsRegex(
				htmlTopTagOutputString, dataSennaTrackAttributeRegex,
				dataSennaTrackAttribute + " is not contained in " +
					htmlTopTagOutputString);

			int countOfDataSennaTrackAttrs = StringUtil.count(
				htmlTopTagOutputString, dataSennaTrackAttributeName);

			Assert.assertEquals(1, countOfDataSennaTrackAttrs);
		}
		else {
			Assert.assertFalse(
				htmlTopTagOutputString.contains(dataSennaTrackAttributeName));
		}

		String elementAttributes = _getElementAttributes(element);

		String originalElementAttributesRegex =
			elementBeginRegex + elementAttributes;

		_assertContainsRegex(
			htmlTopTagOutputString, originalElementAttributesRegex, null);
	}

	private static final int _ELEMENT_ATTRIBUTES_GROUP_INDEX = 2;

	private static final int _ELEMENT_NAME_GROUP_INDEX = 1;

	private static final Pattern _getElementNameAndAttributesPattern =
		Pattern.compile("<(\\S+)\\s+([^>]+)");

}