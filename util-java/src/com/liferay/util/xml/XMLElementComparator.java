/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml;

import com.liferay.util.xml.descriptor.XMLDescriptor;

import org.dom4j.Element;

/**
 * @author Jorge Ferrer
 */
public class XMLElementComparator extends ElementComparator {

	public XMLElementComparator(XMLDescriptor descriptor) {
		_descriptor = descriptor;
	}

	public boolean canJoinChildren(Element element) {
		return _descriptor.canJoinChildren(element);
	}

	@Override
	public int compare(Element el1, Element el2) {
		if (_descriptor.areEqual(el1, el2)) {
			return 0;
		}

		return -1;
	}

	private final XMLDescriptor _descriptor;

}