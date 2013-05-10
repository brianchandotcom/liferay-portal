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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationTargetException;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Patrick Brady
 * @author Raymond Augé
 */
public class PropertyComparator implements Comparator<Object> {

	public PropertyComparator(String propertyName) {
		this(new String[] {propertyName}, true, false);
	}

	public PropertyComparator(
		String propertyName, boolean ascending, boolean caseSensitive) {

		this(new String[] {propertyName}, ascending, caseSensitive);
	}

	public PropertyComparator(String[] propertyNames) {
		this(propertyNames, true, false);
	}

	public PropertyComparator(
		String[] propertyNames, boolean ascending, boolean caseSensitive) {

		_propertyNames = propertyNames;
		_ascending = ascending;
		_caseSensitive = caseSensitive;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		try {
			for (String propertyName : _propertyNames) {
				Object property1 = PropertyUtils.getProperty(
					obj1, propertyName);
				Object property2 = PropertyUtils.getProperty(
					obj2, propertyName);

				if (!_ascending) {
					Object temp = property1;

					property1 = property2;
					property2 = temp;
				}

				if (property1 instanceof String) {
					int result = 0;

					if (_caseSensitive) {
						result = property1.toString().compareTo(
							property2.toString());
					}
					else {
						result = property1.toString().compareToIgnoreCase(
							property2.toString());
					}

					if (result != 0) {
						return result;
					}
				}

				if (property1 instanceof Comparable<?>) {
					int result = ((Comparable<Object>)property1).compareTo(
						property2);

					if (result != 0) {
						return result;
					}
				}
			}
		}
		catch (IllegalAccessException iae) {
			_log.error(iae.getMessage());
		}
		catch (InvocationTargetException ite) {
			_log.error(ite.getMessage());
		}
		catch (NoSuchMethodException nsme) {
			_log.error(nsme.getMessage());
		}

		return -1;
	}

	private static Log _log = LogFactoryUtil.getLog(PropertyComparator.class);

	private boolean _ascending;
	private boolean _caseSensitive;
	private String[] _propertyNames;

}