/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml;

import com.liferay.portal.kernel.util.GetterUtil;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class Dom4jDocUtil {

	public static Element add(Element element, QName qName) {
		return element.addElement(qName);
	}

	public static Element add(Element element, QName qName, boolean text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, double text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, float text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, int text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, long text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, Object text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, short text) {
		return add(element, qName, String.valueOf(text));
	}

	public static Element add(Element element, QName qName, String text) {
		Element childElement = element.addElement(qName);

		childElement.addText(GetterUtil.getString(text));

		return childElement;
	}

	public static Element add(Element element, String name, boolean text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, double text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, float text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, int text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, long text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace) {

		QName qName = DocumentHelper.createQName(name, namespace);

		return element.addElement(qName);
	}

	public static Element add(
		Element element, String name, Namespace namespace, boolean text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, double text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, float text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, int text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, long text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, Object text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, short text) {

		return add(element, name, namespace, String.valueOf(text));
	}

	public static Element add(
		Element element, String name, Namespace namespace, String text) {

		QName qName = DocumentHelper.createQName(name, namespace);

		return add(element, qName, text);
	}

	public static Element add(Element element, String name, Object text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, short text) {
		return add(element, name, String.valueOf(text));
	}

	public static Element add(Element element, String name, String text) {
		Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));

		return childElement;
	}

}