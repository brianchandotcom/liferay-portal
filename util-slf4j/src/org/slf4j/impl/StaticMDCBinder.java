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

package org.slf4j.impl;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * @author Michael C. Han
 */
public class StaticMDCBinder {

	public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

	public MDCAdapter getMDCA() {
		return new BasicMDCAdapter();
	}

	public String getMDCAdapterClassStr() {
		return BasicMDCAdapter.class.getName();
	}

	private StaticMDCBinder() {
	}

}