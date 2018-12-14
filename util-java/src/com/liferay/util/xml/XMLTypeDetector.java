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

import com.liferay.util.xml.descriptor.PortletAppDescriptor;
import com.liferay.util.xml.descriptor.StrictXMLDescriptor;
import com.liferay.util.xml.descriptor.WebXML23Descriptor;
import com.liferay.util.xml.descriptor.WebXML24Descriptor;
import com.liferay.util.xml.descriptor.XMLDescriptor;

import org.dom4j.Document;

/**
 * @author Jorge Ferrer
 */
public class XMLTypeDetector {

	public static final XMLDescriptor[] REGISTERED_DESCRIPTORS = {
		new PortletAppDescriptor(), new WebXML23Descriptor(),
		new WebXML24Descriptor()
	};

	public static XMLDescriptor determineType(String doctype, Document root) {
		for (XMLDescriptor descriptor : REGISTERED_DESCRIPTORS) {
			if (descriptor.canHandleType(doctype, root)) {
				return descriptor;
			}
		}

		return new StrictXMLDescriptor();
	}

}