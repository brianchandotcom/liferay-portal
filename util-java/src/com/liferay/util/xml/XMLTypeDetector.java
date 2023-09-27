/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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