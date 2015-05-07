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
import com.liferay.portal.kernel.util.Validator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class CacheResponseData implements Externalizable, Serializable {

	public CacheResponseData() {};

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

	public void readExternal(ObjectInput in)
		throws IOException, ClassNotFoundException {

		_attributes = (Map<String, Object>)in.readObject();

		int capacity = in.readInt();
		byte[] content = new byte[capacity];
		in.read(content);
		_byteBuffer = ByteBuffer.wrap(content);

		int limit = in.readInt();
		_byteBuffer.limit(limit);

		int position = in.readInt();
		_byteBuffer.position(position);

		_contentType = in.readUTF();

		_headers = (Map<String, Set<Header>>)in.readObject();
	}
	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(_attributes);
		out.writeInt(_byteBuffer.capacity());
		out.write(_byteBuffer.array());
		out.writeInt(_byteBuffer.limit());
		out.writeInt(_byteBuffer.position());
		if (Validator.isNull(_contentType)) {
			_contentType = "";
		}
		out.writeUTF(_contentType);
		out.writeObject(_headers);
	}

	private Map<String, Object> _attributes = new HashMap<>();
	private transient ByteBuffer _byteBuffer;
	private byte[] _content;
	private String _contentType;
	private Map<String, Set<Header>> _headers;

}