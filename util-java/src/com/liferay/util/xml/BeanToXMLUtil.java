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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.xml.Element;

import java.lang.reflect.Method;

import java.util.List;

/**
 * @author Charles May
 */
public class BeanToXMLUtil {

	public static void addBean(Object object, Element parentEl) {
		Class<?> clazz = object.getClass();

		String classNameWithoutPackage = getClassNameWithoutPackage(
			clazz.getName());

		Element el = parentEl.addElement(classNameWithoutPackage);

		addFields(object, el);
	}

	public static void addFields(Object object, Element parentEl) {
		Class<?> clazz = object.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String methodName = method.getName();

			if (methodName.startsWith("get") &&
				!methodName.equals("getClass")) {

				String memberName = StringUtil.removeSubstring(
					methodName, "get");

				memberName = TextFormatter.format(memberName, TextFormatter.I);
				memberName = TextFormatter.format(memberName, TextFormatter.K);

				try {
					Object returnValue = method.invoke(object, new Object[0]);

					if (returnValue instanceof List<?>) {
						List<Object> list = (List<Object>)returnValue;

						Element listEl = parentEl.addElement(memberName);

						for (Object curObject : list) {
							addBean(curObject, listEl);
						}
					}
					else {
						_add(parentEl, memberName, returnValue.toString());
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
		}
	}

	public static String getClassNameWithoutPackage(String className) {
		String[] classNameArray = StringUtil.split(className, CharPool.PERIOD);

		String classNameWithoutPackage =
			classNameArray[classNameArray.length - 1];

		classNameWithoutPackage = TextFormatter.format(
			classNameWithoutPackage, TextFormatter.I);
		classNameWithoutPackage = TextFormatter.format(
			classNameWithoutPackage, TextFormatter.K);

		return classNameWithoutPackage;
	}

	private static Element _add(Element element, String name, String text) {
		Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));

		return childElement;
	}

	private static final Log _log = LogFactoryUtil.getLog(BeanToXMLUtil.class);

}