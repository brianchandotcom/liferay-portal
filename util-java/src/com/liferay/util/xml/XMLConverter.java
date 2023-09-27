/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml;

import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.DOMWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class XMLConverter {

	public static javax.xml.namespace.QName toJavaxQName(
		org.dom4j.QName dom4jQName) {

		return new javax.xml.namespace.QName(
			dom4jQName.getNamespaceURI(), dom4jQName.getName(),
			dom4jQName.getNamespacePrefix());
	}

	public static org.w3c.dom.Document toW3CDocument(
			org.dom4j.Document dom4jDoc)
		throws DocumentException {

		DOMWriter dom4jWriter = new DOMWriter();

		return dom4jWriter.write(dom4jDoc);
	}

	public static org.w3c.dom.Element toW3CElement(org.dom4j.Element dom4jEl)
		throws DocumentException {

		DocumentFactory documentFactory = DocumentFactory.getInstance();

		org.dom4j.Document dom4jDoc = documentFactory.createDocument();

		dom4jDoc.setRootElement(dom4jEl.createCopy());

		org.w3c.dom.Document w3cDoc = toW3CDocument(dom4jDoc);

		return w3cDoc.getDocumentElement();
	}

}