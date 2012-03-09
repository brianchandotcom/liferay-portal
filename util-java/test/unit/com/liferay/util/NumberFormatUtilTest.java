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

import java.text.NumberFormat;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class NumberFormatUtilTest {

	@Test
	public void testNoDecimal() {
		String d = NumberFormatUtil.format(_numberFormat, 1d, 1.1);

		Assert.assertEquals("1", d);
	}

	@Test
	public void testOneDecimal() {
		String d = NumberFormatUtil.format(_numberFormat, 1.1d, 1.1);

		Assert.assertEquals("1.1", d);
	}

	private NumberFormat _numberFormat = NumberFormat.getNumberInstance(
		Locale.ENGLISH);

}