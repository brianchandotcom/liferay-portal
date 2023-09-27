/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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