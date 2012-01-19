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

/**
 * @author Brian Wing Shun Chan
 */
public class XMLConverter {

	public static javax.xml.namespace.QName toJavaxQName(
		org.dom4j.QName dom4jQName) {

		javax.xml.namespace.QName javaxQName = new javax.xml.namespace.QName(
			dom4jQName.getNamespaceURI(), dom4jQName.getName(),
			dom4jQName.getNamespacePrefix());

		return javaxQName;
	}

	public static org.w3c.dom.Document toW3CDocument(
			org.dom4j.Document dom4jDoc)
		throws org.dom4j.DocumentException {

		org.dom4j.io.DOMWriter dom4jWriter = new org.dom4j.io.DOMWriter();

		org.w3c.dom.Document w3cDoc = dom4jWriter.write(dom4jDoc);

		return w3cDoc;
	}

	public static org.w3c.dom.Element toW3CElement(org.dom4j.Element dom4jEl)
		throws org.dom4j.DocumentException {

		org.dom4j.Document dom4jDoc =
			org.dom4j.DocumentFactory.getInstance().createDocument();

		dom4jDoc.setRootElement(dom4jEl.createCopy());

		org.w3c.dom.Document w3cDoc = toW3CDocument(dom4jDoc);

		return w3cDoc.getDocumentElement();
	}

}