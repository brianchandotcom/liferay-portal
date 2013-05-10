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

import org.dom4j.Attribute;

/**
 * @author Brian Wing Shun Chan
 */
public class AttributeComparator implements Comparator<Attribute> {

	@Override
	public int compare(Attribute attr1, Attribute attr2) {
		String attr1Value = attr1.getValue();
		String attr2Value = attr2.getValue();

		return attr1Value.compareTo(attr2Value);
	}

}