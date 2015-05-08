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

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

import org.testng.Assert;

/**
 * @author Alberto Montero
 */
public class CacheResponseDataTest {

	@Test
	public void testReconstructFromSerialialization() throws Exception {
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(mockHttpServletResponse);

		ByteBuffer byteBuffer = ByteBuffer.allocate(10);

		byteBuffer.limit(7);

		for (byte b = 0; b < 7; b++) {
			byteBuffer.put(b, b);
		}

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		CacheResponseData cacheResponseData = new CacheResponseData(
			bufferCacheServletResponse);

		cacheResponseData.setAttribute("a1", "v1");
		cacheResponseData.setAttribute("b1", "v2");

		byte[] serializedCacheResponseData = SerializableUtil.serialize(
			cacheResponseData);

		CacheResponseData deserializedCacheResponseData =
			(CacheResponseData)SerializableUtil.deserialize(
				serializedCacheResponseData);

		Assert.assertEquals(
			deserializedCacheResponseData.getContentType(),
			cacheResponseData.getContentType(),
			"ContentType not correctly recreated");

		Assert.assertEquals(
			deserializedCacheResponseData.getHeaders(),
			cacheResponseData.getHeaders(), "Headers not correctly recreated");

		Assert.assertEquals(
			deserializedCacheResponseData.getAttribute("a1"),
			cacheResponseData.getAttribute("a1"),
			"Attribute a1 not correctly recreated");

		Assert.assertEquals(
			deserializedCacheResponseData.getAttribute("a2"),
			cacheResponseData.getAttribute("a2"),
			"Attribute a2 not correctly recreated");

		ByteBuffer deserializedByteBuffer =
			deserializedCacheResponseData.getByteBuffer();

		Assert.assertEquals(
			deserializedByteBuffer.array(), byteBuffer.array(),
			"ByteBuffer data not correctly recreated");

		Assert.assertEquals(
			deserializedByteBuffer.capacity(), byteBuffer.capacity(),
			"ByteBuffer.capacity() not correctly recreated");

		Assert.assertEquals(
			deserializedByteBuffer.limit(), byteBuffer.limit(),
			"ByteBuffer.limit() not correctly recreated");

		Assert.assertEquals(
			deserializedByteBuffer.position(), byteBuffer.position(),
			"ByteBuffer.position() not correctly recreated");
	}

}