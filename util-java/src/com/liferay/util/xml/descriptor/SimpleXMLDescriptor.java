/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.util.xml.ElementComparator;
import com.liferay.util.xml.ElementIdentifier;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Jorge Ferrer
 */
public abstract class SimpleXMLDescriptor implements XMLDescriptor {

	@Override
	public boolean areEqual(Element el1, Element el2) {
		String name1 = el1.getName();
		String name2 = el2.getName();

		if ((name1 == null) || !name1.equals(name2)) {
			return false;
		}

		if (ArrayUtil.contains(getUniqueElements(), el1.getName())) {
			return true;
		}

		ElementIdentifier[] elIds = getElementsIdentifiedByAttribute();

		for (int i = 0; i < elIds.length; i++) {
			if (name1.equals(elIds[i].getElementName())) {
				if (_compareAttribute(
						el1, el2, elIds[i].getIdentifierName()) == 0) {

					return true;
				}
				else {
					return false;
				}
			}
		}

		elIds = getElementsIdentifiedByChild();

		for (int i = 0; i < elIds.length; i++) {
			if (name1.equals(elIds[i].getElementName())) {
				if (_compareChildText(
						el1, el2, elIds[i].getIdentifierName()) == 0) {

					return true;
				}
				else {
					return false;
				}
			}
		}

		ElementComparator comparator = new ElementComparator();

		if (comparator.compare(el1, el2) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public abstract boolean canHandleType(String doctype, Document root);

	@Override
	public boolean canJoinChildren(Element element) {
		return ArrayUtil.contains(getJoinableElements(), element.getName());
	}

	@Override
	public String[] getChildrenOrder(Element parentElement) {
		return new String[0];
	}

	public ElementIdentifier[] getElementsIdentifiedByAttribute() {
		return new ElementIdentifier[0];
	}

	public ElementIdentifier[] getElementsIdentifiedByChild() {
		return new ElementIdentifier[0];
	}

	public String[] getJoinableElements() {
		return new String[0];
	}

	@Override
	public String[] getRootChildrenOrder() {
		return new String[0];
	}

	public String[] getUniqueElements() {
		return new String[0];
	}

	private int _compareAttribute(Element el1, Element el2, String attrName) {
		String name1 = el1.attributeValue(attrName);
		String name2 = el2.attributeValue(attrName);

		if ((name1 == null) || (name2 == null)) {
			return -1;
		}

		return name1.compareTo(name2);
	}

	private int _compareChildText(Element el1, Element el2, String childName) {
		Element child1 = _getChild(el1, childName);
		Element child2 = _getChild(el2, childName);

		if ((child1 == null) || (child2 == null)) {
			return -1;
		}

		String name1 = child1.getText();
		String name2 = child2.getText();

		if ((name1 == null) || (name2 == null)) {
			return -1;
		}

		return name1.compareTo(name2);
	}

	private Element _getChild(Element parent, String childName) {
		Element child = parent.element(childName);

		/*if (child == null) {
			child = parent.element(childName, parent.getNamespace());
		}*/

		return child;
	}

}