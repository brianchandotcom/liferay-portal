/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml.descriptor;

import com.liferay.util.xml.ElementIdentifier;

import org.dom4j.Document;

/**
 * @author Jorge Ferrer
 */
public class WebXML23Descriptor extends SimpleXMLDescriptor {

	@Override
	public boolean canHandleType(String doctype, Document root) {
		return doctype.contains("web-app");
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

	private static final ElementIdentifier[] _ELEMENTS_IDENTIFIED_BY_ATTR = {};

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

	private static final String[] _JOINABLE_ELEMENTS = {"welcome-file-list"};

	private static final String[] _ROOT_ORDERED_CHILDREN = {
		"icon", "display-name", "description", "distributable", "context-param",
		"filter", "filter-mapping", "listener", "servlet", "servlet-mapping",
		"session-config", "mime-mapping", "welcome-file-list", "error-page",
		"taglib", "resource-env-ref", "resource-ref", "security-constraint",
		"login-config", "security-role", "env-entry", "ejb-ref", "ejb-local-ref"
	};

	private static final String[] _UNIQUE_ELEMENTS = {
		"icon", "display-name", "description", "distributable",
		"session-config", "welcome-file-list", "login-config"
	};

}