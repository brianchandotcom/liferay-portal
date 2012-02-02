/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Brian Wing Shun Chan
 * @author Alan Zimmerman
 * @author Jorge Ferrer
 */
public class XMLMerger {

	public XMLMerger(XMLDescriptor descriptor) {
		_descriptor = descriptor;
	}

	public XMLElementComparator getElementComparator() {
		return new XMLElementComparator(_descriptor);
	}

	public Document merge(Document masterDoc, Document slaveDoc) {
		Document mergedDoc = (Document)masterDoc.clone();

		Element root1 = mergedDoc.getRootElement();
		Element root2 = slaveDoc.getRootElement();

		List<Element> children = root2.elements();

		for (Element el2 : children) {
			Element el2Clone = (Element)el2.clone();

			el2Clone.detach();

			root1.add(el2Clone);
		}

		organizeXML(mergedDoc);

		return mergedDoc;
	}

	public void organizeXML(Document doc) {
		Element root = doc.getRootElement();

		_orderChildren(root, _descriptor.getRootChildrenOrder());
		_mergeDuplicateElements(root, getElementComparator());
	}

	private void _addChildren(
		Element first, Collection<Element> childrenToJoin) {

		Collection<Element> clones = new Vector<Element>();

		Iterator<Element> itr = childrenToJoin.iterator();

		while (itr.hasNext()) {
			clones.add((Element)itr.next().clone());
		}

		first.elements().addAll(clones);

		_orderChildren(first, _descriptor.getChildrenOrder(first));
	}

	private boolean _containsObjectEqualTo(
		Element example, List<Element> list, ElementComparator comparator) {

		Iterator<Element> itr = list.iterator();

		while (itr.hasNext()) {
			Element candidate = itr.next();

			if (comparator.compare(example, candidate) == 0) {
				return true;
			}
		}

		return false;
	}

	private Element _findObjectEqualTo(
		Element example, List<Element> list, ElementComparator comparator) {

		Iterator<Element> itr = list.iterator();

		while (itr.hasNext()) {
			Element candidate = itr.next();

			if (comparator.compare(example, candidate) == 0) {
				return candidate;
			}
		}

		return example;
	}

	private void _mergeDuplicateElements(
		Element el, ElementComparator comparator) {

		if (el.elements().size() > 0) {
			List<Element> children = el.elements();

			List<Element> originals = new ArrayList<Element>();
			List<Element> duplicates = new ArrayList<Element>();

			for (int i = 0; i < children.size(); i++) {
				Element child = children.get(i);

				if (_containsObjectEqualTo(child, originals, comparator)) {
					if (_descriptor.canJoinChildren(child)) {
						Element first = _findObjectEqualTo(
							child, originals, comparator);

						Collection<Element> childrenToJoin = child.elements();

						_addChildren(first, childrenToJoin);
					}

					duplicates.add(child);
				}
				else {
					originals.add(child);
				}
			}

			for (Element duplicate : duplicates) {
				duplicate.detach();
			}

			Iterator<Element> itr = originals.iterator();

			while (itr.hasNext()) {
				Element child = itr.next();

				_mergeDuplicateElements(child, comparator);
			}
		}
	}

	private void _orderChildren(Element parent, String[] orderedChildrenNames) {
		if (orderedChildrenNames == null) {
			return;
		}

		List<Element> elements = new ArrayList<Element>();

		for (int i = 0; i < orderedChildrenNames.length; i++) {
			elements.addAll(parent.elements(orderedChildrenNames[i]));
		}

		for (Element el : elements) {
			el.detach();

			parent.add(el);
		}
	}

	private XMLDescriptor _descriptor;

}