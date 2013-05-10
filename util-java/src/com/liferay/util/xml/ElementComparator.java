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

package com.liferay.util.xml;

import java.util.Comparator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Brian Wing Shun Chan
 */
public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element el1, Element el2) {
		String el1Name = el1.getName();
		String el2Name = el2.getName();

		if (!el1Name.equals(el2Name)) {
			return el1Name.compareTo(el2Name);
		}

		String el1Text = el1.getTextTrim();
		String el2Text = el2.getTextTrim();

		if (!el1Text.equals(el2Text)) {
			return el1Text.compareTo(el2Text);
		}

		List<Attribute> el1Attributes = el1.attributes();
		List<Attribute> el2Attributes = el2.attributes();

		if (el1Attributes.size() < el2Attributes.size()) {
			return -1;
		}
		else if (el1Attributes.size() > el2Attributes.size()) {
			return 1;
		}

		for (Attribute attr : el1Attributes) {
			int value = _compare(
				el2Attributes, attr, new AttributeComparator());

			if (value != 0) {
				return value;
			}
		}

		List<Element> el1Elements = el1.elements();
		List<Element> el2Elements = el2.elements();

		if (el1Elements.size() < el2Elements.size()) {
			return -1;
		}
		else if (el1Elements.size() > el2Elements.size()) {
			return 1;
		}

		for (Element el : el1Elements) {
			int value = _compare(el2Elements, el, new ElementComparator());

			if (value != 0) {
				return value;
			}
		}

		return 0;
	}

	private int _compare(
		List<Attribute> list, Attribute obj, Comparator<Attribute> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Attribute o = list.get(i);

			int value = comparator.compare(obj, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

	private int _compare(
		List<Element> list, Element obj, Comparator<Element> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Element o = list.get(i);

			int value = comparator.compare(obj, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

}