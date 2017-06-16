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

package com.liferay.util.xml.descriptor;

import com.liferay.util.xml.ElementIdentifier;

import org.dom4j.Document;

/**
 * @author Jorge Ferrer
 */
public class TilesDefsDescriptor extends SimpleXMLDescriptor {

	@Override
	public boolean canHandleType(String doctype, Document root) {
		return doctype.contains("tiles-config");
	}

	@Override
	public ElementIdentifier[] getElementsIdentifiedByAttribute() {
		return _ELEMENTS_IDENTIFIED_BY_ATTR;
	}

	@Override
	public String[] getRootChildrenOrder() {
		return _ROOT_CHILDREN_ORDER;
	}

	@Override
	public String[] getUniqueElements() {
		return _UNIQUE_ELEMENTS;
	}

	private static final ElementIdentifier[] _ELEMENTS_IDENTIFIED_BY_ATTR =
		{new ElementIdentifier("definition", "name")};

	private static final String[] _ROOT_CHILDREN_ORDER = {"definition"};

	private static final String[] _UNIQUE_ELEMENTS = {};

}