/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class AttributesTagSupport
	extends ParamAndPropertyAncestorTagImpl implements DynamicAttributes {

	public void clearDynamicAttributes() {
		_dynamicAttributes.clear();
	}

	public String getAttributeNamespace() {
		return _attributeNamespace;
	}

	public Map<String, Object> getScopedAttributes() {
		return _scopedAttributes;
	}

	@Override
	public void release() {
		super.release();

		_attributeNamespace = null;
		_dynamicAttributes = null;
		_scopedAttributes = null;
	}

	public void setAttributeNamespace(String attributeNamespace) {
		_attributeNamespace = attributeNamespace;
	}

	@Override
	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
	}

	public void setNamespacedAttribute(
		HttpServletRequest request, String key, Object value) {

		if (value instanceof Boolean) {
			value = String.valueOf(value);
		}
		else if (value instanceof Number) {
			value = String.valueOf(value);
		}

		request.setAttribute(_encodeKey(key), value);
	}

	public void setScopedAttribute(String name, Object value) {
		_scopedAttributes.put(name, value);
	}

	protected Map<String, Object> getDynamicAttributes() {
		return _dynamicAttributes;
	}

	private String _encodeKey(String key) {
		if (_attributeNamespace.length() == 0) {
			return key;
		}
		else {
			return _attributeNamespace.concat(key);
		}
	}

	private String _attributeNamespace = StringPool.BLANK;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private Map<String, Object> _scopedAttributes =
		new HashMap<String, Object>();

}