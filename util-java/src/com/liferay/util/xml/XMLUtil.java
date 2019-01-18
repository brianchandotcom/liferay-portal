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

package com.liferay.util.xml;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;

import java.io.IOException;

import org.dom4j.DocumentException;

/**
 * @author     Leonardo Barros
 * @see        com.liferay.petra.xml.XMLUtil
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class XMLUtil {

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

	public static String formatXML(Document document) {
		try {
			return document.formattedString(_XML_INDENT);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public static String formatXML(String xml) {

		// This is only supposed to format your xml, however, it will also
		// unwantingly change &#169; and other characters like it into their
		// respective readable versions

		try {
			xml = StringUtil.replace(xml, "&#", "[$SPECIAL_CHARACTER$]");
			xml = Dom4jUtil.toString(xml, _XML_INDENT);
			xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "&#");

			return xml;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
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
				((c >= 0xE000) && (c <= 0xFFFD))) {

				sb.append(c);
			}

			if (Character.isHighSurrogate(c) && ((i + 1) < xml.length())) {
				char c2 = xml.charAt(i + 1);

				if (Character.isLowSurrogate(c2)) {
					int codePoint = Character.toCodePoint(c, c2);

					if ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)) {
						sb.appendCodePoint(codePoint);
					}
				}
			}
		}

		return sb.toString();
	}

	public static String toCompactSafe(String xml) {
		return StringUtil.replace(
			xml, _COMPACT_SAFE_OLD_SUBS, _COMPACT_SAFE_NEW_SUBS);
	}

	private static final String[] _COMPACT_SAFE_NEW_SUBS = {
		"[$NEW_LINE$]", "[$NEW_LINE$]", "[$NEW_LINE$]"
	};

	private static final String[] _COMPACT_SAFE_OLD_SUBS = {
		StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE, StringPool.RETURN
	};

	private static final String _XML_INDENT = "  ";

}