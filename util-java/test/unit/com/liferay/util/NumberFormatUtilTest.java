/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.NumberFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class NumberFormatUtilTest {

	@Test
	public void testNoDecimal() {
		Assert.assertEquals(
			"1", NumberFormatUtil.format(_numberFormat, 1, 1.1));
	}

	@Test
	public void testOneDecimal() {
		Assert.assertEquals(
			"1.1", NumberFormatUtil.format(_numberFormat, 1.1, 1.1));
	}

	private final NumberFormat _numberFormat = NumberFormat.getNumberInstance(
		LocaleUtil.ENGLISH);

}