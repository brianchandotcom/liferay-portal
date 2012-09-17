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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Brian Wing Shun Chan
 * @author Alan Zimmerman
 */
public class XMLFormatter {

	public static String fixProlog(String xml) {

		// LEP-1921

		if (xml != null) {
			int pos = xml.indexOf(CharPool.LESS_THAN);

			if (pos > 0) {
				xml = xml.substring(pos);
			}
		}

		return xml;
	}

	public static String fromCompactSafe(String xml) {
		return StringUtil.replace(xml, "[$NEW_LINE$]", StringPool.NEW_LINE);
	}

	public static String stripInvalidChars(String xml) {
		if (Validator.isNull(xml)) {
			return xml;
		}

		// Strip characters that are not valid in the 1.0 XML spec
		// http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < xml.length(); i++) {
			char c = xml.charAt(i);

			if ((c == 0x9) || (c == 0xA) || (c == 0xD) ||
				((c >= 0x20) && (c <= 0xD7FF)) ||
				((c >= 0xE000) && (c <= 0xFFFD)) ||
				((c >= 0x10000) && (c <= 0x10FFFF))) {

				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String toCompactSafe(String xml) {
		return StringUtil.replace(
			xml,
			new String[] {
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {
				"[$NEW_LINE$]", "[$NEW_LINE$]", "[$NEW_LINE$]"
			});
	}

	public static String toString(Node node) throws IOException {
		return toString(node, StringPool.TAB);
	}

	public static String toString(Node node, String indent) throws IOException {
		return toString(node, StringPool.TAB, false);
	}

	public static String toString(
			Node node, String indent, boolean expandEmptyElements)
		throws IOException {

		return toString(node, indent, expandEmptyElements, true);
	}

	public static String toString(
			Node node, String indent, boolean expandEmptyElements,
			boolean trimText)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		OutputFormat outputFormat = OutputFormat.createPrettyPrint();

		outputFormat.setExpandEmptyElements(expandEmptyElements);
		outputFormat.setIndent(indent);
		outputFormat.setLineSeparator(StringPool.NEW_LINE);
		outputFormat.setTrimText(trimText);

		XMLWriter xmlWriter = new XMLWriter(
			unsyncByteArrayOutputStream, outputFormat);

		xmlWriter.write(node);

		String content = unsyncByteArrayOutputStream.toString(StringPool.UTF8);

		// LEP-4257

		//content = StringUtil.replace(content, "\n\n\n", "\n\n");

		if (content.endsWith("\n\n")) {
			content = content.substring(0, content.length() - 2);
		}

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		while (content.contains(" \n")) {
			content = StringUtil.replace(content, " \n", "\n");
		}

		if (content.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
			content = StringUtil.replaceFirst(
				content, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
				"<?xml version=\"1.0\"?>");
		}

		return content;
	}

	public static String toString(String xml)
		throws DocumentException, IOException {

		return toString(xml, StringPool.TAB);
	}

	public static String toString(String xml, String indent)
		throws DocumentException, IOException {

		SAXReader saxReader = new SAXReader();

		Document document = saxReader.read(new UnsyncStringReader(xml));

		return toString(document, indent);
	}

}