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

package org.slf4j.impl;

import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

/**
 * @author Michael C. Han
 */
public class StaticMarkerBinder implements MarkerFactoryBinder {

	public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder();

	@Override
	public IMarkerFactory getMarkerFactory() {
		return _iMarkerFactory;
	}

	@Override
	public String getMarkerFactoryClassStr() {
		return BasicMarkerFactory.class.getName();
	}

	private StaticMarkerBinder() {
	}

	private final IMarkerFactory _iMarkerFactory = new BasicMarkerFactory();

}