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
import com.liferay.portal.kernel.servlet.Header;

import java.io.IOException;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class CacheResponseData implements Serializable {

	public CacheResponseData(
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		_byteBuffer = bufferCacheServletResponse.getByteBuffer();
		_content = _byteBuffer.array();
		_contentType = bufferCacheServletResponse.getContentType();
		_headers = bufferCacheServletResponse.getHeaders();
	}

	public Object getAttribute(String name) {
		return _attributes.get(name);
	}

	public ByteBuffer getByteBuffer() {
		if (_byteBuffer == null) {
			_byteBuffer = ByteBuffer.wrap(_content);
		}

		return _byteBuffer;
	}

	public String getContentType() {
		return _contentType;
	}

	public Map<String, Set<Header>> getHeaders() {
		return _headers;
	}

	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	private final Map<String, Object> _attributes =
		new HashMap<String, Object>();
	private transient ByteBuffer _byteBuffer;
	private final byte[] _content;
	private final String _contentType;
	private final Map<String, Set<Header>> _headers;

}