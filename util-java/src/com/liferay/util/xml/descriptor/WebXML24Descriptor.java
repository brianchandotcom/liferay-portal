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

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class WebXML24Descriptor extends SimpleXMLDescriptor {

	public WebXML24Descriptor() {
		_orderedChildren.put(
			"servlet",
			new String[] {
				"icon", "servlet-name", "display-name", "description",
				"servlet-class", "jsp-file", "init-param", "load-on-startup",
				"run-as", "security-role-ref"
			});
		_orderedChildren.put(
			"jsp-config", new String[] {"taglib", "jsp-property-group"});
	}

	@Override
	public boolean canHandleType(String doctype, Document root) {
		return doctype.contains("web-app");
	}

	@Override
	public String[] getChildrenOrder(Element parentElement) {
		String parentName = parentElement.getQName().getName();

		String[] childrenOrder = _orderedChildren.get(parentName);

		if (childrenOrder == null) {
			childrenOrder = new String[0];
		}

		return childrenOrder;
	}

	@Override
	public ElementIdentifier[] getElementsIdentifiedByAttribute() {
		return _ELEMENTS_IDENTIFIED_BY_ATTR;
	}

	@Override
	public ElementIdentifier[] getElementsIdentifiedByChild() {
		return _ELEMENTS_IDENTIFIED_BY_CHILD;
	}

	@Override
	public String[] getJoinableElements() {
		return _JOINABLE_ELEMENTS;
	}

	@Override
	public String[] getRootChildrenOrder() {
		return _ROOT_ORDERED_CHILDREN;
	}

	@Override
	public String[] getUniqueElements() {
		return _UNIQUE_ELEMENTS;
	}

	private static final ElementIdentifier[] _ELEMENTS_IDENTIFIED_BY_ATTR = {
	};

	private static final ElementIdentifier[] _ELEMENTS_IDENTIFIED_BY_CHILD = {
		new ElementIdentifier("context-param", "param-name"),
		new ElementIdentifier("filter", "filter-name"),
		//new ElementIdentifier("filter-mapping", "filter-name"),
		new ElementIdentifier("servlet", "servlet-name"),
		//new ElementIdentifier("servlet-mapping", "servlet-name"),
		new ElementIdentifier("init-param", "param-name"),
		new ElementIdentifier("taglib", "taglib-uri"),
		new ElementIdentifier("resource-env-ref", "res-env-ref-name"),
		new ElementIdentifier("resource-ref", "res-ref-name"),
		new ElementIdentifier("ejb-local-ref", "ejb-ref-name")
	};

	private static final String[] _JOINABLE_ELEMENTS = {
		"welcome-file-list", "jsp-config"
	};

	private static final String[] _ROOT_ORDERED_CHILDREN = {
		"icon", "display-name", "description", "distributable", "context-param",
		"filter", "filter-mapping", "listener", "servlet", "servlet-mapping",
		"session-config", "mime-mapping", "welcome-file-list", "error-page",
		"jsp-config", "resource-env-ref", "resource-ref", "security-constraint",
		"login-config", "security-role", "env-entry", "ejb-ref", "ejb-local-ref"
	};

	private static final String[] _UNIQUE_ELEMENTS = {
		"icon", "display-name", "description", "distributable",
		"session-config", "welcome-file-list", "jsp-config", "login-config"
	};

	private final Map<String, String[]> _orderedChildren =
		new HashMap<String, String[]>();

}