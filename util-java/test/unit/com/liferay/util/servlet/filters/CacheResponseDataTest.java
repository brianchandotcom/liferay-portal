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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

		for (int i = 0; i < 7; i++) {
			byteBuffer.put(i, (byte)i);
		}

		Assert.assertEquals(byteBuffer.get(2), 2, "ByteBuffer uninitialized");
		Assert.assertEquals(
			byteBuffer.limit(), 7, "ByteBuffer.limit() not correctly set");
		try {
			Assert.assertEquals(
				byteBuffer.get(8), 0, "ByteBuffer written beyond limit");
			Assert.fail("ByteBuffer allowed to read beyond the limit");
		}
		catch (IndexOutOfBoundsException ioob) {
		}

		bufferCacheServletResponse.setByteBuffer(byteBuffer);

		CacheResponseData cacheResponseData = new CacheResponseData(
			bufferCacheServletResponse);

		String attributeValue1 = "v1";
		String attributeValue2 = "v2";

		cacheResponseData.setAttribute("a1", attributeValue1);
		cacheResponseData.setAttribute("b1", attributeValue2);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(cacheResponseData);
		oos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(
			baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object o = ois.readObject();

		CacheResponseData returnedResponseData = (CacheResponseData)o;

		Assert.assertEquals(
			returnedResponseData.getContentType(),
			cacheResponseData.getContentType(),
			"ContentType not correctly recreated");

		Assert.assertEquals(
			returnedResponseData.getHeaders(), cacheResponseData.getHeaders(),
			"Headers not correctly recreated");

		Assert.assertEquals(
			returnedResponseData.getAttribute("a1"),
			cacheResponseData.getAttribute("a1"),
			"Attribute a1 not correctly recreated");

		Assert.assertEquals(
			returnedResponseData.getAttribute("a2"),
			cacheResponseData.getAttribute("a2"),
			"Attribute a2 not correctly recreated");

		ByteBuffer returnedByteBuffer = returnedResponseData.getByteBuffer();

		Assert.assertEquals(
			returnedByteBuffer.array(), byteBuffer.array(),
			"ByteBuffer data not correctly recreated");

		Assert.assertEquals(
			returnedByteBuffer.capacity(), byteBuffer.capacity(),
			"ByteBuffer.capacity() not correctly recreated");

		Assert.assertEquals(
			returnedByteBuffer.limit(), byteBuffer.limit(),
			"ByteBuffer.limit() not correctly recreated");

		Assert.assertEquals(
			returnedByteBuffer.position(), byteBuffer.position(),
			"ByteBuffer.position() not correctly recreated");
	}
}