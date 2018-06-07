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

package com.liferay.taglib.util;

import com.liferay.taglib.TagSupport;

import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ParamTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		ParamAncestorTag paramAncestorTag =
			(ParamAncestorTag)findAncestorWithClass(
				this, ParamAncestorTag.class);

		if (paramAncestorTag == null) {
			throw new JspException();
		}

		if (paramAncestorTag instanceof TypedParamAccessorTag) {
			TypedParamAccessorTag typedParamAccessorTag =
				(TypedParamAccessorTag)paramAncestorTag;

			typedParamAccessorTag.addParam(_name, _type, _value);
		}
		else {
			paramAncestorTag.addParam(_name, _value);
		}

		return SKIP_BODY;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(String value) {
		_value = value;
	}

	private String _name;
	private String _type;
	private String _value;

}