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

package com.liferay.util.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author Brian Wing Shun Chan
 */
public class NullSession implements HttpSession {

	public NullSession() {
		_attributes = new HashMap<>();
		_creationTime = System.currentTimeMillis();
		_id =
			NullSession.class.getName() + StringPool.POUND +
				StringUtil.randomId();
		_lastAccessedTime = _creationTime;
		_maxInactiveInterval = 0;
		_servletContext = null;
		_new = true;
	}

	@Override
	public Object getAttribute(String name) {
		return _attributes.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(_attributes.keySet());
	}

	@Override
	public long getCreationTime() {
		return _creationTime;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public long getLastAccessedTime() {
		return _lastAccessedTime;
	}

	@Override
	public int getMaxInactiveInterval() {
		return _maxInactiveInterval;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	/**
	 * @deprecated As of Bunyan (6.0.x)
	 */
	@Deprecated
	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[0]);
	}

	@Override
	public void invalidate() {
		_attributes.clear();
	}

	@Override
	public boolean isNew() {
		return _new;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		_attributes.remove(name);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		_attributes.put(name, value);
	}

	@Override
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		_maxInactiveInterval = maxInactiveInterval;
	}

	private final Map<String, Object> _attributes;
	private final long _creationTime;
	private final String _id;
	private final long _lastAccessedTime;
	private int _maxInactiveInterval;
	private final boolean _new;
	private final ServletContext _servletContext;

}