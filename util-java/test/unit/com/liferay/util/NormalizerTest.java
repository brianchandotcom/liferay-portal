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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class NormalizerTest {

	@Test
	public void testAscii() {
		String asciiAlphabet =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		Assert.assertEquals(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
			Normalizer.normalizeToAscii(asciiAlphabet));
	}

	@Test
	public void testGreek() {
		StringBundler sb = new StringBundler(6);

		sb.append("\u0391\u0392\u0393\u0394\u0395\u0396\u0397\u0398\u0399");
		sb.append("\u039a\u039b\u039c\u039d\u039e\u039f\u03a0\u03a1\u03a3");
		sb.append("\u03a4\u03a5\u03a6\u03a7\u03a8\u03a9\u03b1\u03b2\u03b3");
		sb.append("\u03b4\u03b5\u03b6\u03b7\u03b8\u03b9\u03ba\u03bb\u03bc");
		sb.append("\u03bd\u03be\u03bf\u03c0\u03c1\u03c2\u03c3\u03c4\u03c5");
		sb.append("\u03c6\u03c7\u03c8\u03c9");

		String greekAlphabet = sb.toString();

		Assert.assertEquals(
			"ABGDEZETHIKLMN'XOPRSTYPHCHPSOabgdezethiklmn'xoprsstyphchpso",
			Normalizer.normalizeToAscii(greekAlphabet));
	}

	@Test
	public void testHungarian() {
		String hungarianAlphabet =
			"A\u00c1BCDE\u00c9FGHI\u00cdJKLMNO\u00d3\u00d6\u0150PQRSTU\u00da" +
				"\u00dc\u0170VWXYZa\u00e1bcde\u00e9fghi\u00edjklmno\u00f3" +
					"\u00f6\u0151pqrstu\u00fa\u00fc\u0171vwxyz";

		Assert.assertEquals(
			"AABCDEEFGHIIJKLMNOOOOPQRSTUUUUVWXYZaabcdeefghiijklmnoooopqrstuuu" +
				"uvwxyz",
			Normalizer.normalizeToAscii(hungarianAlphabet));
	}

	@Test
	public void testRussian() {
		StringBundler sb = new StringBundler(8);

		sb.append("\u0410\u0411\u0412\u0413\u0414\u0415\u0401\u0416\u0417");
		sb.append("\u0418\u0419\u041a\u041b\u041c\u041d\u041e\u041f\u0420");
		sb.append("\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429");
		sb.append("\u042a\u042b\u042c\u042d\u042e\u042f\u0430\u0431\u0432");
		sb.append("\u0433\u0434\u0435\u0451\u0436\u0437\u0438\u0439\u043a");
		sb.append("\u043b\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443");
		sb.append("\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b\u044c");
		sb.append("\u044d\u044e\u044f");

		String russianAlphabet = sb.toString();

		Assert.assertEquals(
			"ABVGDEEZZIJKLMNOPRSTUFHCCSS\"Y'EUAabvgdeezzijklmnoprstufhccss\"y" +
				"'eua",
			Normalizer.normalizeToAscii(russianAlphabet));
	}

	@Test
	public void testSpanish() {
		String spanishAlphabet =
			"ABCDEFGIJKLMN\u00d1OPQRSTUVWXYZabcdefghijklmn\u00f1opqrstuvwxyz";

		Assert.assertEquals(
			"ABCDEFGIJKLMNNOPQRSTUVWXYZabcdefghijklmnnopqrstuvwxyz",
			Normalizer.normalizeToAscii(spanishAlphabet));
	}

}