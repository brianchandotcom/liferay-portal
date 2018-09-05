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