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
			"ContentType not correctly recreated",
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
			"ByteBuffer data not correctly recreated",
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