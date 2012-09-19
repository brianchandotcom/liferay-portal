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

package com.liferay.util;

import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.property.XProperty;

/**
 * @author Samuel Kong
 */
public class TimeZoneSensitive extends XProperty {

	public static final String PROPERTY_NAME = "X-LIFERAY-TIMEZONE-SENSITIVE";

	public TimeZoneSensitive() {
		super(PROPERTY_NAME);
	}

	public TimeZoneSensitive(ParameterList list, String value) {
		super(PROPERTY_NAME, list, value);
	}

	public TimeZoneSensitive(String value) {
		super(PROPERTY_NAME, value);
	}

	public void setValue(boolean value) {
		setValue(value ? "TRUE" : "FALSE");
	}

}