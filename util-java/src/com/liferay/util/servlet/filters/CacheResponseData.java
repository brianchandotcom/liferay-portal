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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public CacheResponseData() {
		_content = null;
		_contentType = null;
		_headers = null;
		_length = 0;
		_offset = 0;
		_valid = false;
	}

	public CacheResponseData(
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

		_content = byteBuffer.array();

		_contentType = bufferCacheServletResponse.getContentType();
		_headers = bufferCacheServletResponse.getHeaders();
		_length = byteBuffer.remaining();
		_offset = byteBuffer.arrayOffset() + byteBuffer.position();
		_valid = true;
	}

	public Object getAttribute(String name) {
		return _attributes.get(name);
	}

	public ByteBuffer getByteBuffer() {
		return ByteBuffer.wrap(_content, _offset, _length);
	}

	public String getContentType() {
		return _contentType;
	}

	public Map<String, Set<Header>> getHeaders() {
		return _headers;
	}

	public boolean isValid() {
		return _valid;
	}

	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		_length = objectInputStream.readInt();

		if (_length > 0) {
			_content = new byte[_length];

			objectInputStream.readFully(_content);
		}
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		objectOutputStream.writeInt(_length);

		if (_length > 0) {
			objectOutputStream.write(_content, _offset, _length);
		}
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private transient byte[] _content;
	private final String _contentType;
	private final Map<String, Set<Header>> _headers;
	private transient int _length;
	private final transient int _offset;
	private final boolean _valid;

}