/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml.descriptor;

import com.liferay.util.xml.AttributeComparator;
import com.liferay.util.xml.ElementComparator;

import java.util.Comparator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Jorge Ferrer
 */
public class StrictXMLDescriptor implements XMLDescriptor {

	@Override
	public boolean areEqual(Element el1, Element el2) {
		if (_compare(el1, el2) == 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canHandleType(String doctype, Document root) {
		return false;
	}

	@Override
	public boolean canJoinChildren(Element element) {
		return false;
	}

	@Override
	public String[] getChildrenOrder(Element parentElement) {
		return new String[0];
	}

	@Override
	public String[] getRootChildrenOrder() {
		return _ROOT_ORDERED_CHILDREN;
	}

	private int _compare(Object object1, Object object2) {
		Element element1 = (Element)object1;
		Element element2 = (Element)object2;

		String name1 = element1.getName();
		String name2 = element2.getName();

		if (!name1.equals(name2)) {
			return name1.compareTo(name2);
		}

		String el1Text = element1.getTextTrim();
		String el2Text = element2.getTextTrim();

		if (!el1Text.equals(el2Text)) {
			return el1Text.compareTo(el2Text);
		}

		int attributeComparison = _compareAttributes(element1, element2);

		if (attributeComparison != 0) {
			return attributeComparison;
		}

		int childrenComparison = _compareChildren(element1, element2);

		if (childrenComparison != 0) {
			return childrenComparison;
		}

		return 0;
	}

	private int _compareAttributes(Element element1, Element element2) {
		List<Attribute> element1Attrs = element1.attributes();
		List<Attribute> element2Attrs = element2.attributes();

		if (element1Attrs.size() < element2Attrs.size()) {
			return -1;
		}
		else if (element1Attrs.size() > element2Attrs.size()) {
			return 1;
		}

		for (Attribute attr : element1Attrs) {
			int value = _contains(
				element2Attrs, attr, new AttributeComparator());

			if (value != 0) {
				return value;
			}
		}

		return -1;
	}

	private int _compareChildren(Element element1, Element element2) {
		List<Element> element1Children = element1.elements();
		List<Element> element2Children = element2.elements();

		if (element1Children.size() < element2Children.size()) {
			return -1;
		}
		else if (element1Children.size() > element2Children.size()) {
			return 1;
		}

		for (Element element : element1Children) {
			int value = _contains(
				element2Children, element, new ElementComparator());

			if (value != 0) {
				return value;
			}
		}

		return -1;
	}

	private int _contains(
		List<Attribute> list, Attribute object,
		Comparator<Attribute> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Attribute o = list.get(i);

			int value = comparator.compare(object, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

	private int _contains(
		List<Element> list, Element object, Comparator<Element> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Element o = list.get(i);

			int value = comparator.compare(object, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

	private static final String[] _ROOT_ORDERED_CHILDREN = {};

}