/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.servlet.filters;

import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.util.SerializableUtil;

import java.nio.ByteBuffer;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alberto Montero
 * @author Shuyang Zhou
 */
public class CacheResponseDataTest {

	@Test
	public void testReconstructFromSerialialization() throws Exception {
		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(new MockHttpServletResponse());

		byte[] data = new byte[10];

		Random random = new Random();

		random.nextBytes(data);

		ByteBuffer byteBuffer = ByteBuffer.wrap(data, 2, 5);

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		CacheResponseData cacheResponseData = new CacheResponseData(
			bufferCacheServletResponse);

		cacheResponseData.setAttribute("a1", "v1");
		cacheResponseData.setAttribute("b1", "v2");

		CacheResponseData deserializedCacheResponseData =
			(CacheResponseData)SerializableUtil.deserialize(
				SerializableUtil.serialize(cacheResponseData));

		Assert.assertEquals(
			"Content type not correctly recreated",
			cacheResponseData.getContentType(),
			deserializedCacheResponseData.getContentType());
		Assert.assertEquals(
			"Headers not correctly recreated", cacheResponseData.getHeaders(),
			deserializedCacheResponseData.getHeaders());
		Assert.assertEquals(
			"Attribute a1 not correctly recreated",
			cacheResponseData.getAttribute("a1"),
			deserializedCacheResponseData.getAttribute("a1"));
		Assert.assertEquals(
			"Attribute a2 not correctly recreated",
			cacheResponseData.getAttribute("a2"),
			deserializedCacheResponseData.getAttribute("a2"));

		ByteBuffer deserializedByteBuffer =
			deserializedCacheResponseData.getByteBuffer();

		Assert.assertArrayEquals(
			"Byte buffer data not correctly recreated",
			Arrays.copyOfRange(
				byteBuffer.array(),
				byteBuffer.arrayOffset() + byteBuffer.position(),
				byteBuffer.arrayOffset() + byteBuffer.limit()),
			Arrays.copyOfRange(
				deserializedByteBuffer.array(),
				deserializedByteBuffer.arrayOffset() +
					deserializedByteBuffer.position(),
				deserializedByteBuffer.arrayOffset() +
					deserializedByteBuffer.limit()));
	}

}