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

/**
 * @author Jorge Ferrer
 */
public class ElementIdentifier {

	public ElementIdentifier(String elementName, String identifierName) {
		_elementName = elementName;
		_identifierName = identifierName;
	}

	public String getElementName() {
		return _elementName;
	}

	public String getIdentifierName() {
		return _identifierName;
	}

	private final String _elementName;
	private final String _identifierName;

}